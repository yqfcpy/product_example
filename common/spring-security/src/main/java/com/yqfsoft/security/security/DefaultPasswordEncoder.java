package com.yqfsoft.security.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
// 当前使用的是SpringSecurity的BCrypt提供的加密 这里可以写自己的
@Component
public class DefaultPasswordEncoder extends BCryptPasswordEncoder {

    public DefaultPasswordEncoder() {
        this(-1);
    }
    public DefaultPasswordEncoder(int strength) {
    }

    // 加密方法
    @Override
    public String encode(CharSequence charSequence) {
        return super.encode(charSequence);
    }
    //进行密码比对
    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return super.matches(charSequence,encodedPassword);
    }
}
