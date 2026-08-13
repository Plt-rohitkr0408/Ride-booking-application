package com.example.Ride_Service.dto.request;


import com.example.Ride_Service.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusCancelRequest {
    private RideStatus status = RideStatus.CANCELLED;
}
