package org.example.entity.industral.sql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Father {



    private Long createBy;

    private Timestamp createTime;

    private Long updateBy;

    private Timestamp updateTime;

    private String remark;

    private int isDeleted;

    private Long tenantId;




}
