package com.atguigu.controller;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUsingIndexClause;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.controller
 * @Description :
 * @date : 2022-06-06 21:00
 **/
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    @Reference
    private HouseService houseService;


    //分页及带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        //将filters放到request域中
        map.put("filters", filters);
        PageInfo<House> pageInfo = houseService.findPage(filters);
        map.put("page", pageInfo);
        List<Community> communityList = communityService.findAll();
        List<Dict> houseTypeList = dictService.getListByDictCode("houseType");
        List<Dict> floorList = dictService.getListByDictCode("floor");
        List<Dict> buildStructureList = dictService.getListByDictCode("buildStructure");
        List<Dict> directionList = dictService.getListByDictCode("direction");
        List<Dict> decorationList = dictService.getListByDictCode("decoration");
        List<Dict> houseUseList = dictService.getListByDictCode("houseUse");

        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
        return "house/index";
    }


    //去添加业务
    @RequestMapping("/create")
    public String goAddPage(Map map) {
        List<Community> communityList = communityService.findAll();
        List<Dict> houseTypeList = dictService.getListByDictCode("houseType");
        List<Dict> floorList = dictService.getListByDictCode("floor");
        List<Dict> buildStructureList = dictService.getListByDictCode("buildStructure");
        List<Dict> directionList = dictService.getListByDictCode("direction");
        List<Dict> decorationList = dictService.getListByDictCode("decoration");
        List<Dict> houseUseList = dictService.getListByDictCode("houseUse");

        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
        return "house/create";
    }

    //添加房源
    @RequestMapping("/save")
    public String save(House house) {
        houseService.insert(house);
        return "common/successPage";
    }


    //修改页面
    @RequestMapping("/edit/{id}")
    public String goEdit(@PathVariable("id") Long id, Map map) {
        House house = houseService.getById(id);
        map.put("house", house);

        List<Community> communityList = communityService.findAll();
        List<Dict> houseTypeList = dictService.getListByDictCode("houseType");
        List<Dict> floorList = dictService.getListByDictCode("floor");
        List<Dict> buildStructureList = dictService.getListByDictCode("buildStructure");
        List<Dict> directionList = dictService.getListByDictCode("direction");
        List<Dict> decorationList = dictService.getListByDictCode("decoration");
        List<Dict> houseUseList = dictService.getListByDictCode("houseUse");

        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
        return "house/edit";
    }

    //修改
    @PostMapping("/update")
    public String update(House house) {
        houseService.update(house);
        return "common/successPage";
    }


    //删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        houseService.delete(id);
        return "redirect:/house";
    }

    //发布和取消发布
    @GetMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable("houseId") Long houseId, @PathVariable("status") Integer status) {
        houseService.publish(houseId, status);
        return "redirect:/house";
    }


    //去详情页面
    @GetMapping("/{houseId}")
    public String show(@PathVariable("houseId") Long houseId, Map map) {
        //调用HouseService中根据id查询房源的方法
        House house = houseService.getById(houseId);
        map.put("house", house);
        Community community = communityService.getById(house.getCommunityId());
        map.put("community", community);
        //查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 1);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 2);
        //查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokerByHouseId(houseId);
        //查询房东信息
        List<HouseUser> houseUserList = houseUserService.getHouseUsersByHouseId(houseId);
        map.put("houseImage1List", houseImage1List);
        map.put("houseImage2List", houseImage2List);
        map.put("houseBrokerList", houseBrokerList);
        map.put("houseUserList", houseUserList);

        return "house/show";
    }


}
