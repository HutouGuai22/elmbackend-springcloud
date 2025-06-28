package com.eleme.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eleme.common.entity.*;
import com.eleme.order.feign.BusinessServiceFeign;
import com.eleme.order.feign.UserServiceFeign;
import com.eleme.order.mapper.OrderDetailetMapper;
import com.eleme.order.mapper.OrdersMapper;
import com.eleme.order.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailetMapper orderDetailetMapper;

    @Autowired
    private BusinessServiceFeign businessServiceFeign;

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Transactional
    public Integer createOrder(Orders order) {
        order.setOrderDate(LocalDateTime.now());
        order.setOrderState(0); // 未支付
        ordersMapper.insert(order);

        // 获取购物车商品并创建订单详情
        try {
            List<Cart> cartList = businessServiceFeign.getCartByUserIdAndBusinessId(order.getUserId(), order.getBusinessId()).getData();
            if (cartList != null) {
                for (Cart cart : cartList) {
                    OrderDetailet orderDetailet = new OrderDetailet();
                    orderDetailet.setOrderId(order.getOrderId());
                    orderDetailet.setFoodId(cart.getFoodId());
                    orderDetailet.setQuantity(cart.getQuantity());
                    orderDetailetMapper.insert(orderDetailet);
                }

                // 清空购物车
                businessServiceFeign.clearCart(order.getUserId(), order.getBusinessId());
            }
        } catch (Exception e) {
            // 如果调用失败，记录日志但不影响订单创建
            System.err.println("Failed to process cart: " + e.getMessage());
        }

        return order.getOrderId();
    }

    public Orders getOrderById(Integer orderId) {
        return ordersMapper.selectById(orderId);
    }

    public boolean updateOrderState(Integer orderId, Integer orderState) {
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setOrderState(orderState);
        return ordersMapper.updateById(order) > 0;
    }

    public List<OrderDetailet> getOrderDetailsByOrderId(Integer orderId) {
        QueryWrapper<OrderDetailet> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("orderId", orderId);
        List<OrderDetailet> orderDetailets = orderDetailetMapper.selectList(queryWrapper);

        // 填充食品信息
        for (OrderDetailet orderDetailet : orderDetailets) {
            try {
                Food food = businessServiceFeign.getFoodById(orderDetailet.getFoodId()).getData();
                orderDetailet.setFood(food);
            } catch (Exception e) {
                // 如果调用失败，设置默认值
                Food food = new Food();
                food.setFoodName("商品信息暂时不可用");
                food.setFoodPrice(0.0);
                orderDetailet.setFood(food);
            }
        }

        return orderDetailets;
    }

    public List<OrderVO> getOrdersByUserIdAndState(Integer userId, Integer orderState) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("orderState", orderState);
        queryWrapper.orderByDesc("orderDate");

        List<Orders> ordersList = ordersMapper.selectList(queryWrapper);
        List<OrderVO> orderVOList = new ArrayList<>();

        for (Orders orders : ordersList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrderId(orders.getOrderId());
            orderVO.setOrderDate(orders.getOrderDate());
            orderVO.setOrderTotal(orders.getOrderTotal());
            orderVO.setOrderState(orders.getOrderState());

            // 获取商家信息
            try {
                Business business = businessServiceFeign.getBusinessById(orders.getBusinessId()).getData();
                orderVO.setBusiness(business);
            } catch (Exception e) {
                // 如果调用失败，设置默认值
                Business business = new Business();
                business.setBusinessName("商家信息暂时不可用");
                orderVO.setBusiness(business);
            }

            // 获取订单详情
            List<OrderDetailet> orderDetailets = getOrderDetailsByOrderId(orders.getOrderId());
            orderVO.setOrderDetailetList(orderDetailets);

            orderVOList.add(orderVO);
        }

        return orderVOList;
    }

    public boolean deleteOrder(Integer orderId) {
        // 删除订单详情
        QueryWrapper<OrderDetailet> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("orderId", orderId);
        orderDetailetMapper.delete(queryWrapper);

        // 删除订单
        return ordersMapper.deleteById(orderId) > 0;
    }

    public Business getBusinessInfoByOrderId(Integer orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order != null) {
            try {
                return businessServiceFeign.getBusinessById(order.getBusinessId()).getData();
            } catch (Exception e) {
                // 如果调用失败，返回默认值
                Business business = new Business();
                business.setBusinessName("商家信息暂时不可用");
                return business;
            }
        }
        return null;
    }

    public OrderVO getOrderWithDetails(Integer orderId) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null) {
            return null;
        }

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orders.getOrderId());
        orderVO.setOrderDate(orders.getOrderDate());
        orderVO.setOrderTotal(orders.getOrderTotal());
        orderVO.setOrderState(orders.getOrderState());

        // 获取商家信息
        try {
            Business business = businessServiceFeign.getBusinessById(orders.getBusinessId()).getData();
            orderVO.setBusiness(business);
        } catch (Exception e) {
            Business business = new Business();
            business.setBusinessName("商家信息暂时不可用");
            orderVO.setBusiness(business);
        }

        // 获取地址信息
        try {
            DeliveryAddress address = userServiceFeign.getAddressById(orders.getDaId()).getData();
            orderVO.setDeliveryAddress(address);
        } catch (Exception e) {
            DeliveryAddress address = new DeliveryAddress();
            address.setAddress("地址信息暂时不可用");
            orderVO.setDeliveryAddress(address);
        }

        // 获取订单详情
        List<OrderDetailet> orderDetailets = getOrderDetailsByOrderId(orders.getOrderId());
        orderVO.setOrderDetailetList(orderDetailets);

        return orderVO;
    }
}
