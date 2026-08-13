package com.example.Ride_Service.entity;

import com.example.Ride_Service.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "rides")
@Data
@AllArgsConstructor @NoArgsConstructor
public class RideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;
    private Long userId;
    private Long driverId;

    private Double pickedLatitude;
    private Double pickedLongitude;
    private Double dropedLatitude;
    private Double dropedLongitude;

    private Double fare;
    private Double distance;

    private String pickedAddress;
    private String dropedAddress;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private LocalDateTime bookedAt;
    private LocalDateTime pickedAt;
    private LocalDateTime dropedAt;

}
