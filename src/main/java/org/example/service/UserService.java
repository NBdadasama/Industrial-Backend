package org.example.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.dao.mapper.UserMapper;
import org.example.entity.obj1.User;
import org.example.function.GenerateRandom;
import org.example.service.obj.UserBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;


@Service
public class UserService {


    @Resource
    private UserMapper userMapper;



    @Resource
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    public List<User> getAllUser(int page,int number){

        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();

        Page<User> userPage = new Page<>(page,number);
        Page<User> userPage1=userMapper.selectPage(userPage,userLambdaQueryWrapper);
        List<User> userList=userPage1.getRecords();
        return userList;
    }

    public int deleteUser(String id){//根据id删除用户

        return userMapper.deleteById(id);//删除用户
    }



    public User getUser(String userId) {

        return userMapper.selectByUserId(userId);
    }
    public User modifySelf(UserBo userBo){//修改自身信息

        User user=userMapper.selectByUserId(userBo.getUserId());
        User newUser=new User(userBo);
        newUser.setUserPassword(user.getUserPassword());//密码不变
        newUser.setId(user.getId());//Id不变
        newUser.setRegisterTime(user.getRegisterTime());//注册时间不变
        userMapper.updateById(newUser);
        return newUser;
    }


    public String generateVerifyCode(int number){//获取验证码
        return GenerateRandom.getRandom(number);//生成验证码
    }


    public User resetPassword(String userId, String newPassword) {

        User user=userMapper.selectByUserId(userId);//拿到这个用户
        user.setUserPassword(newPassword);
        userMapper.updateById(user);//更新用户
        return user;
    }

    public void sendMail(String to,String text) throws MessagingException {

        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject("密码验证码");
            //邮件内容
            mimeMessageHelper.setText("您本次密码验证码为:"+text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            System.out.println("发送邮件成功："+sendMailer+"->"+to);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败："+e.getMessage());
        }
    }


}
