package com.eleme.business.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eleme.business.mapper.CartMapper;
import com.eleme.common.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public Cart getCartByFoodIdAndUserId(Integer foodId, Integer userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("foodId", foodId).eq("userId", userId);
        return cartMapper.selectOne(queryWrapper);
    }

    public boolean addCart(Cart cart) {
        cart.setQuantity(1);
        return cartMapper.insert(cart) > 0;
    }

    public boolean addQuantity(Integer cartId) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            return cartMapper.updateById(cart) > 0;
        }
        return false;
    }

    public boolean substrateQuantity(Integer cartId) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart != null) {
            if (cart.getQuantity() > 1) {
                cart.setQuantity(cart.getQuantity() - 1);
                return cartMapper.updateById(cart) > 0;
            } else {
                return cartMapper.deleteById(cartId) > 0;
            }
        }
        return false;
    }

    public List<Cart> getCartByUserIdAndBusinessId(Integer userId, Integer businessId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("userId", userId).eq("businessId", businessId);
        return cartMapper.selectList(queryWrapper);
    }

    public boolean clearCart(Integer userId, Integer businessId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("userId", userId).eq("businessId", businessId);
        return cartMapper.delete(queryWrapper) >= 0;
    }
}
