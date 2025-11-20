package br.com.fiap.gs_rise.dto.trilhaobjetivo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrilhaObjetivoRequestDTO {

    @NotNull
    private Integer trilhaId;

    @NotBlank
    @Size(max = 500)
    private String titulo;

    @Size(max = 500)
    private String categoria;

    @Size(max = 1)
    private String concluido; // usar S/N
}
