package org.example.service;


import org.example.controller.vo.DeviceVo;
import org.example.controller.vo.FaultVo;
import org.example.controller.vo.SopVo;
import org.example.controller.vo.relation.RelationVo;
import org.example.dao.interfer.DeviceRepository;
import org.example.dao.interfer.FaultRepository;
import org.example.dao.interfer.SopRepository;
import org.example.dao.relation.DeviceToFaultRepository;
import org.example.dao.relation.FaultToSopRepository;
import org.example.dao.relation.SopToSopRepository;
import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Sop;
import org.example.entity.relation.DeviceToFault;
import org.example.entity.relation.FaultToSop;
import org.example.entity.relation.SopToSop;
import org.neo4j.ogm.model.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeShowService {

    @Resource
    DeviceRepository deviceRepository;
    @Resource
    FaultRepository faultRepository;

    @Resource
    SopRepository sopRepository;
    @Resource
    DeviceToFaultRepository deviceToFaultRepository;

    @Resource
    FaultToSopRepository faultToSopRepository;
    @Resource
    SopToSopRepository sopToSopRepository;




    public List<DeviceVo> getAllDevice() {
        List<Device> deviceList = deviceRepository.findAllDevice();
        List<DeviceVo> deviceVos = deviceList.stream().map((Device device)
                -> {
            return new DeviceVo(device);
        }).collect(Collectors.toList());
        return deviceVos;
    }


    public List<FaultVo> getAllFault() {
        List<Fault> faultList = faultRepository.findAllFault();
        List<FaultVo> faultVos = faultList.stream()
                .map((Fault fault) -> {
                    return new FaultVo(fault);
                }).collect(Collectors.toList());

        return faultVos;
    }

    public List<SopVo> getAllSop() {
        List<Sop> sopList = sopRepository.findAllSop();
        List<SopVo> sopVos = sopList.stream().map((Sop sop) ->
        {
            return new SopVo(sop);
        }).collect(Collectors.toList());
        return sopVos;
    }

    public List<RelationVo> getAllDeviceToFault() {
        List<DeviceToFault> relationVoList = (List<DeviceToFault>) deviceToFaultRepository.findAll();
        List<RelationVo> relationVos = relationVoList.stream().map((DeviceToFault deviceToFault) ->
        {
            return new RelationVo(deviceToFault);
        }).collect(Collectors.toList());
        return relationVos;
    }

    public List<RelationVo> getAllFaultToSop() {
        List<FaultToSop> faultToSopList = (List<FaultToSop>) faultToSopRepository.findAll();
        List<RelationVo> relationVos = faultToSopList.stream().map((FaultToSop faultToSop) ->
        {
            return new RelationVo(faultToSop);
        }).collect(Collectors.toList());
        return relationVos;
    }

    public List<RelationVo> getAllSopToSop() {
        List<SopToSop> sopToSopList = (List<SopToSop>) sopToSopRepository.findAll();
        List<RelationVo> relationVos = sopToSopList.stream().map((SopToSop sopToSop) ->
        {
            return new RelationVo(sopToSop);
        }).collect(Collectors.toList());
        return relationVos;
    }

    public Result getAllRelation() {
        return deviceRepository.getAllRelation();
    }
}
