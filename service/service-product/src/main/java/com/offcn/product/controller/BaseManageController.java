package com.offcn.product.controller;

import com.offcn.common.result.Result;
import com.offcn.model.product.*;
import com.offcn.product.service.ManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品基础属性接口")
@RestController
@RequestMapping("admin/product")
public class BaseManageController {

    @Autowired
    private ManageService manageService;

    /**
     * 查询所有一级分类的信息
     * @return
     */
    @ApiOperation(value = "查询一级分类数据")
    @GetMapping("/getCategory1")
    public Result<List<BaseCategory1>> getCategory1(){
        List<BaseCategory1> baseCategory1List = manageService.getCategory1();
        return Result.ok(baseCategory1List);
    }

    /**
     * 查询所有二级分类的信息
     * @param category1Id
     * @return
     */
    @ApiOperation(value = "根据指定的一级分类编号,查询对应二级分类数据")
    @ApiImplicitParam(name = "category1Id", value = "一级分类编号",required = true,paramType = "path",dataType = "Long")
    @GetMapping("/getCategory2/{category1Id}")
    public Result<List<BaseCategory2>> getCategory2(@PathVariable("category1Id") Long category1Id){
        List<BaseCategory2> baseCategory2List = manageService.getCategory2(category1Id);
        return Result.ok(baseCategory2List);
    }

    /**
     * 查询所有三级分类的信息
     * @param category2Id
     * @return
     */
    @ApiOperation(value = "根据指定的二级分类编号,查询对应三级分类数据")
    @ApiImplicitParam(name = "category2Id", value = "二级分类编号",required = true,paramType = "path",dataType = "Long")
    @GetMapping("/getCategory3/{category2Id}")
    public Result<List<BaseCategory3>> getCategory3(@PathVariable("category2Id")Long category2Id){
        List<BaseCategory3> baseCategory3List = manageService.getCategory3(category2Id);
        return Result.ok(baseCategory3List);
    }

    @ApiOperation(value = "获取指定的一级分类,二级分类,三级分类 的平台属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category1Id", value = "一级分类编号",required = true,paramType = "path",dataType = "Long"),
            @ApiImplicitParam(name = "category2Id", value = "二级分类编号",required = true,paramType = "path",dataType = "Long"),
            @ApiImplicitParam(name = "category3Id", value = "三级分类编号",required = true,paramType = "path",dataType = "Long")
    })
    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result<List<BaseAttrInfo>> attrInfoList(@PathVariable("category1Id")Long category1Id,
                                                   @PathVariable("category2Id")Long category2Id,
                                                   @PathVariable("category3Id")Long category3Id){
        List<BaseAttrInfo> attrInfoList = manageService.getAttrInfoList(category1Id, category2Id, category3Id);
        return Result.ok(attrInfoList);
    }

    /**
     * 保存平台属性的方法
     * @param baseAttrInfo
     * @return
     */
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        //前台数据都被封装到该对象中baseAttrInfo
        manageService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }

    @GetMapping("getAttrValueList/{attrId}")
    public Result<List<BaseAttrValue>> getAttrValueList(@PathVariable("attrId")Long attrId){
        BaseAttrInfo attrInfo = manageService.getAttrInfo(attrId);
        List<BaseAttrValue> attrValueList = attrInfo.getAttrValueList();
        return Result.ok(attrValueList);
    }

}
