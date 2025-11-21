package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoResponseDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.service.TrilhaProgressoService;
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
@RequestMapping("/api/v1/trilhas")
@RequiredArgsConstructor
public class TrilhaProgressoController {

    private final TrilhaProgressoService service;

    @PostMapping
    public ResponseEntity<TrilhaProgressoResponseDTO> criar(@Valid @RequestBody TrilhaProgressoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TrilhaProgressoResponseDTO> atualizar(@PathVariable Integer id,
                                                                @Valid @RequestBody TrilhaProgressoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<TrilhaProgressoResponseDTO>> listar(@ParameterObject Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<TrilhaProgressoResponseDTO>> listarPorUsuario(
            @PathVariable Integer usuarioId, Pageable pageable) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId, pageable));
    }
}
