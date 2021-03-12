package com.barry.controller;

import com.barry.enums.YesOrNo;
import com.barry.pojo.Carousel;
import com.barry.pojo.Category;
import com.barry.pojo.vo.CategoryVO;
import com.barry.pojo.vo.NewItemsVO;
import com.barry.service.CarouselService;
import com.barry.service.CategoryService;
import com.barry.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
@Api(value = "首页",tags = "首页展示相关接口")
public class IndexController {

    @Autowired
    private CarouselService carouselServicel;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel(){

        List<Carousel> list = carouselServicel.queryAll(YesOrNo.YES.Type);

        return JSONResult.ok(list);
    }

    @ApiOperation(value = "用户获取商品分类",notes = "1级分类",httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats(){

        List<Category> list = categoryService.queryAllRootLevelCat();

        return JSONResult.ok(list);
    }
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(list);
    }
    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("//{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(list);
    }



}
