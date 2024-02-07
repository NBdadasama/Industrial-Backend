package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj1.User;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {//可以存到redis内

    String id;
    int identity;//身份
    String userId;
    String password;
    String name;
    String jwt;


    public UserDto(User user) {

        this.identity = user.getIdentity(); // 假设User类有一个getIdentity()方法来获取身份
        this.userId = user.getUserId(); // 假设User类有一个getUserId()方法来获取用户ID


    }
}
