package com.offcn.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.offcn.model.product.SpuSaleAttr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {

    // 根据spuId 查询销售属性集合
    List<SpuSaleAttr> selectSpuSaleAttrList(Long spuId);

}