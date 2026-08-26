package com.example.Driver_Service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDriverRequest {
    private String name;
    private String email;
    private String phone;
    private Long authId;
    private String password;

}
