package com.offcn.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.common.result.Result;
import com.offcn.model.product.BaseSaleAttr;
import com.offcn.model.product.BaseTrademark;
import com.offcn.model.product.SpuInfo;
import com.offcn.product.service.BaseTrademarkService;
import com.offcn.product.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product")
public class SpuManageController {

    @Autowired
    private ManageService manageService;

    @Autowired
    private BaseTrademarkService baseTrademarkService;

    // 根据查询条件封装控制器
    // springMVC 的时候，有个叫对象属性传值 如果页面提交过来的参数与实体类的参数一致，
    // 则可以使用实体类来接收数据
    // http://api.omall.com/admin/product/1/10?category3Id=61
    // @RequestBody 作用 将前台传递过来的json{"category3Id":"61"}  字符串变为java 对象。
    @GetMapping("{page}/{size}")
    public Result getSpuInfoPage(@PathVariable("page") Long page,
                                 @PathVariable("size") Long size,
                                 SpuInfo spuInfo){
        // 创建一个Page 对象
        Page<SpuInfo> spuInfoPage = new Page<>(page,size);
        // 获取数据
        IPage<SpuInfo> spuInfoPageList = manageService.getSpuInfoPage(spuInfoPage, spuInfo);
        // 将获取到的数据返回即可！
        return Result.ok(spuInfoPageList);
    }

    // 销售属性http://api.omall.com/admin/product/baseSaleAttrList
    @GetMapping("baseSaleAttrList")
    public Result baseSaleAttrList(){
        //查询所有的销售属性集合
        List<BaseSaleAttr> baseSaleAttrList = manageService.getBaseSaleAttrList();
        return Result.ok(baseSaleAttrList);
    }


    /**
     * 保存spu
     * @param spuInfo
     * @return
     */
    @PostMapping("saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        // 调用服务层的保存方法
        manageService.saveSpuInfo(spuInfo);
        return Result.ok();
    }
}
