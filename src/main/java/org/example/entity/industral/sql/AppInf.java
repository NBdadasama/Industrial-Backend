package org.example.entity.industral.sql;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("em_asset_bom")
public class AppInf extends Father{


    @TableId
    private Long id;
    private Long factoryId;

    private Long assetId;//部位id
    private String boneCode;
    private String boneName;
    private int seq;//序号
    private Long parentId;//父节点
    private String description;//描述

    private Long sort;//分类



}
