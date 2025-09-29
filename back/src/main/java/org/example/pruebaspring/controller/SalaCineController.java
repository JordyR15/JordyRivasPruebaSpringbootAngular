package org.example.pruebaspring.controller;

import org.example.pruebaspring.dto.CreateSalaCineRequestDto;
import org.example.pruebaspring.entity.SalaCine;
import org.example.pruebaspring.services.ISalaCineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List; // <-- AÑADE ESTE IMPORT
import java.util.Map;

@RestController
@RequestMapping("/api/salas")
public class SalaCineController {

    @Autowired
    private ISalaCineService salaCineService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSalaCine(@Valid @RequestBody CreateSalaCineRequestDto requestDto) {
        SalaCine nuevaSala = salaCineService.createSalaCine(requestDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Sala de cine creada exitosamente.");
        response.put("sala", nuevaSala);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/list")
    public ResponseEntity<List<SalaCine>> getAllSalas() {
        List<SalaCine> salas = salaCineService.getAllSalas();
        return ResponseEntity.ok(salas);
    }
}