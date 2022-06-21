package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-04 21:16
 **/
@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;


    //去展示数据字典的页面
    @RequestMapping
    public String index() {
        return "dict/index";
    }

    //获取数字字典中的数据
    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id) {
        //调用DictService中查询数据字典中的数据的方法
        List<Map<String, Object>> zNode = dictService.findeZnodes(id);
        return Result.ok(zNode);
    }

    //根据父id获取所有子节点
    @ResponseBody
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long id) {
        //调用DictService中根据父id查询所有子节点的方法
        List<Dict> listByParentId = dictService.getListByParentId(id);
        return Result.ok(listByParentId);
    }


}
