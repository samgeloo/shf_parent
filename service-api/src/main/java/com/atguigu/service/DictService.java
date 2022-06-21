package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu.service
 * @Description :
 * @date : 2022-06-04 21:57
 **/
public interface DictService extends BaseService<Dict> {

    //查询数字字典中的数据，通过zTree进行渲染
    List<Map<String, Object>> findeZnodes(Long id);

    //根据父节点id获取所有该节点下的子节点
    List<Dict> getListByParentId(Long parentId);

    //根据编码获取该节点下所有的子节点
    List<Dict> getListByDictCode(String dictCode);
}
