package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-09 21:36
 **/
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    //去添加经纪人页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map) {

        //将房源的id放到request域中
        map.put("houseId", houseId);
        //调用AdminService中获取所有用户的方法
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);

        return "housebroker/create";
    }


    //新增经纪人提交
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker) {
        //调用AdminService中的方法查询经纪人的完整信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        //调用HouseBrokerService中保存的方法
        houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    //去修改经纪人的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        //调用HouseBrokerService中根据id查询经纪人的方法
        HouseBroker broker = houseBrokerService.getById(id);
        map.put("houseBroker", broker);
        List<Admin> adminList = adminService.findAll();
        map.put("adminList", adminList);
        return "housebroker/edit";
    }


    //更新经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker) {
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    //删除经纪人
    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("brokerId") Long brokerId) {
        houseBrokerService.delete(brokerId);
        return "redirect:/house/" + houseId;

    }


}
