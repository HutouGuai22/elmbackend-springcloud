# 饿了么后端微服务项目

本项目是一个基于 Spring Cloud 的饿了么外卖平台后端系统，采用微服务架构，包含业务服务、订单服务、支付服务、用户服务、网关、注册中心等多个模块，支持服务注册与发现、服务间调用、统一认证与鉴权等功能。

## 项目结构

```
elmbackend-springcloud/
├── eleme-business-service      # 商家与商品相关服务
├── eleme-common               # 通用模块（实体类、工具类等）
├── eleme-eureka-server        # 服务注册中心
├── eleme-gateway              # API 网关
├── eleme-order-service        # 订单服务
├── eleme-payment-service      # 支付服务
├── eleme-user-service         # 用户服务
├── scripts                    # 数据库脚本
├── test                       # 测试相关
└── 项目报告.md                # 项目说明文档
```

## 技术栈

- Spring Cloud
- Spring Boot
- Spring Security
- MyBatis
- Eureka
- Spring Cloud Gateway
- Feign
- MySQL

## 快速开始

### 1. 克隆项目

```bash
git clone https://your-repo-url.git
cd elmbackend-springcloud
```

### 2. 数据库准备

在 MySQL 中执行 `scripts/` 目录下的建库和初始化脚本：

```sql
source scripts/create_database.sql;
source scripts/insert_sample_data.sql;
```

### 3. 启动服务

建议按照以下顺序启动：

1. 注册中心（Eureka）：`eleme-eureka-server`
2. 其他微服务模块（可并行启动）：
   - `eleme-gateway`
   - `eleme-business-service`
   - `eleme-order-service`
   - `eleme-payment-service`
   - `eleme-user-service`

可以使用 IDE 或命令行分别进入各模块目录，运行：

```bash
mvn spring-boot:run
```

### 4. 访问接口

- 网关入口：http://localhost:8080/
- Eureka 注册中心：http://localhost:8761/

## 目录说明

- `eleme-business-service`：商家、商品、购物车等相关接口
- `eleme-order-service`：订单相关接口
- `eleme-payment-service`：支付相关接口
- `eleme-user-service`：用户、地址等相关接口
- `eleme-gateway`：统一 API 网关，路由与鉴权
- `eleme-eureka-server`：服务注册与发现
- `eleme-common`：通用实体、工具类、统一返回结果等

## 测试

测试脚本位于 `test/` 目录，可用于接口测试与性能测试。
