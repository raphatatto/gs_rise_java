package br.com.fiap.gs_rise.service;

import br.com.fiap.gs_rise.dto.curriculo.CurriculoRequestDTO;
import br.com.fiap.gs_rise.dto.curriculo.CurriculoResponseDTO;
import br.com.fiap.gs_rise.exception.ResourceNotFoundException;
import br.com.fiap.gs_rise.model.Curriculo;
import br.com.fiap.gs_rise.model.Usuario;
import br.com.fiap.gs_rise.repository.CurriculoRepository;
import br.com.fiap.gs_rise.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository repository;
    private final UsuarioRepository usuarioRepository;

    private CurriculoResponseDTO toResponse(Curriculo curriculo) {
        return CurriculoResponseDTO.builder()
                .id(curriculo.getId())
                .usuarioId(curriculo.getUsuario().getId())
                .titulo(curriculo.getTitulo())
                .formacao(curriculo.getFormacao())
                .habilidades(curriculo.getHabilidades())
                .projetos(curriculo.getProjetos())
                .links(curriculo.getLinks())
                .experienciaProfissional(curriculo.getExperienciaProfissional())
                .build();
    }

    @Transactional
    public CurriculoResponseDTO criar(CurriculoRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Curriculo curriculo = Curriculo.builder()
                .usuario(usuario)
                .titulo(dto.getTitulo())
                .formacao(dto.getFormacao())
                .habilidades(dto.getHabilidades())
                .projetos(dto.getProjetos())
                .links(dto.getLinks())
                .build();

        return toResponse(repository.save(curriculo));
    }

    @Transactional
    public CurriculoResponseDTO atualizar(Integer id, CurriculoRequestDTO dto) {
        Curriculo curriculo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        curriculo.setUsuario(usuario);
        curriculo.setTitulo(dto.getTitulo());
        curriculo.setFormacao(dto.getFormacao());
        curriculo.setHabilidades(dto.getHabilidades());
        curriculo.setProjetos(dto.getProjetos());
        curriculo.setLinks(dto.getLinks());
        curriculo.setExperienciaProfissional(dto.getExperienciaProfissional());
        curriculo.setUltimaAtualizacao(LocalDateTime.now());

        return toResponse(repository.save(curriculo));
    }

    @Transactional(readOnly = true)
    public CurriculoResponseDTO buscarPorUsuario(Integer idUsuario) {
        return repository.findByUsuarioId(idUsuario)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado"));
    }

    @Transactional(readOnly = true)
    public CurriculoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado"));
    }
    @Transactional(readOnly = true)
    public Page<CurriculoResponseDTO> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Currículo não encontrado");
        }
        repository.deleteById(id);
    }
}

