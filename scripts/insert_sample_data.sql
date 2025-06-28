USE eleme;

-- 插入商家数据
INSERT INTO `business` (`businessName`, `businessAddress`, `businessExplain`, `businessImg`, `orderTypeId`, `starPrice`, `deliveryPrice`, `remarks`) VALUES
('万家饺子', '软件园E18店', '各种美味饺子', 'sj01.png', 1, 15.0, 3.0, '美味饺子'),
('麦当劳', '中山路店', '快餐连锁', 'sj02.png', 4, 20.0, 5.0, '快餐'),
('肯德基', '万达店', '炸鸡汉堡', 'sj03.png', 4, 25.0, 4.0, '炸鸡'),
('星巴克', 'CBD店', '咖啡饮品', 'sj04.png', 5, 30.0, 6.0, '咖啡'),
('必胜客', '购物中心店', '披萨意面', 'sj05.png', 4, 35.0, 8.0, '披萨'),
('海底捞', '火车站店', '火锅料理', 'sj06.png', 1, 50.0, 10.0, '火锅'),
('小龙坎', '解放碑店', '重庆火锅', 'sj07.png', 1, 45.0, 8.0, '火锅'),
('喜茶', '太古里店', '新式茶饮', 'sj08.png', 5, 15.0, 3.0, '茶饮'),
('奈雪的茶', 'IFS店', '茶饮甜品', 'sj09.png', 5, 18.0, 4.0, '茶饮');

-- 插入食品数据
INSERT INTO `food` (`foodName`, `foodExplain`, `foodImg`, `foodPrice`, `businessId`, `remarks`) VALUES
('猪肉韭菜饺子', '经典口味', 'sp01.png', 15.0, 1, '水饺'),
('三鲜饺子', '虾仁猪肉韭菜', 'sp02.png', 18.0, 1, '水饺'),
('素三鲜饺子', '素食主义', 'sp03.png', 12.0, 1, '素饺'),
('巨无霸', '经典汉堡', 'sp04.png', 22.0, 2, '汉堡'),
('薯条', '金黄薯条', 'sp05.png', 12.0, 2, '小食'),
('可乐', '冰爽可乐', 'sp06.png', 8.0, 2, '饮品'),
('香辣鸡腿堡', '辣味十足', 'sp07.png', 25.0, 3, '汉堡'),
('鸡米花', '酥脆鸡米花', 'sp08.png', 15.0, 3, '小食'),
('美式咖啡', '经典美式', 'sp09.png', 28.0, 4, '咖啡'),
('拿铁咖啡', '香浓拿铁', 'sp10.png', 32.0, 4, '咖啡'),
('玛格丽特披萨', '经典披萨', 'sp11.png', 45.0, 5, '披萨'),
('意大利面', '番茄肉酱', 'sp12.png', 35.0, 5, '意面');

-- 插入测试用户
INSERT INTO `user` (`userName`, `password`, `userSex`, `userImg`, `delTag`) VALUES
('testuser', '123456', 1, 'yhtx01.png', 1),
('admin', 'admin123', 1, 'yhtx01.png', 1);

-- 插入测试地址
INSERT INTO `deliveryaddress` (`contactName`, `contactSex`, `contactTel`, `address`, `userId`) VALUES
('张三', 1, '13800138000', '北京市朝阳区建国门外大街1号', 1),
('李四', 0, '13900139000', '上海市浦东新区陆家嘴环路1000号', 1);
