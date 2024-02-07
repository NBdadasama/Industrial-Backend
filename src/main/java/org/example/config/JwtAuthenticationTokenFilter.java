package org.example.config;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.example.controller.dto.UserDto;
import org.example.function.JwtUtil;
import org.example.function.RedisUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 定义 Token 解析过滤器
     * @param request
     * @param response
     * @param filterChain
     //* @throws ServletException
     //* @throws IOException
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 获取Token
        String token = request.getHeader("Authorization");


        if (StrUtil.isBlank(token)) {
            // 没有获取到Token 放行，让其它过滤器去拦截
            filterChain.doFilter(request, response);
            return;
        }
        // 2. 解析Token 获取userID
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);//解密
            userId = claims.getSubject();
            System.out.println(userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token异常!");
        }
        // 3. 从Redis中取出用户数据

        String pattern = "\\d{6}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(userId);
        String id=new String();
        while (m.find()) {
            String matchedNumber = m.group();
            id=id+matchedNumber;
        }


        String redisKey="JWT"+id;//RedisConstants.LOGIN_USER_KEY + userId;

        UserDto userDto = (UserDto) redisUtil.get(redisKey);
        if (userDto==null) {
            throw new RuntimeException("Token异常!");
        }
        /**
         * setAuthentication()方法需要一个authentication对象，
         * 所以我们需要把用户信息封装到authentication中
         */
        // TODO 获取权限信息封装到 authentication 中(第三个参数)
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDto,null,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 5. 放行
        filterChain.doFilter(request, response);
    }
}

