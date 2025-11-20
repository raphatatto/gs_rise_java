package br.com.fiap.gs_rise.dto.curriculo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurriculoResponseDTO {
    private Integer id;
    private Integer usuarioId;
    private String titulo;
    private String formacao;
    private String habilidades;
    private String projetos;
    private String links;
}
