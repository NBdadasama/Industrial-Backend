package org.example.controller;


import org.example.dao.mapper.BehavioralRecordMapper;
import org.example.entity.obj1.BehavioralRecord;
import org.example.function.JwtUtil;
import org.example.function.Result;
import org.example.controller.dto.UserDto;
import org.example.entity.obj1.User;
import org.example.function.RedisUtil;
import org.example.service.FunctionService;
import org.example.service.LoginService;
import org.example.service.obj.BehavioralRecordBo;
import org.example.service.obj.UserBo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;


    @Resource
    FunctionService functionService;

    @PostMapping("/user/login")//登录
    public Result login(@RequestBody UserDto userDto) {
        // 1. 检查请求体是否为空。如果是空，则抛出参数错误异常。
        if (userDto == null) {
            return Result.failure(Result.ResultCode.USER_NOT_EXISTED, null);//用户不存在
        }
        //2. 检查用户是否存在，密码是否正确
        String password = userDto.getPassword();
        userDto = loginService.login(userDto);//登录
        if (userDto == null)
            return Result.failure(Result.ResultCode.USER_NOT_EXISTED, null);//用户不存在
        else if (!passwordEncoder.matches(password, userDto.getPassword()))//校验密码是否相等
            return Result.failure(Result.ResultCode.USER_LOGIN_ERROR, null);//如果密码不相等
        // 3. 登录成功后，准备生成JWT令牌。首先创建一个用于存放令牌信息的HashMap。
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userDto.getUserId()); // 将用户ID加入map，用于生成JWT中的claims。
        // 4. 调用后端服务的getJwtToken方法，生成JWT令牌。
        String jwtToken = JwtUtil.createJWT(String.valueOf(map));//使用加密后密码作为key
        // 5. 存入redis缓存内
        userDto.setJwt(jwtToken);//返回给前端
        redisUtil.set("JWT" + userDto.getUserId(), userDto, 60 * 60);//存入缓存中，时间为一个小时
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("authorization", jwtToken);

        //functionService.saveRecord(BehavioralRecordBo.RecordCode.LOGIN.getMessage(),userDto.toString());
        return Result.success(userDto);//成功
    }

    @PostMapping("/user/register")//注册，管理员才可以注册用户
    public Result register(@RequestBody UserBo userBo) {
        User user = loginService.register(userBo);//
        if (user != null) {
            functionService.saveRecord(BehavioralRecordBo.RecordCode.LOGIN.getMessage(), user.toString());
            return Result.success(user);//注册成功
        } else {
            return Result.failure(Result.ResultCode.USER_EXISTED);//用户已经存在,注册失败
        }
    }

    @GetMapping("/user/logout")//注销登录
    public Result logout() {
        UsernamePasswordAuthenticationToken authenticationToken
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authenticationToken.getPrincipal();//获得操作人
        String userId = userDto.getUserId();
        // 根据Key删除Redis的值
        redisUtil.del("JWT" + userId);
        functionService.saveRecord(BehavioralRecordBo.RecordCode.LOGOUT.getMessage(), userDto.toString());
        return Result.success("注销成功");
    }
}
