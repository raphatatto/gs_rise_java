package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.bemestar.BemEstarRequestDTO;
import br.com.fiap.gs_rise.dto.bemestar.BemEstarResponseDTO;
import br.com.fiap.gs_rise.service.BemEstarService;
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
@RequestMapping("/api/v1/bemestar")
@RequiredArgsConstructor
public class BemEstarController {

    private final BemEstarService service;

    @GetMapping
    public ResponseEntity<Page<BemEstarResponseDTO>> listar(@ParameterObject Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PostMapping
    public ResponseEntity<BemEstarResponseDTO> criar(@Valid @RequestBody BemEstarRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
