package org.example.service;

import org.example.controller.dto.UserDto;
import org.example.dao.mapper.BehavioralRecordMapper;
import org.example.entity.obj1.BehavioralRecord;
import org.example.service.obj.BehavioralRecordBo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;




@Service
public class FunctionService {


    @Resource
    BehavioralRecordMapper behavioralRecordMapper;



    public static String getFileExtension(String fileName) {//获取后缀名
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }


    public void saveRecord(String record, String object) {
        UsernamePasswordAuthenticationToken authenticationToken
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authenticationToken.getPrincipal();//获得操作人

        BehavioralRecordBo behavioralRecordBo = new BehavioralRecordBo(userDto, record, object);//创建对象
        behavioralRecordMapper.insert(new BehavioralRecord(behavioralRecordBo));//记录操作


    }


}
