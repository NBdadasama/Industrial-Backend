package org.example.service;


import org.example.controller.dto.UserDto;
import org.example.dao.mapper.UserMapper;
import org.example.entity.obj1.User;
import org.example.service.obj.UserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {


    @Resource
    private UserMapper userMapper;

    public UserDto login(UserDto userDto){//查询是否
        User user = userMapper.selectByUserId(userDto.getUserId());
        System.out.println(user);

        if(user==null)
            return null;
        else {
            userDto.setPassword(user.getUserPassword());//密码赋值
            userDto.setId(String.valueOf(user.getId()));
            userDto.setName(user.getUserName());
            userDto.setIdentity(user.getIdentity());
            return userDto;
        }
    }
    public User register(UserBo userBo){//注册
        User user=new User(userBo);

        if(userMapper.selectByUserId(user.getUserId())==null) {//没有这个用户
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodePassword);//加密后密码

            System.out.println(user);
            userMapper.insert(user);//加密后密码
            return user;
        }
        else return null;//如果这个用户存在

    }




}
