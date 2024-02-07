package org.example.service;


import org.example.dao.interfer.DeviceRepository;
import org.example.dao.relation.DeviceToFaultRepository;
import org.example.dao.relation.DeviceToMaintenanceRepository;
import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Maintenance;
import org.example.entity.relation.DeviceToFault;
import org.example.entity.relation.DeviceToMaintenance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RelationService {



    @Resource
    DeviceRepository deviceRepository;
    @Resource
    DeviceToFaultRepository deviceToFaultRepository;
    @Resource
    DeviceToMaintenanceRepository deviceToMaintenanceRepository;


    public void addDeviceToFault(Fault fault){
        System.out.println(fault);
        Device device=deviceRepository.findByDeviceId(fault.getDeviceId());
        System.out.println(device);
        DeviceToFault deviceToFault=new DeviceToFault(device,fault);
        System.out.println(deviceToFault);
        deviceToFaultRepository.save(deviceToFault);//保存关系

    }


    public void addDeviceToMaintenance(Maintenance maintenance){
        Device device=deviceRepository.findByDeviceId(maintenance.getDeviceId());
        DeviceToMaintenance deviceToMaintenance=new DeviceToMaintenance(device,maintenance);
        System.out.println(deviceToMaintenance);
        deviceToMaintenanceRepository.save(deviceToMaintenance);//保存关系

    }







}
