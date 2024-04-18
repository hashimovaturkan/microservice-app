package com.microservice.userservice.service.grpc;

import com.microservice.common.UserServiceGrpc;
import com.microservice.common.Void;
import com.microservice.common.UserRequest;
import com.microservice.common.UserResponse;
import com.microservice.common.Role;
import com.microservice.common.RoleToUser;
import com.microservice.common.UserName;
import com.microservice.common.AllUsers;
import com.microservice.userservice.domain.mapper.RoleEditMapper;
import com.microservice.userservice.domain.mapper.UserEditMapper;
import com.microservice.userservice.domain.model.User;
import com.microservice.userservice.repository.RoleRepo;
import com.microservice.userservice.repository.UserRepo;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private final UserRepo userRepo;
    private final UserEditMapper userEditMapper;
    private final RoleEditMapper roleEditMapper;
    private final RoleRepo roleRepo;

    @Override
    public void saveUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }

        User user = userEditMapper.create(request);
        userRepo.save(user);

        UserResponse userResponse = UserResponse.newBuilder()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .addAllRoles(new ArrayList<>())
                .build();
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void saveRole(Role request, StreamObserver<Role> responseObserver) {

        var role = roleEditMapper.create(request);

        roleRepo.save(role);
        Role roleResponse = Role.newBuilder()
                .setRoleName(request.getRoleName())
                .build();
        responseObserver.onNext(roleResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void addRoleToUser(RoleToUser request, StreamObserver<Void> responseObserver) {
        userRepo.saveRoleToUser(request.getUsername(), request.getRoleName());

        Void v = Void.newBuilder()
                .build();
        responseObserver.onNext(v);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(UserName request, StreamObserver<UserResponse> responseObserver) {
        Optional<User> user = userRepo.findByUsername(request.getUsername());

        if (user.isPresent()) {
            throw new ValidationException("Username exists!");
        }

        List<Role> rolesResponse = new ArrayList<>();
        for (var role : user.get().getAuthorities()) {
            rolesResponse.add(Role.newBuilder().setRoleName(role.getAuthority()).build());
        }

        UserResponse userResponse = UserResponse.newBuilder()
                .setUsername(user.get().getUsername())
                .setPassword(user.get().getPassword())
                .addAllRoles(rolesResponse)
                .build();
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsers(Void request, StreamObserver<AllUsers> responseObserver) {
        List<User> users = userRepo.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            List<Role> rolesResponse = new ArrayList<>();
            for (var role : user.getAuthorities()) {
                rolesResponse.add(Role.newBuilder().setRoleName(role.getAuthority()).build());
            }

            userResponses.add(
                    UserResponse.newBuilder()
                            .setUsername(user.getUsername())
                            .setPassword(user.getPassword())
                            .addAllRoles(rolesResponse)
                            .build()
            );
        }
        responseObserver.onNext(AllUsers.newBuilder().addAllUsers(userResponses).build());
        responseObserver.onCompleted();
    }
}
