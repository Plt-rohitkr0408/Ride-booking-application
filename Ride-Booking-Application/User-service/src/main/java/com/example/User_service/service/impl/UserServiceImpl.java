package com.example.User_service.service.impl;

import com.example.User_service.dto.request.CreateUserRequest;
import com.example.User_service.dto.request.UpdateUserRequest;
import com.example.User_service.dto.response.UserResponse;
import com.example.User_service.entity.User;
import com.example.User_service.repository.UserRepository;
import com.example.User_service.service.UserService;
import jakarta.ws.rs.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPhone(createUserRequest.getPhone());
        user.setAuthId(createUserRequest.getAuthId());
        user.setPassword(createUserRequest.getPassword());

        User saveUser =userRepository.save(user);

        UserResponse userResponse = UserResponse.builder()
                .name(saveUser.getName())
                .email(saveUser.getEmail())
                .phone(saveUser.getPhone())
                .authId(saveUser.getAuthId())
                .userId(saveUser.getUserId())
                .message("User created Successfully")
                .build();
        return userResponse;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user =userRepository.findByAuthId(updateUserRequest.getAuthId());
        user.setHomeAddress(updateUserRequest.getHomeAddress());
        user.setOfficeAddress(updateUserRequest.getOfficeAddress());
        user.setProfileImage(updateUserRequest.getProfileImage());
        User saveUser =userRepository.save(user);
        UserResponse userResponse = UserResponse.builder()
                .phone(saveUser.getPhone())
                .email(saveUser.getEmail())
                .name(saveUser.getName())
                .authId(saveUser.getAuthId())
                .userId(saveUser.getUserId())
                .homeAddress(saveUser.getHomeAddress())
                .officeAddress(saveUser.getOfficeAddress())
                .profileImage(saveUser.getProfileImage())
                .message("User Updated Successfully")
                .build();
        return userResponse;
    }

    @Override
    public void deleteUser(Long authId) {
        if(userRepository.existsByAuthId(authId)) {
            userRepository.deleteById(authId);
        }
    }

    @Override
    public UserResponse getUserProfile(Long authId) {
        if(!userRepository.existsByAuthId(authId)) {
            throw new BadRequestException("User does not exist");
        }
       User user = userRepository.findByAuthId(authId);
        UserResponse userResponse = UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .authId(user.getAuthId())
                .userId(user.getUserId())
                .homeAddress(user.getHomeAddress())
                .officeAddress(user.getOfficeAddress())
                .profileImage(user.getProfileImage())
                .message("Updated Profile")
                .build();

        return  userResponse;
    }

    public UserResponse getByEmail(String email){
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .authId(user.getAuthId())
                .userId(user.getUserId())
                .homeAddress(user.getHomeAddress())
                .officeAddress(user.getOfficeAddress())
                .profileImage(user.getProfileImage())
                .message("Updated Profile")
                .build();

        return  userResponse;
    }
}
