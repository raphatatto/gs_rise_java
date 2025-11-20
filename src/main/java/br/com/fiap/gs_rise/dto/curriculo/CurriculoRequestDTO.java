package br.com.fiap.gs_rise.dto.curriculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CurriculoRequestDTO {

    @NotNull(message = "{curriculo.usuario.obrigatorio}")
    private Integer usuarioId;

    @NotBlank(message = "{curriculo.titulo.obrigatorio}")
    @Size(max = 100)
    private String titulo;


    @Size(max = 1000)
    private String experienciaProfissional;

    @Size(max = 1000)
    private String habilidades;

    @Size(max = 1000)
    private String formacao;

    @Size(max = 100)
    private String projetos;

    @Size(max = 100)
    private String links;
}
