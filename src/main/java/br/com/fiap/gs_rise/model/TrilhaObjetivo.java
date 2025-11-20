package br.com.fiap.gs_rise.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_rise_trilha_objetivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrilhaObjetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo")
    private Integer id;

    @Column(name = "titulo_objetivo", length = 500)
    private String titulo;

    @Column(name = "categoria_objetivo", length = 500)
    private String categoria;

    @Column(name = "data_planejada")
    private LocalDateTime dataPlanejada;

    @Column(name = "concluido", length = 1)
    private String concluido; // 'S' / 'N' ou algo assim

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(name = "dt_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_trilha", nullable = false)
    private TrilhaProgresso trilha;
}
