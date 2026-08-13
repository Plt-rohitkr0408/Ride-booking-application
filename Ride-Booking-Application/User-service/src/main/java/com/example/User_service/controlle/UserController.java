package com.example.User_service.controlle;

import com.example.User_service.dto.request.CreateUserRequest;
import com.example.User_service.dto.request.UpdateUserRequest;
import com.example.User_service.dto.response.UserResponse;
import com.example.User_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request) {
        UserResponse userResponse = userService.createUser(request);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(request);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{authId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long authId){
        userService.deleteUser(authId);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{authId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long authId){
        UserResponse userResponse = userService.getUserProfile(authId);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserbyEmail(@RequestParam String email){
        System.out.println("Given Email "+ email);
        UserResponse userResponse = userService.getByEmail(email);
        return ResponseEntity.ok(userResponse);
    }
}
