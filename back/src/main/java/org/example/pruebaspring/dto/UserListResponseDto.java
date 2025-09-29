package org.example.pruebaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponseDto {
    private Integer userId;
    private String username;
    private String email;
    private String fullName;
    private List<String> roles;
}

    