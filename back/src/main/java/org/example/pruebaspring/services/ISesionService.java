package org.example.pruebaspring.services;

import org.example.pruebaspring.dto.ForgotPasswordRequestDto;
import org.example.pruebaspring.dto.LoginRequestDto;
import org.example.pruebaspring.dto.LoginResponseDto;
import org.example.pruebaspring.dto.ResetPasswordRequestDto;

public interface ISesionService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void logout(String sessionToken);

    // ...

    void forgotPassword(ForgotPasswordRequestDto requestDto);

    void resetPassword(ResetPasswordRequestDto requestDto);
}

