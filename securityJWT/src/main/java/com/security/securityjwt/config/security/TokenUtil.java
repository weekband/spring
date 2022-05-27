package com.security.securityjwt.config.security;

import java.util.List;

public interface TokenUtil {

    String createToken(String userPk, List<String> roles,String key);



}
