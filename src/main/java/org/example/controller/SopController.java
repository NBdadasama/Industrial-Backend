package org.example.controller;


import org.example.controller.dto.SopDto;
import org.example.controller.dto.SopRelationDto;
import org.example.controller.dto.UserDto;
import org.example.dao.mapper.BehavioralRecordMapper;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Sop;
import org.example.entity.obj1.BehavioralRecord;
import org.example.entity.relation.FaultToSop;
import org.example.entity.relation.SopToSop;
import org.example.service.FunctionService;
import org.example.function.Result;
import org.example.service.NodeService;
import org.example.service.SopService;
import org.example.service.obj.BehavioralRecordBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Controller
@RestController
@CrossOrigin(origins = "*")
public class SopController {//控制Sop

    @Resource
    SopService sopService;


    @Resource
    NodeService nodeService;


    @Resource
    FunctionService functionService;

    @Value("${sop.file.url}")
    String sopUrl;

    @PostMapping("/add/sop")//添加一sop信息
    public Result addSOP(@RequestBody SopDto sopDto) {
        Fault fault = nodeService.getFault(sopDto.getDeviceId(), sopDto.getFaultId());//
        if (fault == null) {//如果这个故障不存在
            return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);//故障不存在
        }
        Sop newSop = sopService.addSop(sopDto);//增加这个节点
        functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_SOP.getMessage(), newSop.toString());
        return Result.success(newSop);
    }


    @PostMapping("/add/sop/relation")//添加一sop关系
    public Result addSopRelation(@RequestBody SopRelationDto sopRelationDto) {
        Fault fault = nodeService.getFault(sopRelationDto.getDeviceId(), sopRelationDto.getFaultId());//
        if (fault == null) {//如果这个故障不存在
            return org.example.function.Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);//故障不存在
        }

        if (sopRelationDto.getFrom().equals("00000000")) {//是fault和sop
            FaultToSop faultToSop = sopService.addFaultToSOP(sopRelationDto);
            functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_SOP_RELATION.getMessage(), faultToSop.toString());
            return Result.success(faultToSop);
        } else {//是sop和sop
            Sop from = sopService.getSop(sopRelationDto.getFrom());
            Sop to = sopService.getSop(sopRelationDto.getTo());//
            if (from != null && to != null) {//两个节点都存在
                SopToSop sopToSop = sopService.addSOPToSOP(sopRelationDto);//插入节点

                functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_SOP_RELATION.getMessage(), sopToSop.toString());
                return Result.success(sopToSop);
            } else return Result.failure(Result.ResultCode.SOP_NOT_EXITED);//Sop节点不存在
        }
    }


    @GetMapping("/export/sops/json")//获得sop的json文件
    public Result getSopJson(@RequestParam String deviceId, @RequestParam String faultId) {//导出哪个设备的
        Fault fault = nodeService.getFault(deviceId, faultId);//设备名字，设备故障码
        if (fault != null) {//如果故障存在
            List<SopDto> sopDtos = sopService.getSopsByFaultId(deviceId, faultId);

            functionService.saveRecord(BehavioralRecordBo.RecordCode.EXPORT_DATA.getMessage(), sopDtos.toString());
            return Result.success(sopDtos);//导出数据
        } else return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);
    }


    @GetMapping("/export/sopRelations/json")//获得sop的json文件
    public Result getRelationJson(@RequestParam String deviceId, @RequestParam String faultId) {//导出哪个设备的
        Fault fault = nodeService.getFault(deviceId, faultId);//设备名字，设备故障码
        if (fault != null) {//如果故障存在
            List<SopRelationDto> relationDtos = sopService.getRelationByFaultId(deviceId, faultId);

            functionService.saveRecord(BehavioralRecordBo.RecordCode.EXPORT_DATA.getMessage(), relationDtos.toString());
            return Result.success(relationDtos);//导出数据
        } else return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);
    }

    @PostMapping("/delete/sop/{sopId}")//删除一个设备的Sop信息
    public Result deleteSopBySopId(@PathVariable String sopId) {
        Sop sop = sopService.getSop(sopId);
        if (sop == null)
            return Result.failure(Result.ResultCode.SOP_NOT_EXITED);
        sopService.deleteSop(sopId);

        functionService.saveRecord(BehavioralRecordBo.RecordCode.DELETE_SOP.getMessage(), sop.toString());
        return Result.success(sop);//删除成功
    }


    @PostMapping("/upload/sop/picture/{sopId}")//上传图片或视频
    public Result uploadFaultPicture(@RequestBody MultipartFile file,
                                     @PathVariable String sopId) {
        Sop sop = sopService.getSop(sopId);//获得这个故障
        if (sop == null)
            return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);//故障不存在
        String fileUrl = sopUrl + sop.getSopId() + "\\" + UUID.randomUUID()
                + "." + FunctionService.getFileExtension(file.getOriginalFilename());
        nodeService.saveFile(file, fileUrl);//异步调用

        fileUrl.replace("\\", "/");
        return Result.success(fileUrl);
    }

    @PostMapping("/import/sops/{deviceId}/{faultId}")//导入Sop信息
    public Result addSops(@RequestBody List<SopDto> sopDtos
            , @PathVariable String deviceId, @PathVariable String faultId) {//按照json添加

        Fault fault = nodeService.getFault(deviceId, faultId);//
        if (fault == null) {//如果这个故障不存在
            return org.example.function.Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);//故障不存在
        }
        sopDtos.stream().map((SopDto sopDto)
                -> {
            Sop sop = sopService.addSop(sopDto);
            functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_SOP.getMessage(), sop.toString());
            return sopDto;
        });
        return org.example.function.Result.success(sopDtos);//返回自身
    }


}
