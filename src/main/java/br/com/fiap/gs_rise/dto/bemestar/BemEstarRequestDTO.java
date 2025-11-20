package br.com.fiap.gs_rise.dto.bemestar;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BemEstarRequestDTO {

    @NotNull(message = "{bemestar.usuario.obrigatorio}")
    private Integer usuarioId;

    private LocalDateTime dataRegistro;
    private Integer nivelHumor;
    private LocalDateTime horasEstudo;
    private String descricaoAtividade;
}
