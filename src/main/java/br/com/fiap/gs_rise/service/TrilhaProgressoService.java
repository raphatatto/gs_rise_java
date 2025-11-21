package br.com.fiap.gs_rise.service;

import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoResponseDTO;
import br.com.fiap.gs_rise.exception.ResourceNotFoundException;
import br.com.fiap.gs_rise.model.TrilhaProgresso;
import br.com.fiap.gs_rise.model.Usuario;
import br.com.fiap.gs_rise.repository.TrilhaProgressoRepository;
import br.com.fiap.gs_rise.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrilhaProgressoService {

    private final TrilhaProgressoRepository repository;
    private final UsuarioRepository usuarioRepository;

    private TrilhaProgressoResponseDTO toResponse(TrilhaProgresso trilha) {
        return TrilhaProgressoResponseDTO.builder()
                .id(trilha.getId())
                .titulo(trilha.getTitulo())
                .categoria(trilha.getCategoria())
                .usuarioId(trilha.getUsuario().getId())
                .build();
    }

    @Transactional
    @CacheEvict(value = "trilhas", allEntries = true)
    public TrilhaProgressoResponseDTO criar(TrilhaProgressoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        TrilhaProgresso trilha = TrilhaProgresso.builder()
                .titulo(dto.getTitulo())
                .categoria(dto.getCategoria())
                .usuario(usuario)
                .build();

        return toResponse(repository.save(trilha));
    }
    @Transactional
    @CacheEvict(value = "trilhas", allEntries = true)
    public TrilhaProgressoResponseDTO atualizar(Integer id, TrilhaProgressoRequestDTO dto) {
        TrilhaProgresso trilha = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        trilha.setTitulo(dto.getTitulo());
        trilha.setCategoria(dto.getCategoria());
        trilha.setUsuario(usuario);

        return toResponse(repository.save(trilha));
    }
    @Transactional(readOnly = true)
    public Page<TrilhaProgressoResponseDTO> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "trilhas", key = "#usuarioId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<TrilhaProgressoResponseDTO> listarPorUsuario(Integer usuarioId, Pageable pageable) {
        return repository.findByUsuarioId(usuarioId, pageable)
                .map(this::toResponse);
    }

    @Transactional
    @CacheEvict(value = "trilhas", allEntries = true)
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Trilha não encontrada");
        }
        repository.deleteById(id);
    }
}
