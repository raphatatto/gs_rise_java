package br.com.fiap.gs_rise.dto.trilhaprogresso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrilhaProgressoResponseDTO {
    private Integer id;
    private String titulo;
    private String categoria;
    private Integer usuarioId;
}
