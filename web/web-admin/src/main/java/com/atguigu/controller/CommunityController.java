package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-05 15:04
 **/

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    @Reference
    private DictService dictService;

    @Reference
    private CommunityService communityService;

    //分页及带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放到request域中
        map.put("filters", filters);
        //调用CommunityService中分页的方法
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        //将pageInfo放到request域中
        map.put("page", pageInfo);
        //根据编码湖区北京所有的区
        List<Dict> areaList = dictService.getListByDictCode("beijing");
        //将北京所有的区域放到request域中
        map.put("areaList", areaList);
        return "community/index";
    }


    //去添加小区的页面
    @RequestMapping("/create")
    public String goAddPage(Map map) {
        List<Dict> areaList = dictService.getListByDictCode("beijing");
        //将北京所有的区域放到request域中
        map.put("areaList", areaList);
        return "community/create";
    }

    //添加小区
    @RequestMapping("/save")
    public String save(Community community) {
        communityService.insert(community);
        return "common/successPage";
    }

    //去修改小区的页面
    @RequestMapping("/edit/{id}")
    public String goEditPage(@PathVariable("id") Long id, Map map) {
            //根据编码获取北京所有的区
        List<Dict> areaList= dictService.getListByDictCode("beijing");
        //将北京所有的区域放到request域中
        map.put("areaList", areaList);
        //调用CommunityService中查询的方法
        Community community = communityService.getById(id);
        map.put("community", community);
        return "community/edit";
    }

    //更新
    @RequestMapping("/update")
    public String update(Community community) {
        //调用CommunityService中更新的方法
        communityService.update(community);
        return "common/successPage";
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        return "redirect:/community";

    }



}
