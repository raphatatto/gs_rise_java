package br.com.fiap.gs_rise.dto.ia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SugestaoTrilhaRequestDTO {

    @NotBlank
    @Size(max = 500)
    private String nomeUsuario;

    @NotBlank
    @Size(max = 1000)
    private String objetivoPrincipal; // ex: "mudar de área para dados"

    @Size(max = 1000)
    private String interesses; // ex: "backend, cloud, devops"

    @Size(max = 1000)
    private String contextoAtual; // ex: "estágio em suporte, 3° semestre ADS"
}
