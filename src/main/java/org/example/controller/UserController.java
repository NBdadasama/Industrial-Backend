package org.example.controller;


import org.example.controller.dto.UserDto;
import org.example.controller.vo.UserVo;
import org.example.dao.mapper.BehavioralRecordMapper;
import org.example.entity.obj1.BehavioralRecord;
import org.example.entity.obj1.User;
import org.example.function.RedisUtil;
import org.example.function.Result;
import org.example.service.FunctionService;
import org.example.service.UserService;
import org.example.service.obj.BehavioralRecordBo;
import org.example.service.obj.UserBo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;


// 人员管理的controller
@Controller
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    BehavioralRecordMapper behavioralRecordMapper;
    @Resource
    RedisUtil redisUtil;

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    FunctionService functionService;

    @PostMapping("/get/all")//获得全部用户
    public Result getAllUser(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {

        List<User> users = userService.getAllUser(pageNum, pageSize);

        List<UserVo> userVos = users.stream().map((User user) -> {
            return new UserVo(user);
        }).collect(Collectors.toList());
        return Result.success(userVos);//返回用户list
    }

    @GetMapping("/get/{userId}")//获得单个用户
    public Result getOneUser(@PathVariable("userId") String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return Result.failure(Result.ResultCode.USER_NOT_EXISTED);//用户不存在
        }
        return Result.success(new UserVo(user));//返回用户list
    }

    @PostMapping("/delete/{userId}")//删除用户
    public Result deleteUser(@PathVariable String userId) {


        User user = userService.getUser(userId);

        // fixed 使用了错误的字段来删除用户
        // 应该使用 id 字段来删除用户而不是userId
        int x = userService.deleteUser(String.valueOf(user.getId()));
        if (x > 0) {//删除用户
            functionService.saveRecord(BehavioralRecordBo.RecordCode.DELETE_USER.getMessage(), user.toString());
            return Result.success("删除成功");
        } else return Result.failure(Result.ResultCode.DELETE_FAILURE, "删除失败");
    }


    @PostMapping("/modify/information")//修改自己信息
    public Result modifySelf(@RequestBody UserBo userBo) {
        User user = userService.getUser(userBo.getUserId());

        if (user == null) {
            return Result.failure(Result.ResultCode.USER_NOT_EXISTED);//用户不存在

        } else {
            user = userService.modifySelf(userBo);//修改user
            functionService.saveRecord(BehavioralRecordBo.RecordCode.MODIFY_USER.getMessage(), user.toString());

            return Result.success(user);
        }


    }


    @GetMapping("/get/verification/code")//获得验证码
    public Result getVerificationCode(@RequestParam String userId, @RequestParam int category) throws MessagingException {//获取验证码,后面是类别

        String code = userService.generateVerifyCode(6);//默认六位验证码

        User user = userService.getUser(userId);
        if (user == null) return Result.failure(Result.ResultCode.USER_NOT_EXISTED);//用户不存在

        if (category == 1) {//重设密码
            redisUtil.set("RESET" + userId, code, 60 * 5);//五分钟过期
            userService.sendMail(userService.getUser(userId).getMail(), code);//发送验证码
        }
        // todo 取消鉴权逻辑
//        functionService.saveRecord(BehavioralRecordBo.RecordCode.GET_CODE.getMessage(), code);
        return Result.success(code);//验证码返回


    }

    @PostMapping("/reset/password")//重置密码
    public Result retrievePassword(@RequestParam String userId, @RequestParam String code, @RequestParam String newPassword) {//验证码

        String userCode = (String) redisUtil.get("RESET" + userId);//用户验证码
        if (userCode == null)//验证码不存在
            return Result.failure(Result.ResultCode.CODE_ERROR);//验证码错误

        else if (code.equals(userCode)) {//如果验证码相等

            User user = userService.resetPassword(userId, passwordEncoder.encode(newPassword));//重设密码

// todo 取消鉴权逻辑
//            functionService.saveRecord(BehavioralRecordBo.RecordCode.MODIFY_PASSWORD.getMessage(), user.toString());

            return Result.success("修改成功");
        } else {
            return Result.failure(Result.ResultCode.CODE_ERROR);
        }


    }


}
