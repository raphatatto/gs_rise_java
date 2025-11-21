package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.trilhaobjetivo.TrilhaObjetivoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaobjetivo.TrilhaObjetivoResponseDTO;
import br.com.fiap.gs_rise.service.TrilhaObjetivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/objetivos")
@RequiredArgsConstructor
public class TrilhaObjetivoController {

    private final TrilhaObjetivoService service;

    @PostMapping
    public ResponseEntity<TrilhaObjetivoResponseDTO> criar(@Valid @RequestBody TrilhaObjetivoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TrilhaObjetivoResponseDTO> atualizar(@PathVariable Integer id,
                                                               @Valid @RequestBody TrilhaObjetivoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<TrilhaObjetivoResponseDTO>> listar(@ParameterObject Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/trilha/{trilhaId}")
    public ResponseEntity<Page<TrilhaObjetivoResponseDTO>> listarPorTrilha(
            @PathVariable Integer trilhaId, Pageable pageable) {
        return ResponseEntity.ok(service.listarPorTrilha(trilhaId, pageable));
    }

}
