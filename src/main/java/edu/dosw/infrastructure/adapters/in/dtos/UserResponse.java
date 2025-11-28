package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String userName;
    private String userPfp;
    private Boolean isActive;
}
