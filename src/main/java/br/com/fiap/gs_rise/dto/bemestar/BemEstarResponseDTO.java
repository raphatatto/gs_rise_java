package br.com.fiap.gs_rise.dto.bemestar;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BemEstarResponseDTO {

    private Integer id;
    private LocalDateTime dataRegistro;
    private Integer nivelHumor;
    private LocalDateTime horasEstudo;
    private String descricaoAtividade;
    private Integer usuarioId;
}
