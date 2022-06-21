package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-10 9:54
 **/
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {
    @Reference
    private HouseUserService houseUserService;

    //添加房东的页面
    @RequestMapping("/create")
    public String goAddPage(@RequestParam("houseId") Long houseId, Map map) {
        //将房源id放到request域中
        map.put("houseId", houseId);
        return "houseuser/create";
    }

    //添加放到
    @RequestMapping("save")
    public String save(HouseUser houseUser) {
        //调用HouseUserService中添加的方法
        houseUserService.insert(houseUser);
        return "common/successPage";
    }

    //去修改首页
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
        //调用HouseUserService中根据id查询的方法
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser", houseUser);
        return "houseuser/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        //调用HouseUserService中更新的方法
        houseUserService.update(houseUser);
        return "common/successPage";
    }


    @RequestMapping("/delete/{houseId}/{houseUserId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("houseUserId") Long houseUserId) {
        //调用HouseUserService中删除的方法
        houseUserService.delete(houseUserId);
        return "redirect:/house/"+houseId;
    }




}
