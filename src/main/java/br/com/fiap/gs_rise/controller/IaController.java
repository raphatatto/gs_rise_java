package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.ia.SugestaoTrilhaRequestDTO;
import br.com.fiap.gs_rise.dto.ia.SugestaoTrilhaResponseDTO;
import br.com.fiap.gs_rise.service.IaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ia")
@RequiredArgsConstructor
public class IaController {

    private final IaService iaService;

    @PostMapping("/sugestoes-trilha")
    public ResponseEntity<SugestaoTrilhaResponseDTO> gerarSugestoes(
            @Valid @RequestBody SugestaoTrilhaRequestDTO dto) {

        var resposta = iaService.gerarSugestaoTrilha(dto);
        return ResponseEntity.ok(resposta);
    }
}
