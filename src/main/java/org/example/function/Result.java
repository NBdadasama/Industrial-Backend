package org.example.function;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    private Result(){}

    public enum ResultCode {

        SUCCESS(200,"成功"),
        PARAM_IS_INVALID(1001,"参数无效"),
        PARAM_IS_BLANK(1002,"参数为空"),
        USER_NOT_LOGGED_IN(2001,"用户未登录"),
        USER_LOGIN_ERROR(2002,"用户不存在或密码错误"),
        USER_NOT_EXISTED(2003,"用户不存在"),

        DELETE_FAILURE(2004,"用户不存在或删除失败"),

        DEVICE_EXISTED(2005,"设备信息已存在"),

        FAULT_EXISTED(2006,"故障信息已存在"),

        MAINTENANCE_EXISTED(2007,"保养信息已存在"),

        DEVICE_NOT_EXISTED(2008,"设备不存在"),

        FAULT_NOT_EXISTED(2009,"故障不存在"),

        MainTENANCE_NOT_EXISTED(2010,"保养信息不存在"),

        DELETE_NODE_FAILURE(2011,"删除失败或节点不存在"),

        CODE_ERROR(2012,"验证码错误" ),

        SOP_EXITED(2013,"SOP节点已存在" ),

        USER_EXISTED(2100,"用户已存在"), SOP_NOT_EXITED(2014,"sop节点不存在" );
        private Integer code;
        private String message;

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        ResultCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }


    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public Result(ResultCode resultCode,Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = data;
    }


    public static Result success(){
        Result result = new Result(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result(ResultCode.SUCCESS, data);
        return result;
    }

    public static Result failure(ResultCode resultCode){
        Result result = new Result(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode,Object data){
        Result result = new Result(resultCode, data);
        return result;
    }



}