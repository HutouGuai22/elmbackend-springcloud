package com.eleme.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eleme.common.entity.Food;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
