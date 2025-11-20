package br.com.fiap.gs_rise.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_rise_bem_estar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BemEstar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bem_estar")
    private Integer id;

    @Column(name = "dt_registro")
    private LocalDateTime dataRegistro;

    @Column(name = "nivel_humor")
    private Integer nivelHumor;

    @Column(name = "horas_estudo")
    private LocalDateTime horasEstudo;

    @Column(name = "desc_atividade", length = 50)
    private String descricaoAtividade;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
