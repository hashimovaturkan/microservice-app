package com.microservice.authservice.api;

import com.microservice.authservice.configuration.security.JwtTokenUtil;
import com.microservice.authservice.domain.dto.AuthRequest;
import com.microservice.authservice.domain.dto.CreateUserRequest;
import com.microservice.authservice.domain.dto.UserView;
import com.microservice.authservice.domain.mapper.UserViewMapper;
import com.microservice.authservice.domain.model.User;
import com.microservice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "auth")
public class AuthApi {

    public AuthApi(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserViewMapper userViewMapper, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userViewMapper = userViewMapper;
        this.userService = userService;
    }

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserViewMapper userViewMapper;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                    .body(userViewMapper.toUserView(user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public UserView register(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
