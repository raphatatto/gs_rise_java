package br.com.fiap.gs_rise.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_rise_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nome__usuario", nullable = false, length = 500)
    private String nome;

    @Column(name = "email_usuario", length = 50)
    private String email;

    @Column(name = "senha_usuario", nullable = false, length = 50)
    private String senha;

    @Column(name = "tipo_usuario", length = 50)
    private String tipo;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "desc", length = 500)
    private String descricao;

    @Column(name = "habilidades", length = 500)
    private String habilidades;

    // Relacionamentos

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BemEstar> registrosBemEstar;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrilhaProgresso> trilhas;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Curriculo curriculo;
}
