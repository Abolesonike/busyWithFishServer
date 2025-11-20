package org.fizzy.busywithfish.dataserver.controller;

import lombok.Data;
import org.fizzy.busywithfish.dataserver.dto.User;
import org.fizzy.busywithfish.dataserver.dto.UserRegistrationDto;
import org.fizzy.busywithfish.dataserver.service.EmailVerificationService;
import org.fizzy.busywithfish.dataserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private UserService userService;

    /**
     * 发送验证码到邮箱
     */
    @PostMapping("/send-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody UserRegistrationDto registrationDto) {
        // 生成并发送验证码
        emailVerificationService.generateAndSendVerificationCode(registrationDto.getEmail());
        
        return ResponseEntity.ok("验证码已发送到您的邮箱");
    }

    /**
     * 验证邮箱代码后注册新用户
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        // 验证代码
        if (!emailVerificationService.verifyCode(request.getUser().getEmail(), request.getCode())) {
            return ResponseEntity.badRequest().body("验证码无效或过期");
        }

        // 检查是否有用户已经使用相同的邮箱或用户名
        User existingUser = userService.getOne(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<User>()
                .eq("email", request.getUser().getEmail())
                .or()
                .eq("username", request.getUser().getUsername())
        );
        
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("拥有该邮箱或用户名的用户已经存在");
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(request.getUser().getUsername());
        newUser.setPassword(request.getUser().getPassword());
        newUser.setEmail(request.getUser().getEmail());
        newUser.setPhone(request.getUser().getPhone());

        // 注册用户（这将加密密码）
        boolean registered = userService.registerUser(newUser);
        
        if (registered) {
            return ResponseEntity.ok("用户注册成功");
        } else {
            return ResponseEntity.badRequest().body("用户注册失败");
        }
    }

    // 请用验证码申请DTO注册
    @Data
    public static class UserRegistrationRequest {
        private UserRegistrationDto user;
        private String code;
    }
}