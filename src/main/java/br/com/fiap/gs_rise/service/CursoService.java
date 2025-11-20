package br.com.fiap.gs_rise.service;
import br.com.fiap.gs_rise.dto.curso.CursoRequestDTO;
import br.com.fiap.gs_rise.dto.curso.CursoResponseDTO;
import br.com.fiap.gs_rise.exception.ResourceNotFoundException;
import br.com.fiap.gs_rise.model.Curso;
import br.com.fiap.gs_rise.model.Usuario;
import br.com.fiap.gs_rise.repository.CursoRepository;
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
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    private CursoResponseDTO toResponse(Curso curso) {
        return CursoResponseDTO.builder()
                .id(curso.getId())
                .nome(curso.getNome())
                .descricao(curso.getDescricao())
                .link(curso.getLink())
                .area(curso.getArea())
                .usuarioId(curso.getUsuario().getId())
                .build();
    }

    @Transactional
    @CacheEvict(value = "cursos", allEntries = true)
    public CursoResponseDTO criar(CursoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Curso curso = Curso.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .area(dto.getArea())
                .link(dto.getLink())
                .usuario(usuario)
                .build();

        return toResponse(cursoRepository.save(curso));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cursos", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<CursoResponseDTO> listar(Pageable pageable) {
        return cursoRepository.findAll(pageable).map(this::toResponse);
    }
}
