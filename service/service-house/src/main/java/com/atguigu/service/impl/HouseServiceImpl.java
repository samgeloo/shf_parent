package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service.impl
 * @Description :
 * @date : 2022-06-06 21:11
 **/
@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;
    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }


    @Override
    public void publish(Long houseId, Integer status) {
        House house = new House();
        house.setId(houseId);
        house.setStatus(status);
        houseDao.update(house);
    }

    @Override
    public PageInfo<HouseVo> findList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //调用HouseDao中前端分页及带条件查询的方法
        Page<HouseVo> page = houseDao.findPageList(houseQueryVo);
        //遍历page
        for (HouseVo houseVo : page) {
            //获取房屋类型
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            //获取楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            //获取朝向
            String directionName = dictDao.getNameById(houseVo.getDirectionId());
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page, 5);
    }

    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        String floorName = dictDao.getNameById(house.getFloorId());
        String directionName = dictDao.getNameById(house.getDirectionId());
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        String decorationName = dictDao.getNameById(house.getDecorationId());
        String houseUseName = dictDao.getNameById(house.getHouseUseId());
        //设置
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setDirectionName(directionName);
        house.setBuildStructureName(buildStructureName);
        house.setDecorationName(decorationName);
        house.setHouseUseName(houseUseName);
        return house;
    }
}
