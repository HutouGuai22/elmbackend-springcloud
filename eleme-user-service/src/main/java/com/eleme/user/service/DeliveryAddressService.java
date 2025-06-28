package com.eleme.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eleme.common.entity.DeliveryAddress;
import com.eleme.user.mapper.DeliveryAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAddressService {

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    public List<DeliveryAddress> getAddressByUserId(Integer userId) {
        QueryWrapper<DeliveryAddress> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("userId", userId);
        return deliveryAddressMapper.selectList(queryWrapper);
    }

    public DeliveryAddress getAddressById(Integer daId) {
        return deliveryAddressMapper.selectById(daId);
    }

    public boolean addAddress(DeliveryAddress address) {
        return deliveryAddressMapper.insert(address) > 0;
    }

    public boolean updateAddress(DeliveryAddress address) {
        return deliveryAddressMapper.updateById(address) > 0;
    }

    public boolean deleteAddress(Integer daId) {
        return deliveryAddressMapper.deleteById(daId) > 0;
    }
}
