package com.security.securityjwt.domain.admin;

import com.security.securityjwt.domain.user.CustomUserDetailService;
import com.security.securityjwt.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/admin")
public class AdminController {

    private final CustomUserDetailService customUserDetailService;


    @GetMapping("/{email}/search")
    public UserDetails searchDetailUser(@PathVariable("email") String email) {

        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

        return userDetails;
    }



}
