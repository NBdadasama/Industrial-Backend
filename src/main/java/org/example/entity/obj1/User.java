package org.example.entity.obj1;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.function.TimeUtil;
import org.example.service.obj.UserBo;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName("user")
public class User implements Serializable {


    public User (UserBo userBo){
        this.age=userBo.getAge();
        this.userId=userBo.getUserId();
        this.mail=userBo.getMail();
        this.userPassword="123456";//初始密码
        this.phone=userBo.getPhone();
        this.userName=userBo.getName();
        this.identity=userBo.getIdentity();
        this.registerTime= TimeUtil.getNowTime();//获取时间
    }
    @TableId
    private Long id;
    private String userName;
    private String userId;

    private String userPassword;//密码

    private String phone;

    private int age;

    private int identity;//身份

    private String mail;

    private Timestamp registerTime;//注册时间


}
