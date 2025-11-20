package br.com.fiap.gs_rise.repository;

import br.com.fiap.gs_rise.model.BemEstar;
import br.com.fiap.gs_rise.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BemEstarRepository extends JpaRepository<BemEstar, Integer> {

    List<BemEstar> findByUsuario(Usuario usuario);

    List<BemEstar> findByUsuarioId(Integer idUsuario);
}
