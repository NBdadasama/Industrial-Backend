package org.example.controller.ind;


import org.example.controller.vo.ind.IndDeviceVo;
import org.example.function.Result;
import org.example.service.ind.IndNodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RestController
@RequestMapping("/ind/node")
@CrossOrigin(origins = "*")
public class IndNodeController {

    @Resource
    private IndNodeService indNodeService;


    /**分页获取deviceVo
     * @param pageNumber
     * @param pageSize
     * @return List<DeviceVo>
     */
    @PostMapping("/get/device/by/page")//
    public Result getIndDeviceNodeByPage(@RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){//限制个数
        return Result.success(indNodeService.getDeviceVoByPage(pageNumber,pageSize));
    }


    /**分页获取 父deviceVo
     * @param pageNumber
     * @param pageSize
     * @return  List<DeviceVo>
     */
    @PostMapping("/get/parent/device/by/page")//
    public Result getParentIndDeviceNodeByPage(@RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){//限制个数
        return Result.success(indNodeService.getParentDeviceVoByPage(pageNumber,pageSize));

    }


    /**分页查询
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping("/get/inspection/by/page")//
    public Result getIndInspectionNodeByPage(@RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){//限制个数
        return Result.success(indNodeService.getInspectionByPage(pageNumber,pageSize));

    }


    /**分页查询
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PostMapping("/get/maintenance/by/page")//
    public Result getIndMaintenanceNodeByPage(@RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                                             @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){//限制个数
        return Result.success(indNodeService.getIndMaintenanceByPage(pageNumber,pageSize));

    }




    /**获取父device
     * @param limit
     * @return 返回List<deviceVo>
     */
    @GetMapping("/get/device/{limit}")//
    public Result getIndDeviceNodeLimit(@PathVariable int limit){//限制个数
        return Result.success(indNodeService.getDeviceVoLimit(limit));

    }


    /**根据deviceId返回所有相关设备
     * @param deviceId
     * @return List<deviceVo>
     */
    @GetMapping("/get/device/by/deviceId/{deviceId}")//
    public Result getDeviceByDeviceId(@PathVariable Long deviceId){

        return Result.success(indNodeService.getDeviceVoByDeviceId(deviceId));
    }


    /**根据deviceId获取所有相关的检查
     * @param deviceId
     * @return List<inspectionVo>
     */
    @GetMapping("/get/inspection/by/deviceId/{deviceId}")//
    public Result getInspectionByDeviceId(@PathVariable Long deviceId){

        return Result.success(indNodeService.getInspectionByDeviceId(deviceId));
    }


    /**
     * @param deviceId
     * @return List<IndMaintenanceVo>
     */
    @GetMapping("/get/maintenance/by/deviceId/{deviceId}")//
    public Result getMaintenanceByDeviceId(@PathVariable Long deviceId){
        return Result.success(indNodeService.getMaintenanceByDeviceId(deviceId));
    }


    /**
     * @param deviceId
     * @return 返回所有关系
     */
    @GetMapping("/get/all/relation/by/deviceId/{deviceId}")//
    public Result getAllRelationByDeviceId(@PathVariable Long deviceId){
        return Result.success(indNodeService.getAllRelationByDeviceId(deviceId));
    }


    /**获得所有需要展示的节点
     * @param deviceId
     * @return NodeVo
     */
    @GetMapping("/get/all/node/by/deviceId/{deviceId}")//
    public Result getAllNodeByDeviceId(@PathVariable Long deviceId){
        return Result.success(indNodeService.getAllNodeByDeviceId(deviceId));
    }






}
