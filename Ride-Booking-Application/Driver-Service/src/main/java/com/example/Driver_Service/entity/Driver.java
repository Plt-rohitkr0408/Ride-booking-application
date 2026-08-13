package com.example.Driver_Service.entity;

import com.example.Driver_Service.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driverId")
    private Long driver_id;
    private Long authId;
    private String name;
    private String email;
    private String phone;
    private String password;

    private String licence;
    private String vehicleNumber;
    private String vehicleTye;

    private Double rating;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    private Double latitude;
    private Double longitude;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;


}
