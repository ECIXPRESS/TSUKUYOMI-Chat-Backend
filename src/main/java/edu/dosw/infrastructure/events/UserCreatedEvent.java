package edu.dosw.infrastructure.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent implements Serializable {
    private String userId;
    private String email;
    private String userName;
    private String userType;
    private LocalDateTime createdAt;
}