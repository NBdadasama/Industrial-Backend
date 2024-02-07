package org.example;

import com.huaban.analysis.jieba.WordDictionary;
import org.example.controller.dto.UserDto;
import org.example.dao.mapper.UserMapper;
import org.example.dao.interfer.DeviceRepository;
import org.example.dao.interfer.FaultRepository;
import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.example.function.JieBa;
import org.example.function.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootNeo4jApplicationTests {


    @Autowired
    private DeviceRepository deviceRepository;


    @Autowired
    private FaultRepository faultRepository;



    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisUtil redisUtil;


    @Test
    public void createDevice() {//创建设备
        Device device = new Device();
        device.setDeviceId("01000100");
        device.setName("大型冲压机1");

        deviceRepository.save(device);//保存节点
    }


    @Test
    public void selectFault() {//查询错误关系

        System.out.println(deviceRepository.findFaultByDeviceId("01000100"));

    }

    @Test
    public void findDeviceByDeviceId() {//查设备根据设备id

        Device device=deviceRepository.findByDeviceId("01000100");//保存节点
        System.out.println(device);
    }

    @Test
    public void findOneDeviceByDeviceId() {//查设备根据设备id

        Fault fault=deviceRepository.findOneFaultByDeviceId("01000100","AAAAI");//保存节点
        System.out.println(fault);
    }




    @Test
    public void jiebaTest(){//测试分词器


        Path path = Paths.get("src/main/resources/fkck.txt");
        WordDictionary.getInstance().loadUserDict(path);

        System.out.println(JieBa.participle("红灯闪烁"));
    }


    @Test
    public void getAllNode() {//查找所有
        Result result =deviceRepository.getAllNode();//保存节点
        System.out.println(result);
    }

    @Test
    public void getAllRelation() {//查找所有
        Result result =deviceRepository.getAllRelation();//保存节点
        System.out.println(result);
    }







    @Test
    public void getFault() {//查找所有
        Fault fault=faultRepository.findByFaultId("AAAII");//保存节点
        System.out.println(fault);
    }


    @Test
    public void testRedis() {//测试redis


        //redisUtil.set("111",new UserDto("111","111","sadas","sdsad","111"),60*60);

        //System.out.println(redisUtil.get("111"));


        UserDto userDto = (UserDto) redisUtil.get("JWT111111");

        System.out.println(userDto);

    }



    @Test
    public void testBCryptPasswordEncoder1() {//测试加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        /**
         * encode()：加密方法，传入一个明文，加密后返回一个密文
         * 同一明文，每次调用encode()方法生成出来的密文都是不一样的，
         * 因为内部进行加密的时候，会生成一个【随机的加密盐】，
         * 底层是通过【加密盐】和原文进行一系列处理之后再进行加密
         * 这样的话，虽然明文一样，但是每一次的密文都是不一样的
         */
        String encode_pwd_1 = passwordEncoder.encode("123456");
        System.out.println(encode_pwd_1);
    }


    @Test
    public void testBCryptPasswordEncoder2() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean flag_true = passwordEncoder.
                matches("123456", "$2a$10$zkNXFw91x9UhNLWacycKZ.IMTtivHQQiEPG92dAMfbKlUy8mIxlpC");

        boolean flag_false = passwordEncoder.
                matches("111111", "$2a$10$zkNXFw91x9UhNLWacycKZ.IMTtivHQQiEPG92dAMfbKlUy8mIxlpC");


        System.out.println(flag_true);
        System.out.println(flag_false);
    }



    @Test
    public void testDeCode() {//解码测试
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    }





}
