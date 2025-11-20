package org.fizzy.busywithfish.dataserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {
    
    private static PasswordEncoder passwordEncoder;
    
    @Autowired
    public PasswordEncoderUtil(PasswordEncoder passwordEncoder) {
        PasswordEncoderUtil.passwordEncoder = passwordEncoder;
    }
    
    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}