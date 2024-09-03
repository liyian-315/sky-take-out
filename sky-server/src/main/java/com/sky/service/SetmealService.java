package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {


    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult PageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 分页查询
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐
     * @param id
     */
    SetmealVO getBySetmealId(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    void StartOrStop(Integer status, Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void delete(List<Long> ids);
}
