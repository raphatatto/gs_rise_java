package br.com.fiap.gs_rise.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_rise_curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer id;

    @Column(name = "nome_curso", length = 500)
    private String nome;

    @Column(name = "desc_curso", length = 500)
    private String descricao;

    @Column(name = "link_curso", length = 500)
    private String link;

    @Column(name = "area_curso", length = 500)
    private String area;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
