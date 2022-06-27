package com.illimity.codechallenge.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {

    public String encode(String rawPassword){
        return DigestUtils.sha256Hex(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword){
        return DigestUtils.sha256Hex(rawPassword).equals(encodedPassword);
    }

}
