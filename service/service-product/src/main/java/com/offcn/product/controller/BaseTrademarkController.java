package com.offcn.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.offcn.common.result.Result;
import com.offcn.model.product.BaseTrademark;
import com.offcn.product.service.BaseTrademarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "品牌管理接口")
@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

    @Autowired
    private BaseTrademarkService baseTrademarkService;

    @GetMapping("getTrademarkList")
    public Result<List<BaseTrademark>> getTrademarkList(){
        List<BaseTrademark> baseTrademarkList = baseTrademarkService.list(null);
        return Result.ok(baseTrademarkList);
    }


    @ApiOperation(value = "分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",paramType = "path",dataType = "Long"),
            @ApiImplicitParam(name = "limit",value = "每页显示记录数",paramType = "path",dataType = "Long")
    })
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable("page") Long page,
                        @PathVariable("limit") Long limit){
        Page<BaseTrademark> pageParam = new Page<>(page, limit);
        IPage<BaseTrademark> pageModel = baseTrademarkService.getPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取BaseTrademark")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.ok(baseTrademark);
    }

    @ApiOperation(value = "新增BaseTrademark")
    @PostMapping("save")
    public Result save(@RequestBody BaseTrademark banner) {
        baseTrademarkService.save(banner);
        return Result.ok();
    }

    @ApiOperation(value = "修改BaseTrademark")
    @PutMapping("update")
    public Result updateById(@RequestBody BaseTrademark banner) {
        baseTrademarkService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(value = "删除BaseTrademark")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        baseTrademarkService.removeById(id);
        return Result.ok();
    }
}
