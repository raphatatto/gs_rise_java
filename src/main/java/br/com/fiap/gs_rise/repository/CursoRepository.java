package br.com.fiap.gs_rise.repository;

import br.com.fiap.gs_rise.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    List<Curso> findByUsuarioId(Integer idUsuario);

    List<Curso> findByArea(String area);
}
