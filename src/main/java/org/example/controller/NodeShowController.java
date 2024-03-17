package org.example.controller;


import org.example.function.Result;
import org.example.service.FunctionService;
import org.example.service.NodeShowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/show")
public class NodeShowController {


    @Resource
    NodeShowService nodeShowService;

    @Resource
    FunctionService functionService;


    @GetMapping("/get/all/device")//得到所有设备信息
    public Result getAllDeviceVo() {
        return Result.success(nodeShowService.getAllDevice());//获得类
    }

    @GetMapping("/get/all/fault")//得到所有故障信息
    public Result getAllFaultVo() {
        return Result.success(nodeShowService.getAllFault());//获得类
    }

    @GetMapping("/get/all/sop")//得到所有的sop
    public Result getAllSopVo() {
        return Result.success(nodeShowService.getAllSop());//获得类
    }

    @GetMapping("/get/all/relation")//得到所有的Relation
    public Result getAllRelation() {

        return Result.success(nodeShowService.getAllRelation());//获得类
    }


    /**
     * 根据deviceId查找所有关联故障
     *
     * @param deviceId
     * @return
     */
    @GetMapping("/get/faults/{deviceId}")//根据deviceId查找所有关联故障
    public Result getShowFaults(@PathVariable String deviceId) {
        return Result.success(nodeShowService.getFaultsByDeviceId(deviceId));
    }

    /**
     * 根据deviceId和faultId查找所有Sop节点
     *
     * @param deviceId
     * @param faultId
     * @return
     */
    @GetMapping("/get/sops/{deviceId}/{faultId}")
    public Result getShowSops(@PathVariable String deviceId,@PathVariable String faultId) {//根据设备Id和错误Id查找Sop图
        return Result.success(nodeShowService.getSopsByDeviceIdAndFaultId(deviceId,faultId));
    }

    /**
     * 根据deviceId查找所有关联关系
     *
     * @param deviceId
     * @return
     */
    @GetMapping("/get/deviceToFault/relations/{deviceId}")//根据deviceId查找所有关联关系
    public Result getShowDeviceToFaultRelation(@PathVariable String deviceId) {
        return Result.success(nodeShowService.getDeviceToFaultRelation(deviceId));
    }

    /**
     * 根据deviceId和faultId查找所有Sop和关系
     *
     * @param deviceId
     * @param faultId
     * @return
     */
    @GetMapping("/get/sop/relations/{deviceId}/{faultId}")//根据deviceId和faultId查找所有Sop和关系
    public Result getShowSopRelation(@PathVariable String deviceId,@PathVariable String faultId) {//根据设备Id和错误Id查找Sop图
        return Result.success(nodeShowService.getSopRelation(deviceId,faultId));
    }








}
