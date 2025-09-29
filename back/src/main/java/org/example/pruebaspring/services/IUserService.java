package org.example.pruebaspring.services;

import org.example.pruebaspring.dto.CreateUserRequestDto;
import org.example.pruebaspring.dto.ListUserRequestDto;
import org.example.pruebaspring.dto.UserListResponseDto;

import java.util.List;

public interface IUserService {
    void createUser(CreateUserRequestDto requestDto);
    List<UserListResponseDto> listUsers(ListUserRequestDto requestDto);
}