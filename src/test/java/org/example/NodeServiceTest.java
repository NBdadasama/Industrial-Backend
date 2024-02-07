package org.example;


import org.example.dao.interfer.DeviceRepository;
import org.example.dao.interfer.FaultRepository;
import org.example.dao.interfer.MaintenanceRepository;
import org.example.dao.relation.DeviceToFaultRepository;
import org.example.entity.obj.Maintenance;
import org.example.service.NodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NodeServiceTest {


    @Autowired
    NodeService nodeService;


    @Resource
    DeviceRepository deviceRepository;

    @Resource
    FaultRepository faultRepository;

    @Resource
    MaintenanceRepository maintenanceRepository;

    @Resource
    DeviceToFaultRepository deviceToFaultRepository;

    @Test
    public void selectAllNodeTest(){

        System.out.println(deviceRepository.findByDeviceId("10001"));

        System.out.println(faultRepository.findByFaultId("E1"));
        Maintenance maintenance=maintenanceRepository.findByMaintenanceId("001");

        System.out.println(maintenance);



    }

    @Test
    public void stringToListTest(){

        String str="定频机的内室温传感器标准阻值绝大多数为5KΩ。;变频机的内室温传感器标准阻值绝大多数为15KΩ;维修时请确认，不要用错，否则可能导致机器错误感知环境温度，不开机或不停机。可以通过将空调转到“送风模式”看送风模式下空调显示屏的环境温度来判断传感器准确性。;5KΩ错用为15KΩ：检测温度比实际温度低很多，会导致制热不停机，制冷不开机等。;15KΩ错用为5KΩ：检测温度比实际温度高很多，会导致制热不开机，制冷不停机等。";
        List<String> list= Arrays.stream(str.split(";")).collect(Collectors.toList());
        System.out.println(list.get(1));
    }


    @Test
    public void getAllRelationVoTest(){
        System.out.println(deviceToFaultRepository.findAll());
    }


    @Test
    public void getCodeTest() {

        for (int i = 0; i <= 100; i++)
        {
            String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            StringBuffer flag = new StringBuffer();
            for (int j = 0; j < 6; j++)
            {
                flag.append(sources.charAt(rand.nextInt(sources.length())) + "");
            }
            System.out.println(flag);
        }


    }







}
