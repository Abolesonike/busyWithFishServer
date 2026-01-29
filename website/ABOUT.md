# Busy With Fish 官网 - 项目说明

## 项目概述

这是一个为 Busy With Fish 软件创建的静态官网，具有以下特点：

- **简洁现代的设计**：采用白色主色调，配以柔和的颜色搭配
- **响应式布局**：适配各种设备尺寸
- **功能齐全**：包含下载、图片轮播、功能介绍和捐赠等模块
- **易于定制**：预留了修改空间，便于用户自定义内容

## 设计特色

### 1. 简洁高级的视觉风格
- 白色主色调，配以淡雅的蓝色、绿色、橙色点缀
- 现代化的卡片设计和阴影效果
- 清晰的排版和适当的留白

### 2. 完整的功能模块
- **首页横幅**：吸引人的首屏展示
- **功能介绍**：详细的功能特性说明
- **图片轮播**：界面预览展示
- **下载区域**：多平台下载选项
- **捐赠支持**：支持项目发展的渠道

### 3. 用户体验优化
- 平滑滚动导航
- 响应式设计适配移动设备
- 交互式元素（轮播图、导航菜单）

## 文件结构

```
website/
├── index.html          # 主页面文件
├── styles.css          # 样式文件
├── script.js           # JavaScript交互功能
├── images/             # 图片资源目录
├── README.md           # 使用说明
└── ABOUT.md            # 项目说明（当前文件）
```

## 自定义指南

### 1. 修改下载链接
编辑 [script.js](./script.js) 文件，找到以下代码并替换为实际的下载地址：

```javascript
// Windows 版本下载
// window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-windows.zip';

// macOS 版本下载
// window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-macos.dmg';

// Linux 版本下载
// window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-linux.tar.gz';
```

### 2. 替换界面截图
在 [index.html](./index.html) 文件中，轮播图部分使用了SVG图形作为占位符。您可以将其替换为真实的软件截图：

```html
<!-- 将SVG替换为真实图片 -->
<img src="images/actual-screenshot.jpg" alt="功能描述" class="carousel-slide active">
```

### 3. 更新捐赠二维码
同样在 [index.html](./index.html) 文件中，将SVG二维码替换为真实的支付二维码图片：

```html
<img src="images/alipay_qr.png" alt="支付宝捐赠码" />
<img src="images/wechat_qr.png" alt="微信捐赠码" />
```

## 技术特点

- **纯静态网页**：无需服务器端处理，可直接部署
- **现代CSS**：使用Grid和Flexbox布局
- **ES6 JavaScript**：现代化的交互功能实现
- **响应式设计**：适配桌面、平板和手机设备

## 部署说明

将整个 `website` 目录上传到任何支持静态文件托管的服务，例如：
- GitHub Pages
- Netlify
- Vercel
- 传统Web服务器

## 版权信息

此网站模板为 Busy With Fish 项目的一部分，遵循项目的MIT许可证。