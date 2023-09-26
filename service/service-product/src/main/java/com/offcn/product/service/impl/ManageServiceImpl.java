package com.offcn.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.model.product.*;
import com.offcn.product.mapper.*;
import com.offcn.product.service.ManageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Override
    public List<BaseCategory1> getCategory1() {
        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        // select * from baseCategory2 where Category1Id = ?
        QueryWrapper queryWrapper = new QueryWrapper<BaseCategory2>();
        queryWrapper.eq("category1_id",category1Id);
        List<BaseCategory2> baseCategory2List = baseCategory2Mapper.selectList(queryWrapper);
        return baseCategory2List;
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        // select * from baseCategory3 where Category2Id = ?
        QueryWrapper queryWrapper = new QueryWrapper<BaseCategory3>();
        queryWrapper.eq("category2_id",category2Id);
        List<BaseCategory3> baseCategory3List = baseCategory3Mapper.selectList(queryWrapper);
        return baseCategory3List;
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        // 调用mapper：
        return baseAttrInfoMapper.selectBaseAttrInfoList(category1Id, category2Id, category3Id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //判断有id是修改 无id是添加
        if (baseAttrInfo.getId() != null){
            //修改数据
            baseAttrInfoMapper.updateById(baseAttrInfo);
        }else {
            //新增数据
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        //baseAttrValue 平台属性值
        // 修改：通过先删除{baseAttrValue}，在新增的方式！
        // 删除条件：baseAttrValue.attrId = baseAttrInfo.id
        QueryWrapper queryWrapper = new QueryWrapper<BaseAttrValue>();
        queryWrapper.eq("attr_id",baseAttrInfo.getId());
        baseAttrValueMapper.delete(queryWrapper);

        //获取页面传递过来所有平台属性数值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList != null && attrValueList.size() > 0){
            //循环遍历
            for (BaseAttrValue baseAttrValue : attrValueList){
                //获取平台属性id 给attrId
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }
    }

    /**
     * 查询平台属性方法实现类
     * @param attrId
     * @return
     */
    @Override
    public BaseAttrInfo getAttrInfo(Long attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);
        //查询到最新的平台属性值集合数据放入到平台属性中
        baseAttrInfo.setAttrValueList(getAttrValueList(attrId));
        return baseAttrInfo;
    }

    @Override
    public IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> pageParam, SpuInfo spuInfo) {
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id", spuInfo.getCategory3Id());
        queryWrapper.orderByDesc("id");
        return spuInfoMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 根据属性id获取属性值
     * @param attrId
     * @return
     */
    private List<BaseAttrValue> getAttrValueList(Long attrId){
        // select * from baseAttrValue where attrId = ?
        QueryWrapper queryWrapper = new QueryWrapper<BaseAttrValue>();
        queryWrapper.eq("attr_Id",attrId);
        List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectList(queryWrapper);
        return baseAttrValueList;
    }
}