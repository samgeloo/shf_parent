package com.atguigu.dao;

import com.atguigu.entity.Dict;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.samge.dao
 * @Description :
 * @date : 2022-06-04 22:00
 **/
public interface DictDao extends BaseDao<Dict> {
    //根据父id查询该节点下所有的子节点
    List<Dict> findListParentId(Long id);
    //根据父id判断该节点是否是父节点
    Integer isParentNode(Long id);

    //根据编码获取Dict对象
    Dict getDictByDictCode(String dictCode);
    //根据id获取name
    String getNameById(Long id);



}
