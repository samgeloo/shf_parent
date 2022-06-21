package com.atguigu;

import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ：samgeloo
 * @version ：v1.0.0
 * @Package : com.atguigu
 * @Description :
 * @date : 2022-06-06 20:30
 **/
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class DictTest {
    @Autowired
    private DictDao dictDao;


    @Test
    public void testFindListByParentId() {
        List<Dict> listParentId = dictDao.findListParentId(1L);
        for (Dict dict : listParentId) {
            System.out.println("dict = " + dict);
        }
    }


}
