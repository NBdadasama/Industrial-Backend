package org.example.service;


import org.example.controller.vo.CategoryToNameVo;
import org.example.dao.mapper.CategoryToNameMapper;
import org.example.entity.obj1.CategoryToName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigurationService {//功能类

    @Resource
    CategoryToNameMapper categoryToNameMapper;




    public List<CategoryToNameVo> getCategoryToName() {

        List<CategoryToName> categoryToNames = categoryToNameMapper.selectList(null);
        return categoryToNames.stream().map((CategoryToName categoryToName) -> {
            return new CategoryToNameVo(categoryToName);
        }).collect(Collectors.toList());

    }









}
