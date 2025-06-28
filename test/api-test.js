// API自动化测试脚本
const axios = require("axios")

const BASE_URL = "http://localhost:8080"
let authToken = ""

// 测试配置
const testConfig = {
    timeout: 5000,
    retries: 3,
}

// 测试用户数据
const testUser = {
    userName: "testuser_" + Date.now(),
    password: "123456",
    userSex: 1,
}

// 测试结果统计
const testResults = {
    total: 0,
    passed: 0,
    failed: 0,
    errors: [],
}

// 工具函数
function logTest(testName, passed, message = "") {
    testResults.total++
    if (passed) {
        testResults.passed++
        console.log(`✅ ${testName} - PASSED`)
    } else {
        testResults.failed++
        console.log(`❌ ${testName} - FAILED: ${message}`)
        testResults.errors.push({ test: testName, error: message })
    }
}

// 延迟函数
function delay(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms))
}

// 1. 用户注册测试
async function testUserRegister() {
    try {
        const response = await axios.post(`${BASE_URL}/register`, testUser)
        logTest("用户注册", response.data.code === 1)
    } catch (error) {
        logTest("用户注册", false, error.message)
    }
}

// 2. 用户登录测试
async function testUserLogin() {
    try {
        const response = await axios.post(`${BASE_URL}/login`, {
            userName: testUser.userName,
            password: testUser.password,
        })

        if (response.data.code === 1) {
            authToken = response.data.data.token
            logTest("用户登录", true)
        } else {
            logTest("用户登录", false, response.data.msg)
        }
    } catch (error) {
        logTest("用户登录", false, error.message)
    }
}

// 3. 商家列表测试
async function testBusinessList() {
    try {
        const response = await axios.get(`${BASE_URL}/business`)
        logTest("商家列表查询", response.data.code === 1 && Array.isArray(response.data.data))
    } catch (error) {
        logTest("商家列表查询", false, error.message)
    }
}

// 4. 商家详情测试
async function testBusinessDetail() {
    try {
        const response = await axios.get(`${BASE_URL}/business/businessId/1`)
        logTest("商家详情查询", response.data.code === 1 && response.data.data.businessId === 1)
    } catch (error) {
        logTest("商家详情查询", false, error.message)
    }
}

// 5. 商品列表测试
async function testFoodList() {
    try {
        const response = await axios.get(`${BASE_URL}/food/businessId/1`)
        logTest("商品列表查询", response.data.code === 1 && Array.isArray(response.data.data))
    } catch (error) {
        logTest("商品列表查询", false, error.message)
    }
}

// 6. 添加地址测试
async function testAddAddress() {
    if (!authToken) {
        logTest("添加地址", false, "需要先登录")
        return
    }

    try {
        const addressData = {
            contactName: "测试用户",
            contactSex: 1,
            contactTel: "13800138000",
            address: "测试地址123号",
            userId: 1,
        }

        const response = await axios.post(`${BASE_URL}/addressList`, addressData, {
            headers: { Authorization: authToken },
        })

        logTest("添加地址", response.data.code === 1)
    } catch (error) {
        logTest("添加地址", false, error.message)
    }
}

// 7. 购物车测试
async function testAddToCart() {
    if (!authToken) {
        logTest("添加购物车", false, "需要先登录")
        return
    }

    try {
        const cartData = {
            userId: 1,
            foodId: 1,
            businessId: 1,
        }

        const response = await axios.post(`${BASE_URL}/cart`, cartData, {
            headers: { Authorization: authToken },
        })

        logTest("添加购物车", response.data.code === 1)
    } catch (error) {
        logTest("添加购物车", false, error.message)
    }
}

// 8. 创建订单测试
async function testCreateOrder() {
    if (!authToken) {
        logTest("创建订单", false, "需要先登录")
        return
    }

    try {
        const orderData = {
            userId: 1,
            businessId: 1,
            orderTotal: 50.0,
            daId: 1,
        }

        const response = await axios.post(`${BASE_URL}/orders`, orderData, {
            headers: { Authorization: authToken },
        })

        logTest("创建订单", response.data.code === 1)
        return response.data.data // 返回订单ID
    } catch (error) {
        logTest("创建订单", false, error.message)
    }
}

// 9. 订单查询测试
async function testOrderQuery(orderId) {
    if (!authToken || !orderId) {
        logTest("订单查询", false, "需要先登录和创建订单")
        return
    }

    try {
        const response = await axios.get(`${BASE_URL}/orders/${orderId}`, {
            headers: { Authorization: authToken },
        })

        logTest("订单查询", response.data.code === 1 && response.data.data.orderId === orderId)
    } catch (error) {
        logTest("订单查询", false, error.message)
    }
}

// 10. 支付测试
async function testPayment(orderId) {
    if (!orderId) {
        logTest("支付功能", false, "需要先创建订单")
        return
    }

    try {
        const response = await axios.post(`${BASE_URL}/payment/pay`, null, {
            params: {
                orderId: orderId,
                paymentMethod: "alipay",
            },
        })

        logTest("支付功能", response.data.code === 1)
    } catch (error) {
        logTest("支付功能", false, error.message)
    }
}

// 11. 服务健康检查测试
async function testHealthCheck() {
    const services = [
        { name: "Eureka Server", url: "http://localhost:8761/actuator/health" },
        { name: "Gateway", url: "http://localhost:8080/actuator/health" },
        { name: "User Service", url: "http://localhost:8081/actuator/health" },
        { name: "Business Service", url: "http://localhost:8082/actuator/health" },
        { name: "Order Service", url: "http://localhost:8083/actuator/health" },
        { name: "Payment Service", url: "http://localhost:8084/actuator/health" },
    ]

    for (const service of services) {
        try {
            const response = await axios.get(service.url, { timeout: 3000 })
            logTest(`${service.name} 健康检查`, response.data.status === "UP")
        } catch (error) {
            logTest(`${service.name} 健康检查`, false, error.message)
        }
    }
}

// 12. 性能测试
async function testPerformance() {
    const testUrl = `${BASE_URL}/business`
    const requestCount = 50
    const startTime = Date.now()

    try {
        const promises = Array(requestCount)
            .fill()
            .map(() => axios.get(testUrl, { timeout: 5000 }))

        const results = await Promise.allSettled(promises)
        const endTime = Date.now()

        const successCount = results.filter((r) => r.status === "fulfilled").length
        const avgResponseTime = (endTime - startTime) / requestCount

        logTest(
            "性能测试",
            successCount >= requestCount * 0.95 && avgResponseTime < 1000,
            `成功率: ${((successCount / requestCount) * 100).toFixed(1)}%, 平均响应时间: ${avgResponseTime.toFixed(0)}ms`,
        )
    } catch (error) {
        logTest("性能测试", false, error.message)
    }
}

// 主测试函数
async function runAllTests() {
    console.log("🚀 开始执行API自动化测试...\n")

    // 基础功能测试
    await testUserRegister()
    await delay(1000)

    await testUserLogin()
    await delay(1000)

    await testBusinessList()
    await delay(500)

    await testBusinessDetail()
    await delay(500)

    await testFoodList()
    await delay(500)

    await testAddAddress()
    await delay(1000)

    await testAddToCart()
    await delay(1000)

    const orderId = await testCreateOrder()
    await delay(1000)

    if (orderId) {
        await testOrderQuery(orderId)
        await delay(500)

        await testPayment(orderId)
        await delay(1000)
    }

    // 系统健康检查
    console.log("\n🔍 执行服务健康检查...")
    await testHealthCheck()

    // 性能测试
    console.log("\n⚡ 执行性能测试...")
    await testPerformance()

    // 输出测试结果
    console.log("\n📊 测试结果统计:")
    console.log(`总测试数: ${testResults.total}`)
    console.log(`通过数: ${testResults.passed}`)
    console.log(`失败数: ${testResults.failed}`)
    console.log(`通过率: ${((testResults.passed / testResults.total) * 100).toFixed(1)}%`)

    if (testResults.errors.length > 0) {
        console.log("\n❌ 失败的测试:")
        testResults.errors.forEach((error) => {
            console.log(`  - ${error.test}: ${error.error}`)
        })
    }

    console.log("\n✅ 测试执行完成!")
}

// 执行测试
if (require.main === module) {
    runAllTests().catch(console.error)
}

module.exports = {
    runAllTests,
    testResults,
}
