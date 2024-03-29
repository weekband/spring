package com.security.securityjwt.domain.user;


import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import com.security.securityjwt.config.security.JwtTokenDTO;
import com.security.securityjwt.config.security.JwtTokenProvider;
import com.security.securityjwt.domain.token.Token;
import com.security.securityjwt.domain.token.TokenRepository;
import com.security.securityjwt.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final TokenService tokenService;


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
    public ResponseEntity<JwtTokenDTO> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new UserException(UserExceptionResult.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(user.get("password"),member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        String refrash = jwtTokenProvider.issueToken(member.getUsername(), member.getRoles(), "refrash");


        return ResponseEntity.status(HttpStatus.CREATED).body(JwtTokenDTO.builder()
                .accessToken(jwtTokenProvider.issueToken(member.getUsername(),member.getRoles(),"access"))
                .refreshToken(tokenRepository.save(Token.builder().refrashToken(refrash).build()).getRefrashToken())
                .build());
    }

    @PostMapping("/reissuance")
    public ResponseEntity tokenReissuance (HttpServletRequest request) {
        String token = request.getHeader("X-REFRASH-TOKEN");
        System.out.println("tokenReissuance = " + token);
        if (jwtTokenProvider.validateRefreshToken(token)){
            User member = userRepository.findByEmail(jwtTokenProvider.getUserPk(token))
                    .orElseThrow(()->new UserException(UserExceptionResult.EMAIL_NOT_FOUND));
            System.out.println("member : " + member);
            String reflashToken = tokenService.tokenUpdate(token, member);


            return ResponseEntity.ok().body(JwtTokenDTO.builder()
                            .accessToken(jwtTokenProvider.issueToken(member.getUsername(),member.getRoles(),"access"))
                            .refreshToken(reflashToken)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UserExceptionResult.REFRASH_TOKEN_INVALID_VERIFICATION);
        }


    }

    @GetMapping("/{email}/search")
    public String searchEmail(@PathVariable("email") String email) {
        User findEmail = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("찾으시는 이메일이 없습니다."));

        return findEmail.getEmail();
    }

   /* @GetMapping("/tokenInfo")
    public ResponseEntity<String> myTokenInfo(HttpServletRequest request) {
        String header = request.getHeader("X-ACCESS-TOKEN");
        ObjectMapper mapper = new ObjectMapper();

        //Claims jwtTokenProviderUserPk = jwtTokenProvider.getTokenInfo(header);


        return ResponseEntity.ok(jwtTokenProviderUserPk.toString());
    }
*/

}
