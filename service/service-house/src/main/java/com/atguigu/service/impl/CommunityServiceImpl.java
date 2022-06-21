package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service.impl
 * @Description :
 * @date : 2022-06-05 14:50
 **/
@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private DictDao dictDao;

    @Autowired
    private CommunityDao communityDao;



    @Override
    protected BaseDao getEntityDao() {
        return communityDao;
    }




    /**
     * 重写分页的方法，目的是为了给小区中的区域和板块的名字赋值
     * @param filters
     * @return
     */
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {

        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);
        Page<Community> page = communityDao.findPage(filters);
        //遍历page对象
        for (Community community : page) {
            //根据区域id获取区域的名字
            String areaName = dictDao.getNameById(community.getAreaId());
            //根据板块id获取板块的名字
            String plateName = dictDao.getNameById(community.getPlateId());
            //给community对象的区域和板块赋值
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<>(page, 10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);
        //根据区域id获取区域的名字
        String areaName = dictDao.getNameById(community.getAreaId());
        //根据板块id获取板块的名字
        String plateName = dictDao.getNameById(community.getPlateId());
        //给community对象的区域和板块赋值
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;
    }
}
