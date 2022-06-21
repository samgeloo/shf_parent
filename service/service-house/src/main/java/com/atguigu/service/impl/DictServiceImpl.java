package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import com.atguigu.dao.DictDao;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.samge.service.impl
 * @Description :
 * @date : 2022-06-04 21:54
 **/

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    private DictDao dictDao;


    @Override
    public List<Map<String, Object>> findeZnodes(Long id) {
        //根据父id查询该节点下所有的子节点
        List<Dict> dictList = dictDao.findListParentId(id);
        //创建返回的List
        ArrayList<Map<String, Object>> zNodes = new ArrayList<>();
        //遍历dictList
        for (Dict dict : dictList) {
            //  返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
            //创建一个Map
            Map map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("name", dict.getName());
            //调用DictDao中判断该节点是否是父节点的方法
            Integer count = dictDao.isParentNode(dict.getId());
            map.put("isParent", count > 0 ? true : false);
            //将map放到返回List中
            zNodes.add(map);
        }

        return zNodes;
    }

    @Override
    public List<Dict> getListByParentId(Long parentId) {
        return dictDao.findListParentId(parentId);
    }

    @Override
    public List<Dict> getListByDictCode(String dictCode) {
        //调用DictDao中根据编码得到Dict对象的方法
        Dict dict = dictDao.getDictByDictCode(dictCode);
        if (null == dict) {
            return null;
        }
        //根据父id查询所有子节点的方法
        List<Dict> listByParentId = this.getListByParentId(dict.getId());
        return listByParentId;
    }


    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }
}
