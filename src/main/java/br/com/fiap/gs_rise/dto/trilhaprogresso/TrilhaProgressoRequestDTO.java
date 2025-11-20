package br.com.fiap.gs_rise.dto.trilhaprogresso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrilhaProgressoRequestDTO {

    @NotNull
    private Integer usuarioId;

    @NotBlank
    @Size(max = 500)
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String categoria;
}
