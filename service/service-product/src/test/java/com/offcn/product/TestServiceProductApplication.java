package com.offcn.product;

import com.offcn.model.product.BaseCategory1;
import com.offcn.product.mapper.BaseAttrInfoMapper;
import com.offcn.product.mapper.BaseCategory1Mapper;
import com.offcn.product.mapper.BaseCategory2Mapper;
import com.offcn.product.mapper.BaseCategory3Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestServiceProductApplication {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    //测试一级分类
    @Test
    public void testQueryBaseCategory1FindAll(){
        List<BaseCategory1> baseCategory1s = baseCategory1Mapper.selectList(null);
        for(BaseCategory1 baseCategory1 :baseCategory1s){
            System.out.println("一级分类名字 = " + baseCategory1.getName());
        }

    }
    //测试二级分类

    //测试三级分类

    //测试获取平台属性数据

}
