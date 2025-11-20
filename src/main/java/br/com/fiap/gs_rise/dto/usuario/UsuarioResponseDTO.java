package br.com.fiap.gs_rise.dto.usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private String tipo;
    private String telefone;
    private String descricao;
    private String habilidades;
}
