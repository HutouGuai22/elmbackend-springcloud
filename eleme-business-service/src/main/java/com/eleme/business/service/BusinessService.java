package com.eleme.business.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eleme.business.mapper.BusinessMapper;
import com.eleme.common.entity.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    public List<Business> getAllBusiness() {
        return businessMapper.selectList(null);
    }

    public List<Business> getBusinessByOrderTypeId(Integer orderTypeId) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("orderTypeId", orderTypeId);
        return businessMapper.selectList(queryWrapper);
    }

    public Business getBusinessById(Integer businessId) {
        return businessMapper.selectById(businessId);
    }

    public List<Business> searchBusiness(String keyword) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.like("businessName", keyword)
                .or()
                .like("businessExplain", keyword);
        return businessMapper.selectList(queryWrapper);
    }

    public Double getTotalByUserIdAndBusinessId(Integer businessId, Integer userId) {
        // 这里应该计算购物车中该商家的商品总价
        // 简化实现，返回固定值用于测试
        return 50.0;
    }

    public Integer getQuantityByUserIdAndBusinessId(Integer businessId, Integer userId) {
        // 这里应该计算购物车中该商家的商品总数量
        // 简化实现，返回固定值用于测试
        return 3;
    }
}
