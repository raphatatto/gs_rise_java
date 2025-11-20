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

    @Transactional(readOnly = true)
    public CurriculoResponseDTO buscarPorUsuario(Integer idUsuario) {
        return repository.findByUsuarioId(idUsuario)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado"));
    }
    @Transactional(readOnly = true)
    public Page<CurriculoResponseDTO> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }
}

