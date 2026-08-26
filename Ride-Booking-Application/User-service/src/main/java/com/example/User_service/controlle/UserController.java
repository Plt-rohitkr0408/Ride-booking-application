package com.example.User_service.controlle;

import com.example.User_service.dto.request.CreateUserRequest;
import com.example.User_service.dto.request.UpdateUserRequest;
import com.example.User_service.dto.response.UserResponse;
import com.example.User_service.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 10,
            sort = "name",
            direction = Sort.Direction.DESC) Pageable pageable) {
            Page<UserResponse> userResponses = userService.getAllUsers(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody CreateUserRequest request) {
        UserResponse userResponse = userService.createUser(request);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request , Authentication authentication) {
        UserResponse userResponse = userService.updateUser(request , authentication);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/delete/id")
    public ResponseEntity<String> deleteUser(Authentication authentication_){
        userService.deleteUser(authentication_);
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
