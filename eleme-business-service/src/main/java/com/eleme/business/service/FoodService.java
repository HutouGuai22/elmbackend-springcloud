package com.eleme.business.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eleme.business.mapper.CartMapper;
import com.eleme.business.mapper.FoodMapper;
import com.eleme.common.entity.Cart;
import com.eleme.common.entity.Food;
import com.eleme.business.vo.FoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private CartMapper cartMapper;

    public List<FoodVO> getFoodByBusinessId(Integer businessId) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("businessId", businessId);
        List<Food> foods = foodMapper.selectList(queryWrapper);

        List<FoodVO> foodVOs = new ArrayList<>();
        for (Food food : foods) {
            FoodVO foodVO = new FoodVO();
            foodVO.setFoodId(food.getFoodId());
            foodVO.setFoodName(food.getFoodName());
            foodVO.setFoodExplain(food.getFoodExplain());
            foodVO.setFoodImg(food.getFoodImg());
            foodVO.setFoodPrice(food.getFoodPrice());
            foodVO.setBusinessId(food.getBusinessId());
            foodVO.setRemarks(food.getRemarks());

            // 设置空的购物车信息
            Cart cart = new Cart();
            cart.setQuantity(0);
            foodVO.setCart(cart);

            foodVOs.add(foodVO);
        }

        return foodVOs;
    }

    public List<FoodVO> getFoodByBusinessIdAndUserId(Integer businessId, Integer userId) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("businessId", businessId);
        List<Food> foods = foodMapper.selectList(queryWrapper);

        List<FoodVO> foodVOs = new ArrayList<>();
        for (Food food : foods) {
            FoodVO foodVO = new FoodVO();
            foodVO.setFoodId(food.getFoodId());
            foodVO.setFoodName(food.getFoodName());
            foodVO.setFoodExplain(food.getFoodExplain());
            foodVO.setFoodImg(food.getFoodImg());
            foodVO.setFoodPrice(food.getFoodPrice());
            foodVO.setBusinessId(food.getBusinessId());
            foodVO.setRemarks(food.getRemarks());

            // 查询购物车信息
            QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
            // 明确指定数据库字段名
            cartQueryWrapper.eq("foodId", food.getFoodId())
                    .eq("userId", userId);
            Cart cart = cartMapper.selectOne(cartQueryWrapper);
            if (cart == null) {
                cart = new Cart();
                cart.setQuantity(0);
            }
            foodVO.setCart(cart);

            foodVOs.add(foodVO);
        }

        return foodVOs;
    }

    public Food getFoodById(Integer foodId) {
        return foodMapper.selectById(foodId);
    }
}
