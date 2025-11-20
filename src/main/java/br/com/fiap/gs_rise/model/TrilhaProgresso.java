package br.com.fiap.gs_rise.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_rise_trilha_progresso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrilhaProgresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trilha")
    private Integer id;

    @Column(name = "percentual_concluido")
    private Integer percentualConcluido;

    @Column(name = "dt_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "dt_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;

    @Column(name = "titulo_trilha", length = 500)
    private String titulo;

    @Column(name = "categoria_trilha", length = 500)
    private String categoria;

    @Column(name = "data_planejada")
    private LocalDateTime dataPlanejada;

    @Column(name = "dt_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrilhaObjetivo> objetivos;
}
