package org.example.pruebaspring.controller;

import org.example.pruebaspring.dto.RoleListRequestDto;
import org.example.pruebaspring.dto.RoleResponseDto;
import org.example.pruebaspring.services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRolService rolService;


    @PostMapping("/list")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(@RequestBody(required = false) RoleListRequestDto requestDto) {
        List<RoleResponseDto> roles = rolService.getAllRoles(requestDto);
        return ResponseEntity.ok(roles);
    }
}
    