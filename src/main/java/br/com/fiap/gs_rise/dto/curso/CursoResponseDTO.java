package br.com.fiap.gs_rise.dto.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CursoResponseDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private String link;
    private String area;
    private Integer usuarioId;
}
