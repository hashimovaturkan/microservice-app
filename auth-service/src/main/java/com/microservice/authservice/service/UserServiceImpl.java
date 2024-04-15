package com.microservice.authservice.service;

import com.microservice.authservice.domain.dto.*;
import com.microservice.authservice.domain.mapper.UserEditMapper;
import com.microservice.authservice.domain.mapper.UserViewMapper;
import com.microservice.common.*;
import com.microservice.common.Void;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserViewMapper userViewMapper;
    private final PasswordEncoder passwordEncoder;
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserView create(CreateUserRequest request) {
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        UserRequest userRequest = UserRequest.newBuilder()
                .setUsername(request.getUsername())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .build();

        UserResponse userResponse = userServiceBlockingStub.saveUser(userRequest);

        return userViewMapper.toUserView(userResponse);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserView userDto = getUser(username);
        if (userDto == null) {
            throw new UsernameNotFoundException("Username with " + username + " not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userDto.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return UserDetailsImpl.build(userDto);
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        Role roleRequest = Role.newBuilder()
                .setRoleName(roleDto.getName())
                .build();

        Role roleResponse = userServiceBlockingStub.saveRole(roleRequest);
        return new RoleDto(roleResponse.getRoleName());
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        RoleToUser roleToUser = RoleToUser.newBuilder()
                .setUsername(username)
                .setRoleName(roleName)
                .build();
        userServiceBlockingStub.addRoleToUser(roleToUser);
    }

    @Override
    public UserView getUser(String username) {
        UserName userName = UserName.newBuilder()
                .setUsername(username)
                .build();

        UserResponse userResponse = userServiceBlockingStub.getUser(userName);
        Set<RoleDto> roleDtos =
                userResponse.getRolesList().stream().map(role -> new RoleDto(role.getRoleName())).collect(Collectors.toSet());

        return new UserView(userResponse.getId(),userResponse.getUsername(), userResponse.getFullname(), roleDtos, userResponse.getPassword());
    }

    @Override
    public List<UserDto> getUsers() {
        Void v = Void.newBuilder()
                .build();
        List<UserResponse> users = userServiceBlockingStub.getUsers(v).getUsersList();
        List<UserDto> userDtos = new ArrayList<>();

        users.forEach(user -> {
            List<RoleDto> roleDtos = new ArrayList<>();
            user.getRolesList().forEach(role -> {
                roleDtos.add(new RoleDto(role.getRoleName()));
            });
            userDtos.add(new UserDto(user.getUsername(), user.getPassword(), new HashSet<>(roleDtos)));
        });

        return userDtos;
    }


}
