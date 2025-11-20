package org.fizzy.busywithfish.dataserver.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fizzy.busywithfish.dataserver.dto.User;
import org.fizzy.busywithfish.dataserver.mapper.UserMapper;
import org.fizzy.busywithfish.dataserver.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    
    /**
     * 用加密密码注册新用户
     * @param user 用户注册
     * @return 注册成功则为true，否则为false
     */
    public boolean registerUser(User user) {
        // 保存前先加密密码
        user.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
        return this.save(user);
    }
}