package org.fizzy.busywithfish.dataserver.service;

import org.fizzy.busywithfish.dataserver.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationService {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    /**
     * Generate a verification code for an email and send it
     * @param email The email to generate code for
     */
    public void generateAndSendVerificationCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        // Store in Redis with 5 minutes expiration
        redisUtil.set(email, code, TimeUnit.MINUTES.toSeconds(5));
        
        // Send the code via email
        emailService.sendVerificationCode(email, code);
    }
    
    /**
     * Verify the code for an email
     * @param email The email to verify
     * @param code The code to check
     * @return True if the code is valid, false otherwise
     */
    public boolean verifyCode(String email, String code) {
        Object storedCode = redisUtil.get(email);
        if (storedCode != null && storedCode.toString().equals(code)) {
            // Remove the code after successful verification
            redisUtil.del(email);
            return true;
        }
        return false;
    }
}