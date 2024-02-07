package org.example.service;


import org.example.controller.dto.DeviceDto;
import org.example.controller.dto.FaultDto;
import org.example.controller.dto.MaintenanceDto;
import org.example.controller.vo.FaultVo;
import org.example.dao.interfer.*;
import org.example.dao.mapper.ColorMapper;
import org.example.dao.mapper.SopIdMapper;
import org.example.dao.relation.FaultToSopRepository;
import org.example.dao.relation.SopToSopRepository;
import org.example.entity.obj.*;
import org.example.entity.obj1.Color;
import org.neo4j.ogm.model.Result;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeService {//device，fault，节点类

    @Resource
    private DeviceRepository deviceRepository;
    @Resource
    private FaultRepository faultRepository;


    @Resource
    private SopRepository sopRepository;

    @Resource
    private MaintenanceRepository maintenanceRepository;

    @Resource
    private ColorMapper colorMapper;

    @Resource
    RelationService relationService;


    public Result getAllNode() {
        return deviceRepository.getAllNode();
    }

    public Result getAllRelation() {

        return deviceRepository.getAllRelation();
    }


    public Device addDevice(DeviceDto deviceDto) {//增加一个设备
        Device device = new Device(deviceDto);
        if (deviceRepository.findByDeviceId(device.getDeviceId()) == null) {
            device = deviceRepository.save(device);//如果设备不存在
            colorMapper.insert(new Color(null, device.getNodeId(), device.getCategory(), device.getColor()));
            return device;
        } else return null;
    }


    public Fault addFault(FaultDto faultDto) {//增加一个故障
        Fault fault = new Fault(faultDto);


        if (faultRepository.findByFaultId(fault.getFaultId()) == null) {
            fault = faultRepository.save(fault);
            colorMapper.insert(new Color(null, fault.getNodeId(), fault.getCategory(), fault.getColor()));
            return fault;
        } else return null;
    }


    public Maintenance addMaintenance(MaintenanceDto maintenanceDto) {

        Maintenance maintenance = new Maintenance(maintenanceDto);
        if (maintenanceRepository.findByMaintenanceId(maintenance.getMaintenanceId()) == null)//这个节点不存在
        {
            maintenance = maintenanceRepository.save(maintenance);
            colorMapper.insert(new Color(null, maintenance.getNodeId(), maintenance.getCategory(), maintenance.getColor()));
            return maintenance;
        } else return null;
    }


    public void deleteDeviceByDeviceId(String deviceId) {
        deviceRepository.deleteRelatedByDeviceId(deviceId);//先删除相关节点
        deviceRepository.deleteDeviceByDeviceId(deviceId);//再删除整个设备
    }


    public void deleteFaultByFaultId(String deviceId, String faultId) {
        faultRepository.deleteByFaultId(deviceId, faultId);
        sopRepository.deleteByDeviceIdAndFaultId(deviceId, faultId);
    }

    public void deleteMaintenanceByMaintenanceId(String deviceId, String maintenanceId) {
        maintenanceRepository.deleteByMaintenanceId(deviceId, maintenanceId);

    }


    public void addDevices(List<DeviceDto> deviceDtos) {//批量导入device
        deviceDtos.stream().map((DeviceDto deviceDto) -> {
            this.addDevice(deviceDto);
            return null;
        });
    }


    public List<FaultVo> addFaults(List<FaultDto> faultDtos) {//批量导入device
        return faultDtos.stream().map((FaultDto faultDto) -> {
            Fault fault = this.addFault(faultDto);//增加故障
            relationService.addDeviceToFault(fault);//增加关系
            return new FaultVo(fault);
        }).collect(Collectors.toList());
    }

    public void addMaintenances(List<MaintenanceDto> maintenanceDtos) {//批量导入device
        maintenanceDtos.stream().map((MaintenanceDto maintenanceDto) -> {
            Maintenance maintenance = this.addMaintenance(maintenanceDto);//增加故障
            relationService.addDeviceToMaintenance(maintenance);//增加关系
            return null;
        });
    }


    public Device getDevice(String deviceId) {//获得一个设备
        return deviceRepository.findByDeviceId(deviceId);
    }//查找一个人的信息


    public Fault getFault(String deviceId, String faultId) {//增加一个设备

        return faultRepository.findByDeviceIdAndFaultId(deviceId, faultId);
    }//查找一个设备的信息


    public Maintenance getMaintenance(String deviceId, String maintenanceId) {
        return maintenanceRepository.findByDeviceIdAndMaintenanceId(deviceId, maintenanceId);
    }


    public List<Fault> getFaultByDeviceId(String deviceId) {//查找所有关联device的设备
        return faultRepository.findAllByDeviceId(deviceId);
    }


    public List<Maintenance> getMaintenanceByDeviceId(String deviceId) {//查找所有关联device的设备
        return maintenanceRepository.findAllByDeviceId(deviceId);
    }


    public List<Device> getAllDevice() {//查找所有设备信息,分页查找
        return (List<Device>) deviceRepository.findAll();
    }


    public List<Device> findDeviceByPage(int pageId, int number) {

        List<Device> deviceList = deviceRepository.findByPage(pageId, number);
        return deviceList;//根据页码查询
    }


    public Device modifyDevice(Device newDevice) {
        return deviceRepository.save(newDevice);

    }

    public Fault modifyFault(Fault newFault) {
        return faultRepository.save(newFault);

    }


    public Maintenance modifyMaintenance(Maintenance newMaintenance) {
        return maintenanceRepository.save(newMaintenance);
    }


    @Async//异步方法可
    public void saveFile(MultipartFile file, String fileUrl) {//保存文件

        try {
            // 确保目标文件夹存在
            File directory = new File(fileUrl).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();//创建目录
            }
            // 生成唯一的文件名（如果需要）
            // 保存文件到指定路径
            file.transferTo(new File(fileUrl));//异步调用

            System.out.println( "文件保存成功！"+fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "文件保存失败"+fileUrl);
        }

    }
}
