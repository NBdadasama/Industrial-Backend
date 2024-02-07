package org.example.controller;

import org.example.function.Result;
import org.example.service.ConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
@RestController
@RequestMapping("/configuration")
@CrossOrigin(origins = "*")
public class ConfigurationController {


    @Resource
    private ConfigurationService configurationService;


    @GetMapping("/get/category/to/name")//获得配置信息
    public Result getCategoryToName() {
        return Result.success(configurationService.getCategoryToName());
    }


}
