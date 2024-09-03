package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult PageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //查询套餐
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        Page<Setmeal> page = setmealMapper.PageQuery(setmealPageQueryDTO);

        List<Setmeal> result = page.getResult();
        long total = page.getTotal();

        //套餐包含的菜品
        return new PageResult(total,result);
    }

    /**
     * 新增菜品
     * @param setmealDTO
     */
    @Override
    public void save(SetmealDTO setmealDTO) {
        // 保存套餐
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setmealMapper.inser(setmeal);
        //保存对应菜品
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes !=null && setmealDishes.size()>0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.inserBatch(setmealDishes);
        }
    }

    /**
     * 根据id查询套餐
     * @param id
     */
    @Override
    public SetmealVO getBySetmealId(Long id) {
        //查询套餐表
        Setmeal setmeal = setmealMapper.getBySetmealId(id);
        //查询菜品表
        List<SetmealDish> setmealDishes = setmealDishMapper.getDishBySetmealId(id);
        //组合为VO
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        //修改套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        //更新对应的setmeal_dish列表
        Long setmealId = setmealDTO.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //TODO 先删除
        setmealDishMapper.deleteBySetmealId(setmealId);
        //TODO 再添加
        if(setmealDishes != null && setmealDishes.size()>0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.inserBatch(setmealDishes);
        }
    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    @Override
    public void StartOrStop(Integer status, Long id) {
        //判断套餐中是否包含未起售菜品
        List<SetmealDish> dishes = setmealDishMapper.getDishBySetmealId(id);
        dishes.forEach(dish -> {
            if(Objects.equals(dishMapper.getById(dish.getDishId()).getStatus(), StatusConstant.DISABLE)){
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        });
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();

        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        //判断套餐是否在起售中，起售中不能删除
        for(Long id : ids){
            Setmeal setmeal = setmealMapper.getBySetmealId(id);
            if(Objects.equals(setmeal.getStatus(), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        for(Long id : ids){
            //根据id删除setmeal表
            setmealMapper.delete(id);
            //根据setmeal_id删除setmeal_dish表 delete from setmeal where id in (1,2,3...)
            setmealDishMapper.deleteBySetmealId(id);
        }

    }
}
