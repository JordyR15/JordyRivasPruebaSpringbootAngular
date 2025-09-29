package org.example.pruebaspring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private OffsetDateTime createdAt;
    private List<String> roles;
}