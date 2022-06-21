package com.atguigu.comtroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;

import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.comtroller
 * @Description :
 * @date : 2022-06-11 17:46
 **/
@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    //根据编码获取所有的子节点
    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode) {
        //调用DictService中根据编码获取所有的子节点的方法
        List<Dict> listByDictCode = dictService.getListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    //根据父id查询所有额的子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable("areaId") Long areaId) {
        //调用DictService中根据编码获取所有子节点的方法
        List<Dict> listByParentId = dictService.getListByParentId(areaId);
        return Result.ok(listByParentId);
    }

}
