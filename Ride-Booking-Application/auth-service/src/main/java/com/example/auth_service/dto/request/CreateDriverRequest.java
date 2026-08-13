package com.example.auth_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CreateDriverRequest {
    private String name;
    private Long authId;
    private String email;
    private String phone;
    private String password;
}
