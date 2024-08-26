package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "分类相关接口")
@RequestMapping("admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page (CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询");
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @ApiOperation("修改分类")
    @PutMapping
    public Result update (@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类,{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 启用或禁用分类
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("启用或禁用分类")
    @PostMapping("status/{status}")
    public Result startOrStop (@PathVariable Integer status,Long id){
        log.info("启用或禁用分类");
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result save (@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类");
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result delete (Long id){
        log.info("根据id删除分类");
        categoryService.delete(id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> getByType(Integer type){
        log.info("根据类型查询分类");
        List<Category> list = categoryService.getByType(type);
        return Result.success(list);
    }
}
