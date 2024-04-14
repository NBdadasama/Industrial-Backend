package org.example.service.ind;


import org.example.controller.vo.father.NodeVo;
import org.example.controller.vo.ind.IndDeviceVo;
import org.example.controller.vo.ind.IndInspectionVo;
import org.example.controller.vo.ind.IndMaintenanceVo;
import org.example.dao.mapper.industral.neo4j.IndDeviceRepository;
import org.example.dao.mapper.industral.neo4j.IndInspectionRepository;
import org.example.dao.mapper.industral.neo4j.IndMaintenanceRepository;
import org.example.dao.mapper.industral.relation.IndDeviceToIndDeviceRepository;
import org.example.dao.mapper.industral.relation.IndDeviceToIndInspectionRepository;
import org.example.dao.mapper.industral.relation.IndDeviceToIndMaintenanceRepository;
import org.example.entity.industral.neo4j.IndDevice;
import org.example.entity.industral.neo4j.IndInspection;
import org.example.entity.industral.neo4j.IndMaintenance;
import org.neo4j.ogm.model.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndNodeService {


    @Resource
    private IndDeviceRepository indDeviceRepository;
    @Resource
    private IndInspectionRepository indInspectionRepository;
    @Resource
    private IndMaintenanceRepository indMaintenanceRepository;
    @Resource
    private IndDeviceToIndDeviceRepository indDeviceToIndDeviceRepository;
    @Resource
    private IndDeviceToIndInspectionRepository indDeviceToIndInspectionRepository;
    @Resource
    private IndDeviceToIndMaintenanceRepository indDeviceToIndMaintenanceRepository;



    public List<IndDeviceVo> getDeviceVoLimit(int limit){



        List<IndDevice> indDevices=indDeviceRepository.findByLimit(limit);

        List<IndDeviceVo> indDeviceVos=indDevices.stream().map(n->{
            return new IndDeviceVo(n);
        }).collect(Collectors.toList());

        return indDeviceVos;
    }


    public List<IndDeviceVo> getDeviceVoByDeviceId(Long deviceId) {

        List<IndDevice> indDevices=indDeviceRepository.findByDeviceId(deviceId);

        List<IndDeviceVo> indDeviceVos=indDevices.stream().map(n->{
            return new IndDeviceVo(n);
        }).collect(Collectors.toList());
        return indDeviceVos;
    }




    public List<IndInspectionVo> getInspectionByDeviceId(Long deviceId) {

        List<IndInspection> inspections=indInspectionRepository.findByDeviceId(deviceId);
        List<IndInspectionVo> indInspectionVos=inspections.stream().map(n->{
            return new IndInspectionVo(n);
        }).collect(Collectors.toList());
        return indInspectionVos;
    }


    public List<IndMaintenanceVo> getMaintenanceByDeviceId(Long deviceId) {

        List<IndMaintenance> indMaintenances=indMaintenanceRepository.findByDeviceId(deviceId);
        List<IndMaintenanceVo> indMaintenanceVos=indMaintenances.stream().map(n->{
            return new IndMaintenanceVo(n);
        }).collect(Collectors.toList());
        return indMaintenanceVos;
    }


    public Result getAllRelationByDeviceId(Long deviceId){


        return indDeviceToIndDeviceRepository.findByDeviceId(deviceId);//查找所有关系

    }





    public List<NodeVo> getAllNodeByDeviceId(Long deviceId) {

        List<NodeVo> nodeVos=indDeviceRepository.findByDeviceId(deviceId).stream().map(n->{
            return new IndDeviceVo(n);
        }).collect(Collectors.toList());//拼接device
        nodeVos.addAll(indInspectionRepository.findByDeviceId(deviceId).stream().map(n->{
            return new IndInspectionVo(n);//拼接
        }).collect(Collectors.toList()));

        nodeVos.addAll(indMaintenanceRepository.findByDeviceId(deviceId).stream().map(n->{
            return new IndMaintenanceVo(n);//拼接
        }).collect(Collectors.toList()));


        return nodeVos;
    }

    public List<IndDeviceVo> getDeviceVoByPage(int pageNumber, int pageSize) {

        List<IndDevice> indDevices=indDeviceRepository.findByPage(pageNumber-1,pageSize);

        List<IndDeviceVo> indDeviceVos=indDevices.stream().map(n->{
            return new IndDeviceVo(n);
        }).collect(Collectors.toList());
        return indDeviceVos;
    }


    public List<IndDeviceVo> getParentDeviceVoByPage(int pageNumber, int pageSize) {

        List<IndDevice> indDevices=indDeviceRepository.findParentIndDeviceByPage(pageNumber-1,pageSize);
        List<IndDeviceVo> indDeviceVos=indDevices.stream().map(n->{
            return new IndDeviceVo(n);
        }).collect(Collectors.toList());
        return indDeviceVos;
    }

    public List<IndInspectionVo> getInspectionByPage(int pageNumber, int pageSize) {

        List<IndInspection> indInspections=indInspectionRepository.findByPage(pageNumber-1,pageSize);

        List<IndInspectionVo> indInspectionVos=indInspections.stream().map(n->{
            return new IndInspectionVo(n);
        }).collect(Collectors.toList());

        return indInspectionVos;
    }


    public List<IndMaintenanceVo> getIndMaintenanceByPage(int pageNumber, int pageSize) {

        List<IndMaintenance> indMaintenances=indMaintenanceRepository.findByPage(pageNumber-1,pageSize);

        List<IndMaintenanceVo> indMaintenanceVos=indMaintenances.stream().map(n->{
            return new IndMaintenanceVo(n);
        }).collect(Collectors.toList());

        return indMaintenanceVos;
    }

}
