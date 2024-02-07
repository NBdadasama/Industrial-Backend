package org.example.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.relation.SopToSop;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SopRelationDto {//表示不同的


    private String from;
    private String to;//

    private String describe;
    private String faultId;//错误id，隶属于哪个故障

    private String deviceId;//错误id，隶属于哪个故障

    private int category;//类别

    public SopRelationDto(SopToSop sopToSop) {
        this.from=sopToSop.getFrom().getSopId();
        this.to=sopToSop.getTo().getSopId();
        this.describe= sopToSop.getDescribe();
        this.faultId=sopToSop.getFaultId();
        this.deviceId=sopToSop.getDeviceId();
        this.category=0;

    }
}
