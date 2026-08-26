package com.example.auth_service.service.serviceImpl;

import com.example.auth_service.config.DriverClient;
import com.example.auth_service.config.UserClient;
import com.example.auth_service.dto.request.*;
import com.example.auth_service.dto.response.LoginResponse;
import com.example.auth_service.dto.response.RegisterResponse;
import com.example.auth_service.dto.response.UserResponse;
import com.example.auth_service.entity.AuthUser;
import com.example.auth_service.entity.RefreshToken;
import com.example.auth_service.enums.Role;
import com.example.auth_service.repository.AuthUserRepository;
import com.example.auth_service.repository.RefreshTokenRepository;
import com.example.auth_service.security.JwtService;
import com.example.auth_service.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final DriverClient driverClient;

    private static final Long secretNumber = 1L;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${refresh.expiration}")
    private Long expiredIn;
    private final JwtService jwtService;
    private final UserClient   userClient;
    public AuthServiceImpl(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository, DriverClient driverClient, JwtService jwtService, UserClient userClient){
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.driverClient = driverClient;
        this.jwtService = jwtService;
        this.userClient = userClient;
    }

    @Override
    @Transactional
    public RegisterResponse register(Register register) {
        AuthUser authUser = new AuthUser();
        authUser.setEmail(register.getEmail());
        authUser.setPassword(passwordEncoder.encode(register.getPassword()));
        authUser.setName(register.getName());
        authUser.setPhone(register.getPhone());
        authUser.setRole(Role.valueOf(register.getRole().toUpperCase()));
        AuthUser saveUser = authUserRepository.save(authUser);

        if(register.getRole().equalsIgnoreCase("USER")){
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email(saveUser.getEmail())
                .password(saveUser.getPassword())
                .name(saveUser.getName())
                .phone(saveUser.getPhone())
                .authId(authUser.getId())
                .build();
        logger.info("CreateUser Request successfully");
        userClient.createUser(createUserRequest);
        }
        else if( register.getRole().equalsIgnoreCase("DRIVER")){
            CreateDriverRequest createDriverRequest = CreateDriverRequest.builder()
                    .name(saveUser.getName())
                    .authId(saveUser.getId())
                    .email(saveUser.getEmail())
                    .phone(saveUser.getPhone())
                    .password(passwordEncoder.encode(saveUser.getPassword()))
                    .build();
        logger.info("Driver Request successfully");
            driverClient.createDriver(createDriverRequest);
        }

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(saveUser.getEmail());
        registerResponse.setRole(saveUser.getRole().toString());
        registerResponse.setUsername(saveUser.getName());
        registerResponse.setId(saveUser.getId());
        registerResponse.setMessage(saveUser.getRole() + " Registered Successfully");
        return registerResponse;
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        if(authentication == null){
            throw new BadCredentialsException("Authentication Failed");
        }

        AuthUser authUser = authUserRepository.findByEmail(loginRequest.getEmail());
        authentication.getPrincipal();
        String accessToken = jwtService.accessToken(authUser.getEmail());
        String refreshToken = jwtService.refreshToken(authUser.getEmail());
        String token_type = "Bearer";
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccess_token(accessToken);
        loginResponse.setRefresh_token(refreshToken);
        loginResponse.setToken_type(token_type);
        loginResponse.setUsername(authUser.getName());
        loginResponse.setEmail(authUser.getEmail());
        loginResponse.setRole(authUser.getRole().toString());
        loginResponse.setUserId(authUser.getId());
        loginResponse.setExpireIn(LocalDateTime.now().plus(Duration.ofMillis(expiredIn)));

        RefreshToken refreshToken1 =  new RefreshToken();
        refreshToken1.setToken(loginResponse.getRefresh_token());
        refreshToken1.setUserId(authUser.getId());
        refreshToken1.setCreatedAt(LocalDateTime.now());
        refreshToken1.setExpiredAt(LocalDateTime.now().plus(Duration.ofMillis(expiredIn)));
        refreshTokenRepository.save(refreshToken1);
        return loginResponse;
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken);

        if(token == null){
        throw new BadCredentialsException("Refresh Token not found");
        }
        if( !token.getExpiredAt().isBefore(LocalDateTime.now())){
            refreshTokenRepository.delete(token);
            throw new BadCredentialsException("Refresh Token Expired");
        }
        AuthUser  authUser = authUserRepository.findById(token.getUserId()).orElse(null);
        LoginResponse loginResponse = new  LoginResponse();
        if(authUser == null){
            throw new BadCredentialsException("Authentication Failed");
        }

        String accessToken = jwtService.accessToken(authUser.getEmail());
        loginResponse.setAccess_token(accessToken);
        loginResponse.setRefresh_token(refreshToken);
        loginResponse.setUserId(authUser.getId());
        loginResponse.setUsername(authUser.getName());
        loginResponse.setEmail(authUser.getEmail());
        loginResponse.setRole(authUser.getRole().toString());

        return loginResponse;
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = authUserRepository.findByEmail(authentication.getName());
        UserResponse userResponse = new UserResponse();
        userResponse.setId(authUser.getId());
        userResponse.setUsername(authUser.getName());
        userResponse.setEmail(authUser.getEmail());
        userResponse.setRole(authUser.getRole().toString());

        return userResponse;
    }

    @Override
    public void logout() {
      Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
      if(auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())){
          throw new BadCredentialsException("User is not authenticated ");
      }
      String email = auth.getName();
      AuthUser authuser = authUserRepository.findByEmail(email);
      if(authuser == null){
          throw new BadCredentialsException("User not found");
      }
      RefreshToken refreshToken =refreshTokenRepository.findByUserId(authuser.getId());
      if(refreshToken != null){
          refreshTokenRepository.delete(refreshToken);
      }
      SecurityContextHolder.clearContext();
    }
}
