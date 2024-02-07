package org.example;


import org.example.controller.dto.DeviceDto;
import org.example.dao.interfer.DeviceRepository;
import org.example.dao.interfer.FaultRepository;
import org.example.entity.obj.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddData {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private FaultRepository faultRepository;




    @Test
    public void createDevice() {//创建设备

        DeviceDto deviceDto=new
                DeviceDto("10001","file//",
                "奥克斯主流定频，变频挂机","奥斯克挂式空调","");
        Device device = new Device(deviceDto);
        deviceRepository.save(device);//保存节点
    }














}
