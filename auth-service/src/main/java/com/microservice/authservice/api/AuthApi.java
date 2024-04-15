package com.microservice.authservice.api;

import com.microservice.authservice.configuration.security.JwtTokenUtil;
import com.microservice.authservice.domain.dto.AuthRequest;
import com.microservice.authservice.domain.dto.CreateUserRequest;
import com.microservice.authservice.domain.dto.UserView;
import com.microservice.authservice.domain.mapper.UserViewMapper;
import com.microservice.authservice.domain.dto.RoleDto;
import com.microservice.authservice.service.UserService;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "auth")
public class AuthApi {

    public AuthApi(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserViewMapper userViewMapper, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userViewMapper = userViewMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserViewMapper userViewMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest loginRequest) {
        try {
            UserView user = userService.getUser(loginRequest.getUsername());
            if (user == null)
                throw new BadRequestException("User not found");

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                throw new BadRequestException("User not found");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            String jwt = jwtTokenUtil.createAccessToken(user.getId().toString(), "roles", user.getRoles().stream().map(RoleDto::getName).collect(Collectors.toList()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwt)
                    .body(user);

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
