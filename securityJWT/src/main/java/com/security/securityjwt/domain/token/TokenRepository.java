package com.security.securityjwt.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByRefrashToken(String token);
}
