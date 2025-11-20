package br.com.fiap.gs_rise.repository;

import br.com.fiap.gs_rise.model.TrilhaProgresso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrilhaProgressoRepository extends JpaRepository<TrilhaProgresso, Integer> {

    Page<TrilhaProgresso> findByUsuarioId(Integer idUsuario, Pageable pageable);
}
