package br.com.fiap.gs_rise.repository;

import br.com.fiap.gs_rise.model.TrilhaObjetivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrilhaObjetivoRepository extends JpaRepository<TrilhaObjetivo, Integer> {

    Page<TrilhaObjetivo> findByTrilhaId(Integer idTrilha, Pageable pageable);

    List<TrilhaObjetivo> findByConcluido(String concluido);
}
