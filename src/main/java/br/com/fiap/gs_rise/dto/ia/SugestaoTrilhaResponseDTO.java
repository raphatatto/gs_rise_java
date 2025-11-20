package br.com.fiap.gs_rise.dto.ia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SugestaoTrilhaResponseDTO {

    private String sugestaoGerada; // texto inteiro vindo da IA
}
