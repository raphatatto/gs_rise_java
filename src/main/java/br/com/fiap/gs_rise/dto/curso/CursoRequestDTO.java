package br.com.fiap.gs_rise.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CursoRequestDTO {

    @NotNull(message = "O usuário é obrigatório")
    private Integer usuarioId;

    @NotBlank(message = "O nome do curso é obrigatório")
    @Size(max = 500)
    private String nome;

    @Size(max = 500)
    private String descricao;

    @Size(max = 500)
    private String link;

    @Size(max = 500)
    private String area;
}
