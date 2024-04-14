package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建BCryptPasswordEncoder注入容器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;




    /**
     * 对一些接口放行 比如：登录
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭 csrf
                .csrf().disable()
                // 不通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()//登录
                .antMatchers("/user/get/verification/code").anonymous()//获得验证码
                .antMatchers("/user/reset/password").anonymous()//重新设置密码
                .antMatchers("/get/picture").anonymous()//获得图片视频
                .antMatchers("/dify/ask/question").anonymous()//问问题
                //.antMatchers("/user/register").anonymous()
                // 除了上面的请求，任何请求都需要鉴权认证
//                .anyRequest().authenticated()
                .and().sessionManagement(item -> item.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(req -> req
                        //非普通请求(比如请求新增了自定义头部信息,比如Jwt头),会发送预检Option请求，这里直接让他通过
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().authenticated());
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 重写该方法，暴露 authenticationManagerBean
     * 具体使用只需保留原本的代码即可
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }






}
