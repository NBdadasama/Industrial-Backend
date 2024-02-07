package org.example.controller;


import com.alibaba.fastjson.JSONObject;
import org.example.controller.dto.*;
import org.example.controller.vo.DeviceVo;
import org.example.controller.vo.FaultVo;
import org.example.controller.vo.MaintenanceVo;
import org.example.dao.mapper.BehavioralRecordMapper;
import org.example.entity.obj.*;
import org.example.service.FunctionService;
import org.example.function.Result;
import org.example.service.NodeService;
import org.example.service.RelationService;
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
public class NodeController {//节点类

    @Resource
    NodeService nodeService;

    @Resource
    RelationService relationService;

    @Resource
    BehavioralRecordMapper behavioralRecordMapper;

    @Resource
    FunctionService functionService;

    @Value("${device.file.url}")
    String deviceUrl;

    @Value("${fault.file.url}")
    String faultUrl;

    @GetMapping("/get/all/relation")//获得所有相关的关系
    public Result getAllRelation() {//获得所有关系
        return Result.success(nodeService.getAllRelation());
    }


    @PostMapping("/add/device")//添加一个设备
    public Result addDevice(@RequestBody DeviceDto deviceDto) {
        Device device = nodeService.addDevice(deviceDto);//插入
        if (device == null)
            return Result.failure(Result.ResultCode.DEVICE_EXISTED);//设备已经存在
        DeviceVo deviceVo = new DeviceVo(device);

        functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_DEVICE.getMessage(), device.toString());
        //记录此次操作
        return Result.success(deviceVo);//添加设备
    }


    @PostMapping("/add/fault")//添加一个故障信息
    public Result addFault(@RequestBody FaultDto faultDto) {
        Fault fault = nodeService.addFault(faultDto);
        if (fault == null)
            return org.example.function.Result.failure(Result.ResultCode.FAULT_EXISTED);//如果已经存在错误
        relationService.addDeviceToFault(fault);//增加关系
        functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_FAULT.getMessage(), fault.toString());//记录此次操作
        FaultVo faultVo = new FaultVo(fault);//生成返回值错误
        return Result.success(faultVo);//添加设备
    }


    @PostMapping("/add/maintenance")//添加一个保养信息
    public Result addMaintenance(@RequestBody MaintenanceDto maintenanceDto) {
        Maintenance maintenance = nodeService.addMaintenance(maintenanceDto);
        if (maintenance == null)
            return org.example.function.Result.failure(Result.ResultCode.MAINTENANCE_EXISTED);//如果已经存在错误
        relationService.addDeviceToMaintenance(maintenance);//添加关系
        functionService.saveRecord(BehavioralRecordBo.RecordCode.ADD_MAINTENANCE.getMessage(), maintenance.toString());//记录此次操作
        MaintenanceVo maintenanceVo = new MaintenanceVo(maintenance);//生成返回值
        return Result.success(maintenanceVo);//
    }


    @PostMapping("/delete/device/{deviceId}")//删除设备
    public Result deleteDevice(@PathVariable String deviceId) {


        Device device = nodeService.getDevice(deviceId);

        if (device != null) {//如果有这个设备
            nodeService.deleteDeviceByDeviceId(deviceId);//删除节点
            functionService.saveRecord(BehavioralRecordBo.RecordCode.DELETE_NODE.getMessage(), device.toString());//记录此次操作
            return Result.success("删除成功");
        } else return Result.failure(Result.ResultCode.DELETE_NODE_FAILURE);//删除失败

    }


    @PostMapping("/delete/fault/{deviceId}/{faultId}")//删除故障信息
    public Result deleteFault(@PathVariable String deviceId, @PathVariable String faultId) {


        Fault fault = nodeService.getFault(deviceId, faultId);

        if (fault != null) {//如果有这个设备

            nodeService.deleteFaultByFaultId(deviceId, faultId);

            functionService.saveRecord(BehavioralRecordBo.RecordCode.DELETE_NODE.getMessage(), fault.toString());//记录此次操作
            return Result.success("删除成功");

        } else return Result.failure(Result.ResultCode.DELETE_NODE_FAILURE);//删除失败

    }


    @PostMapping("/delete/maintenance/{deviceId}/{maintenanceId}")//删除保养信息
    public Result deleteMaintenance(@PathVariable String deviceId,
                                    @PathVariable String maintenanceId) {
        Maintenance maintenance = nodeService.getMaintenance(deviceId, maintenanceId);

        if (maintenance != null) {//如果有这个设备
            nodeService.deleteMaintenanceByMaintenanceId(deviceId, maintenanceId);
            functionService.saveRecord(BehavioralRecordBo.RecordCode.DELETE_NODE.getMessage(), maintenance.toString());//记录此次操作
            return Result.success("删除成功");

        } else return Result.failure(Result.ResultCode.DELETE_NODE_FAILURE);//删除失败

    }


    @PostMapping("/modify/device")//修改设备信息
    public Result modifyDevice(@RequestBody DeviceDto deviceDto) {
        Device device = nodeService.getDevice(deviceDto.getDeviceId());
        if (device != null) {//如果存在设备
            Device newDevice = new Device(deviceDto);
            newDevice.setNodeId(device.getNodeId());
            newDevice = nodeService.modifyDevice(newDevice);//修改设备节点
            functionService.saveRecord(BehavioralRecordBo.RecordCode.MODIFY_NODE.getMessage(), newDevice.toString());
            return Result.success(new DeviceVo(newDevice));
        } else return Result.failure(Result.ResultCode.DEVICE_NOT_EXISTED);//删除失败

    }


    @PostMapping("/modify/fault")//修改故障信息
    public Result modifyFault(@RequestBody FaultDto faultDto) {
        Fault fault = nodeService.getFault(faultDto.getDeviceId(), faultDto.getFaultId());
        if (fault != null) {//如果故障信息存在
            Fault newFault = new Fault(faultDto);
            newFault.setNodeId(fault.getNodeId());
            newFault = nodeService.modifyFault(newFault);//修改设备节点
            functionService.saveRecord(BehavioralRecordBo.RecordCode.MODIFY_NODE.getMessage(), newFault.toString());//修改记录
            return Result.success(new FaultVo(newFault));
        } else return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);//删除失败

    }

    @PostMapping("/modify/maintenance")//修改保养信息
    public org.example.function.Result modifyMaintenance(@RequestBody MaintenanceDto maintenanceDto) {

        Maintenance maintenance = nodeService.getMaintenance(maintenanceDto.getDeviceId(), maintenanceDto.getMaintenanceId());
        if (maintenance != null) {//如果不是空
            Maintenance newMaintenance = new Maintenance(maintenanceDto);
            newMaintenance.setNodeId(maintenance.getNodeId());
            newMaintenance = nodeService.modifyMaintenance(newMaintenance);
            functionService.saveRecord(BehavioralRecordBo.RecordCode.MODIFY_NODE.getMessage(), newMaintenance.toString());
            return org.example.function.Result.success(new MaintenanceVo(newMaintenance));
        } else return org.example.function.Result.failure(Result.ResultCode.MainTENANCE_NOT_EXISTED);//删除失败
    }

    @GetMapping("/get/device/{deviceId}")//根据设备id获取设备
    public Result getDevice(@PathVariable String deviceId) {//查找一个设备
        Device device = nodeService.getDevice(deviceId);
        if (device == null)
            return Result.failure(Result.ResultCode.DEVICE_NOT_EXISTED);//设备不存在
        return Result.success(new DeviceVo(device));//返回Vo给用户
    }

    @GetMapping("/get/fault/{deviceId}/{faultId}")//根据deviceid和错误id查找故障
    public Result getFault(@PathVariable String deviceId, @PathVariable String faultId) {//查找一个错误

        Fault fault = nodeService.getFault(deviceId, faultId);
        if (fault == null)
            return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);
        return Result.success(new FaultVo(fault));
    }


    @GetMapping("/get/maintenance/{deviceId}/{maintenanceId}")//根据deviceId和错误id查找保养信息
    public Result getMaintenance(@PathVariable String deviceId, @PathVariable String maintenanceId) {//查找一个维护
        Maintenance maintenance = nodeService.getMaintenance(deviceId, maintenanceId);
        if (maintenance == null)
            return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);
        return Result.success(new MaintenanceVo(maintenance));
    }

    @GetMapping("/get/by/page/devices")//分页查询设备
    public Result getDeviceByPage(@RequestParam(required = false, defaultValue = "0") int pageId,
                                  @RequestParam(required = false, defaultValue = "10") int number) {//分页查找
        return Result.success(nodeService.findDeviceByPage(pageId, number));
    }


    @GetMapping("/export/device/{deviceId}")//导出一个设备的json文件
    public Result exportNodes(@PathVariable String deviceId) {

        Device device = nodeService.getDevice(deviceId);
        if (device == null)
            return Result.failure(Result.ResultCode.DEVICE_NOT_EXISTED);//设备不存在
        List<Fault> faults = nodeService.getFaultByDeviceId(deviceId);
        List<Maintenance> maintenances = nodeService.getMaintenanceByDeviceId(deviceId);
        DeviceAndFaultAndMaintenanceDto deviceAndFaultAndMaintenanceDto =
                new DeviceAndFaultAndMaintenanceDto(device, faults, maintenances);
        functionService.saveRecord(BehavioralRecordBo.RecordCode.EXPORT_DATA.getMessage(),
                deviceAndFaultAndMaintenanceDto.toString());

        return Result.success(deviceAndFaultAndMaintenanceDto);//导出节点
    }

    @PostMapping("/import/nodes")//批量导入节点
    public org.example.function.Result importNodes(DeviceAndFaultAndMaintenanceDto deviceAndFaultAndMaintenanceDto) {

        nodeService.addDevice(deviceAndFaultAndMaintenanceDto.getDeviceDto());
        nodeService.addFaults(deviceAndFaultAndMaintenanceDto.getFaultDtos());
        nodeService.addMaintenances(deviceAndFaultAndMaintenanceDto.getMaintenanceDtos());
        functionService.saveRecord(BehavioralRecordBo.RecordCode.IMPORT_DATA.getMessage(),
                deviceAndFaultAndMaintenanceDto.toString());
        return Result.success("插入成功");
    }


    @PostMapping("/upload/device/picture/{deviceId}")//上传图片
    public Result uploadDevicePicture(@RequestBody MultipartFile file,
                                      @PathVariable String deviceId) {

        Device device = nodeService.getDevice(deviceId);
        if (device == null) {//如果不为空
            return Result.failure(Result.ResultCode.DEVICE_NOT_EXISTED);//节点不存在
        }
        String fileUrl = deviceUrl + device.getName() + "\\" + UUID.randomUUID().toString()
                + "." + FunctionService.getFileExtension(file.getOriginalFilename());
        nodeService.saveFile(file, fileUrl);//异步调用


        functionService.saveRecord(BehavioralRecordBo.RecordCode.UPLOAD_PICTURE.getMessage(), fileUrl);

        fileUrl.replace("\\", "/");

        return Result.success(fileUrl);
    }

    @PostMapping("/upload/fault/picture/{deviceId}/{faultId}")//上传图片
    public Result uploadFaultPicture(@RequestBody MultipartFile file,
                                     @PathVariable String deviceId, @PathVariable String faultId) {

        Fault fault = nodeService.getFault(deviceId, faultId);//获得这个故障
        if (fault == null)
            return Result.failure(Result.ResultCode.FAULT_NOT_EXISTED);//故障不存在
        String fileUrl = faultUrl + fault.getName() + "\\" + UUID.randomUUID().toString()
                + "." + FunctionService.getFileExtension(file.getOriginalFilename());


        nodeService.saveFile(file, fileUrl);//异步调用

        functionService.saveRecord(BehavioralRecordBo.RecordCode.UPLOAD_PICTURE.getMessage(), fileUrl);

        fileUrl.replace("\\", "/");
        return Result.success(fileUrl);
    }


    @PostMapping("/add/nodes")//批量导入节点
    public Result addDevices(@RequestBody JSONObject param) {//导入json字符


        JSONObject deviceObject = param.getJSONObject("device");

        return Result.success();//导出节点
    }


}
