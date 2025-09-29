package org.example.pruebaspring.services.impl;

import org.example.pruebaspring.dto.BuscarPorNombreSalaRequestDto;
import org.example.pruebaspring.dto.CreateSalaCineRequestDto;
import org.example.pruebaspring.entity.Pelicula;
import org.example.pruebaspring.entity.SalaCine;
import org.example.pruebaspring.repository.SalaCineRepository;
import org.example.pruebaspring.services.ISalaCineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaCineServiceImpl implements ISalaCineService {

    @Autowired
    private SalaCineRepository salaCineRepository;

    @Override
    public SalaCine createSalaCine(CreateSalaCineRequestDto requestDto) {
        SalaCine salaCine = new SalaCine();
        salaCine.setNombre(requestDto.getNombre());
        salaCine.setEstado(1);
        return salaCineRepository.save(salaCine);
    }

    @Override
    public List<SalaCine> getAllSalas() {
        return salaCineRepository.findAll();
    }

    @Override
    public List<Pelicula> buscarPeliculasPorNombreSala(BuscarPorNombreSalaRequestDto requestDto) {
        Optional<SalaCine> salaOptional = salaCineRepository.findByNombreIgnoreCase(requestDto.getNombreSala());

        if (salaOptional.isPresent()) {
            SalaCine sala = salaOptional.get();
            return sala.getPeliculaSalacines().stream()
                    .map(asignacion -> asignacion.getPelicula()) // Obtiene la Pelicula de cada asignación
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}