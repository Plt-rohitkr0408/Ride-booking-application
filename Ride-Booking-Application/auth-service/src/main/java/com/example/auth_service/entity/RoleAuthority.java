package com.example.auth_service.entity;

import com.example.auth_service.enums.Authority;
import com.example.auth_service.enums.Role;

import java.util.Set;

public class RoleAuthority {
    public static Set<Authority> getRoleAuthorities(Role role){

       return  switch (role) {
           case USER->
               Set.of(
                       Authority.USER_CREATE,
                       Authority.USER_UPDATE,
                       Authority.RIDE_CREATE,
                       Authority.RIDE_DELETE,
                       Authority.RIDE_ASSIGN
               );

           case DRIVER->
               Set.of(
                       Authority.DRIVER_CREATE,
                       Authority.DRIVER_UPDATE,
                       Authority.RIDE_APPROVE,
                       Authority.RIDE_REJECT

               );

           case ADMIN->
               Set.of(
                       Authority.USER_CREATE,
                       Authority.USER_UPDATE,
                       Authority.DRIVER_CREATE,
                       Authority.DRIVER_UPDATE,
                       Authority.USER_DELETE,
                       Authority.DRIVER_DELETE
               );
       };

    }
}
