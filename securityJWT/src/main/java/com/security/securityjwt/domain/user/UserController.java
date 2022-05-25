package com.security.securityjwt.domain.user;


import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import com.security.securityjwt.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    //회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER"))  //최초가입시 USER로 설정
                .build()).getId();

    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new UserException(UserExceptionResult.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(user.get("password"),member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @GetMapping("/{email}/search")
    public String searchEmail(@PathVariable("email") String email) {


        User findEmail = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("찾으시는 이메일이 없습니다."));

        return findEmail.getEmail();
    }

}
