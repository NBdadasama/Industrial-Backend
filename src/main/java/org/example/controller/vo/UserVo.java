package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj1.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private String name;//姓名
    private String userId;//用户名

    private String phone;
    private int age;//年龄
    private int identity;//身份
    private String mail;//邮箱

    public UserVo(User user) {
        if (user != null) {
            this.name = user.getUserName();
            this.userId = user.getUserId();
            this.phone = user.getPhone();
            this.age = user.getAge();
            this.identity = user.getIdentity();
            this.mail = user.getMail();
        } else {
            // 处理user为null的情况，例如初始化默认值或抛出异常等
        }
    }




}
