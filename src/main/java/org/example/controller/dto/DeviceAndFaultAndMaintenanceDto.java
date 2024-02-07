package org.example.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Maintenance;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAndFaultAndMaintenanceDto {

    private DeviceDto deviceDto;

    private List<FaultDto> faultDtos;

    private List<MaintenanceDto> maintenanceDtos;//三个类可以直接导入的类

    public DeviceAndFaultAndMaintenanceDto(Device device, List<Fault> faults, List<Maintenance> maintenances) {


        this.deviceDto=new DeviceDto(device);
        this.faultDtos=faults.stream().map((Fault fault)->
        {return new FaultDto(fault);}).collect(Collectors.toList());
        this.maintenanceDtos=maintenances.stream().map((Maintenance maintenance)->
        {return new MaintenanceDto(maintenance);}).collect(Collectors.toList());//流编程赋值

    }
}
