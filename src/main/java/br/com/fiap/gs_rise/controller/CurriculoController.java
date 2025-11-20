package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.curriculo.CurriculoRequestDTO;
import br.com.fiap.gs_rise.dto.curriculo.CurriculoResponseDTO;
import br.com.fiap.gs_rise.service.CurriculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/curriculos")
@RequiredArgsConstructor
public class CurriculoController {

    private final CurriculoService service;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CurriculoResponseDTO> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.buscarPorUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<CurriculoResponseDTO> criar(@Valid @RequestBody CurriculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
    @GetMapping
    public ResponseEntity<Page<CurriculoResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

}
