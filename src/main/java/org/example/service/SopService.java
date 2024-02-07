package org.example.service;


import org.example.controller.dto.FaultDto;
import org.example.controller.dto.SopDto;
import org.example.controller.dto.SopRelationDto;
import org.example.controller.vo.relation.RelationVo;
import org.example.dao.interfer.FaultRepository;
import org.example.dao.interfer.SopRepository;
import org.example.dao.mapper.SopIdMapper;
import org.example.dao.relation.FaultToSopRepository;
import org.example.dao.relation.SopToSopRepository;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Sop;
import org.example.entity.obj1.SopId;
import org.example.entity.relation.FaultToSop;
import org.example.entity.relation.SopToSop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class SopService {


    @Resource
    SopIdMapper sopIdMapper;

    @Resource
    SopRepository sopRepository;

    @Resource
    FaultRepository faultRepository;

    @Resource
    FaultToSopRepository faultToSOPRepository;


    @Resource
    SopToSopRepository sopToSOPRepository;




    public String getSopId(){//自动编号的SOPid

        int id=sopIdMapper.selectMaxId();


        String newString = String.format("%08d", id);
        SopId sopId=new SopId(null,id+1);
        sopIdMapper.insert(sopId);//更新id
        return newString;

    }


    public Sop getSop(String sopId){

        return sopRepository.findBySopID(sopId);//根据sopId获得Sop
    }



    public Sop addSop(SopDto sopDto){

        Sop sop=new Sop(sopDto);//赋值
        sop.setSopId(this.getSopId());//自动获取SopId，确保不会重复
        sopRepository.save(sop);//保存这个Sop节点

        return sop;
    }


    public void deleteSop(String sopId){
        sopRepository.deleteBySopId(sopId);//删除这个节点

    }



    public FaultToSop addFaultToSOP(SopRelationDto sopRelationDto) {//增加关系

        Fault fault=faultRepository.findByFaultId(sopRelationDto.getFaultId());//找到错误
        Sop sop=sopRepository.findBySopID(sopRelationDto.getTo());
        FaultToSop faultToSOP=new FaultToSop(fault,sop,sopRelationDto);
        System.out.println(faultToSOP);
        return faultToSOPRepository.save(faultToSOP);//保存数据

    }

    public SopToSop addSOPToSOP(SopRelationDto sopRelationDto) {//增加关系
        Sop from=sopRepository.findBySopID(sopRelationDto.getFrom());//获得来节点
        Sop to=sopRepository.findBySopID(sopRelationDto.getTo());//获得去节点
        SopToSop sopToSOP=new SopToSop(from,to,sopRelationDto);
        return sopToSOPRepository.save(sopToSOP);//保存节点

    }


    public List<SopDto> getSopsByFaultId(String deviceId, String faultId) {


        List<Sop> sopDtos= sopRepository.findAllSopByFaultID(deviceId,faultId);
        return sopDtos.stream().map((Sop sop)->
        {return new SopDto(sop);}).collect(Collectors.toList());//返回这个类

    }

    public List<SopRelationDto> getRelationByFaultId(String deviceId, String faultId) {

        List<SopToSop> sopToSops = sopRepository.findAllSopToSopRelation(deviceId, faultId);
        return sopToSops.stream().map((SopToSop SopToSop) ->
                {
                    return new SopRelationDto(SopToSop);
                }
        ).collect(Collectors.toList());//返回这个类
    }
}
