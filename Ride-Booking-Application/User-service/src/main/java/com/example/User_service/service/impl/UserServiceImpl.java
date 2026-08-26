package com.example.User_service.service.impl;

import com.example.User_service.dto.request.CreateUserRequest;
import com.example.User_service.dto.request.UpdateUserRequest;
import com.example.User_service.dto.response.UserResponse;
import com.example.User_service.entity.User;
import com.example.User_service.mapper.UserMapper;
import com.example.User_service.repository.UserRepository;
import com.example.User_service.service.UserService;
import jakarta.ws.rs.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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

        UserResponse userResponse = UserMapper.toUserResponse(saveUser, "Created Successfully");
        return userResponse;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest , Authentication authentication) {
        User user  = userRepository.findByEmail(authentication.getName());
        user.setHomeAddress(updateUserRequest.getHomeAddress());
        user.setOfficeAddress(updateUserRequest.getOfficeAddress());
        user.setProfileImage(updateUserRequest.getProfileImage());
        User saveUser =userRepository.save(user);
        UserResponse userResponse = UserMapper.toUserResponse(saveUser, "Updated Successfully");
        return userResponse;
    }

    @Override
    public void deleteUser(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        userRepository.delete(user);
    }

    @Override
    public UserResponse getUserProfile(Long userId) {
        if(!userRepository.existsByUserId(userId)) {
            throw new BadRequestException("User does not exist");
        }
       User user = userRepository.findByUserId(userId);
        UserResponse userResponse = UserMapper.toUserResponse(user, "Profile Updated Successfully");
        return  userResponse;
    }

    public UserResponse getByEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new BadRequestException("User does not exist");
        }
        UserResponse userResponse =  UserMapper.toUserResponse(user, "User Updated Successfully");

        return  userResponse;
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if(users == null){
            throw new BadRequestException("Users is empty");
        }
        Page<UserResponse> userResponse = users.map(user -> UserMapper.toUserResponse(user, "Fetch Successfully"));
        return userResponse;
    }
}
