# Busy With Fish 官网

这是 Busy With Fish 软件的官方网站源代码。

## 项目结构

- `index.html` - 主页面文件
- `styles.css` - 样式文件
- `script.js` - JavaScript 交互功能
- `images/` - 存放网站图片资源

## 功能说明

### 1. 下载链接配置
在 [script.js](./script.js) 文件中找到以下部分，替换为你服务器上的实际下载路径：

```javascript
// Windows 下载
window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-windows.zip';

// macOS 下载
window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-macos.dmg';

// Linux 下载
window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-linux.tar.gz';
```

### 2. 图片轮播配置
在 [index.html](./index.html) 文件中找到轮播图部分，替换为实际的截图路径：

```html
<img src="images/screenshot1.jpg" alt="木鱼模式" class="carousel-slide active">
<img src="images/screenshot2.jpg" alt="Pop Cat模式" class="carousel-slide">
<!-- 更多图片... -->
```

### 3. 捐赠二维码配置
在 [index.html](./index.html) 文件中找到捐赠区域，替换为实际的二维码图片：

```html
<div class="qr-image" id="alipay-qr">
  <img src="images/alipay_qr.png" alt="支付宝捐赠码" />
</div>
<div class="qr-image" id="wechat-qr">
  <img src="images/wechat_qr.png" alt="微信捐赠码" />
</div>
```

## 部署说明

1. 将整个 `website` 目录上传到你的 Web 服务器
2. 确保所有文件路径正确
3. 配置下载链接指向你的实际发布文件
4. 替换占位图片为实际图片

## 预览

你可以直接在浏览器中打开 `index.html` 文件进行本地预览。