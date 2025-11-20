package br.com.fiap.gs_rise.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String mensagem;
    private List<String> erros;
    private String path;
}
