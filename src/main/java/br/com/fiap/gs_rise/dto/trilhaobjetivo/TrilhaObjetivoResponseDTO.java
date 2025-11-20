package br.com.fiap.gs_rise.dto.trilhaobjetivo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrilhaObjetivoResponseDTO {
    private Integer id;
    private String titulo;
    private String categoria;
    private String concluido;
    private Integer trilhaId;
}

