package org.example.controller;


import org.example.function.Result;
import org.example.service.FunctionService;
import org.example.service.NodeShowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
