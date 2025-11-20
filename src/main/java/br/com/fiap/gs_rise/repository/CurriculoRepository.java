package br.com.fiap.gs_rise.repository;

import br.com.fiap.gs_rise.model.Curriculo;
import br.com.fiap.gs_rise.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurriculoRepository extends JpaRepository<Curriculo, Integer> {

    Optional<Curriculo> findByUsuario(Usuario usuario);

    Optional<Curriculo> findByUsuarioId(Integer idUsuario);
}
