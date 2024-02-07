package org.example.service.obj;


import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class UserBo {

    String userId;//id


    String name;//姓名

    String phone;//电话

    int identity;//身份

    int age;//年龄

    String mail;//邮箱





}
