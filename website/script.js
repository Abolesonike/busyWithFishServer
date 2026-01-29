// 轮播图功能
let slideIndex = 1;
showSlides(slideIndex);

// 下一张/上一张
function changeSlide(n) {
    showSlides(slideIndex += n);
}

// 当前幻灯片
function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    const slides = document.querySelectorAll('.carousel-slide');
    const dots = document.querySelectorAll('.dot');
    
    if (n > slides.length) {slideIndex = 1}
    if (n < 1) {slideIndex = slides.length}
    
    // 隐藏所有幻灯片
    slides.forEach(slide => {
        slide.style.display = 'none';
    });
    
    // 移除所有活动点
    dots.forEach(dot => {
        dot.classList.remove('active');
    });
    
    // 显示当前幻灯片
    if (slides[slideIndex - 1]) {
        slides[slideIndex - 1].style.display = 'block';
    }
    
    // 激活当前点
    if (dots[slideIndex - 1]) {
        dots[slideIndex - 1].classList.add('active');
    }
}

// 自动播放轮播图
setInterval(() => {
    changeSlide(1);
}, 5000); // 每5秒切换一次

// 导航栏功能
const hamburger = document.querySelector('.hamburger');
const navMenu = document.querySelector('.nav-menu');

hamburger.addEventListener('click', () => {
    hamburger.classList.toggle('active');
    navMenu.classList.toggle('active');
});

// 点击导航链接时关闭移动端菜单
document.querySelectorAll('.nav-menu a').forEach(link => {
    link.addEventListener('click', () => {
        hamburger.classList.remove('active');
        navMenu.classList.remove('active');
    });
});

// 滚动时导航栏效果
window.addEventListener('scroll', () => {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) {
        navbar.style.padding = '0.5rem 0';
        navbar.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
    } else {
        navbar.style.padding = '1rem 0';
        navbar.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
    }
});

// 平滑滚动到锚点
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();
        
        const targetId = this.getAttribute('href');
        if (targetId === '#') return;
        
        const targetElement = document.querySelector(targetId);
        if (targetElement) {
            window.scrollTo({
                top: targetElement.offsetTop - 80, // 考虑固定导航栏的高度
                behavior: 'smooth'
            });
        }
    });
});

// 下载链接功能 - 这些可以由用户根据实际部署路径修改
document.getElementById('win-download').addEventListener('click', function(e) {
    e.preventDefault();
    // 预留 Windows 下载链接，用户可在此修改实际路径
    alert('Windows 版本下载链接将在服务器部署后更新');
    // window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-windows.zip'; 
});

document.getElementById('mac-download').addEventListener('click', function(e) {
    e.preventDefault();
    // 预留 macOS 下载链接，用户可在此修改实际路径
    alert('macOS 版本下载链接将在服务器部署后更新');
    // window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-macos.dmg';
});

document.getElementById('linux-download').addEventListener('click', function(e) {
    e.preventDefault();
    // 预留 Linux 下载链接，用户可在此修改实际路径
    alert('Linux 版本下载链接将在服务器部署后更新');
    // window.location.href = 'YOUR_SERVER_PATH/busy-with-fish-linux.tar.gz';
});

// 页面加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 检测当前页面URL并高亮对应的导航项
    const sections = document.querySelectorAll('section');
    const navLinks = document.querySelectorAll('.nav-menu a');
    
    window.addEventListener('scroll', () => {
        let current = '';
        
        sections.forEach(section => {
            const sectionTop = section.offsetTop;
            const sectionHeight = section.clientHeight;
            
            if (pageYOffset >= (sectionTop - 100)) {
                current = section.getAttribute('id');
            }
        });
        
        navLinks.forEach(link => {
            link.classList.remove('active');
            if (link.getAttribute('href') === `#${current}`) {
                link.classList.add('active');
            }
        });
    });
});