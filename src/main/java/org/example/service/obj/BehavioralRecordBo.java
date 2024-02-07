package org.example.service.obj;


import lombok.Data;
import org.example.controller.dto.UserDto;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class BehavioralRecordBo {//记录此次操作所做的事


    private String userId;//


    private String name;//用户姓名

    private String msg;//做了什么事

    private String object;

    public BehavioralRecordBo(UserDto userDto,String record) {

        this.userId=userDto.getUserId();
        this.name=userDto.getName();
        this.msg=record;
    }

    public BehavioralRecordBo(UserDto userDto, String record, String object) {
        this.userId=userDto.getUserId();
        this.name=userDto.getName();
        this.msg=record;
        this.object=object;//对象

    }


    public enum RecordCode {
        LOGIN("登录"),//
        SEARCH("查询"),//
        REGISTER("注册"),//

        ADD_DEVICE("新增设备"),//设备

        ADD_FAULT("新增故障"),//

        ADD_MAINTENANCE("新增保养"),//
        LOGOUT("注销用户"),
        DELETE_NODE("删除节点"),
        MODIFY_NODE("修改节点"),
        DELETE_USER("删除用户信息"),
        RESET_PASSWORD("修改密码"),
        MODIFY_USER("修改用户信息"),
        ADD_SOP("新增SOP"),
        EXPORT_DATA("导出数据"),
        IMPORT_DATA("导入数据"),
        UPLOAD_PICTURE("上传资料"),
        DELETE_SOP("删除SOP"),
        ADD_SOP_RELATION("新建SOP关系"),
        GET_CODE("获取验证码"),
        MODIFY_PASSWORD("修改密码"),
        CREATE_CONSERVATION("创建对话"),
        ASK_QUESTION("询问"),
        DELETE_CONVERSATION("删除对话");//注销
        private String message;

        public String getMessage() {
            return message;
        }

        RecordCode(String message) {
            this.message = message;
        }


    }




}
