package br.com.fiap.gs_rise.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_rise_curriculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curriculo")
    private Integer id;

    @Column(name = "titulo_curriculo", length = 100)
    private String titulo;

    @Column(name = "experiencia_profissional", length = 1000)
    private String experienciaProfissional;

    @Column(name = "habilidades", length = 1000)
    private String habilidades;

    @Column(name = "formacao", length = 1000)
    private String formacao;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "projetos", length = 100)
    private String projetos;

    @Column(name = "links", length = 100)
    private String links;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;
}
