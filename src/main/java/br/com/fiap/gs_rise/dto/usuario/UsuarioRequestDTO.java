package br.com.fiap.gs_rise.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "{usuario.nome.obrigatorio}")
    @Size(max = 500)
    private String nome;

    @jakarta.validation.constraints.Email(message = "{usuario.email.invalido}")
    @NotBlank(message = "{usuario.email.obrigatorio}")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "{usuario.senha.obrigatoria}")
    @Size(max = 50)
    private String senha;

    @Size(max = 50)
    private String tipo;

    @Size(max = 20)
    private String telefone;

    @Size(max = 500)
    private String descricao;

    @Size(max = 500)
    private String habilidades;
}
