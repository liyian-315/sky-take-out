package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询对应套餐id
     * @param dishIds
     * @return
     */
    //select setmeal_id from setmeal_dish where dish_id in (1,2,3,4) 动态sql
    List<Long> getSetMealIdsByDishIds(List<Long> dishIds);

    /**
     * 新增套餐对应的菜品
     * @param setmealDishes
     */
    @AutoFill(value = OperationType.INSERT)
    void inserBatch(List<SetmealDish> setmealDishes);

    /**
     * 套餐根据套餐id对应菜品
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getDishBySetmealId(Long id);

    /**
     * 根据套餐id删除菜品
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where dish_id=#{setmealId}")
    void deleteBySetmealId(Long setmealId);
}
