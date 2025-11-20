package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.curso.CursoRequestDTO;
import br.com.fiap.gs_rise.dto.curso.CursoResponseDTO;
import br.com.fiap.gs_rise.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService service;

    @GetMapping
    public ResponseEntity<Page<CursoResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> criar(@Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
}

