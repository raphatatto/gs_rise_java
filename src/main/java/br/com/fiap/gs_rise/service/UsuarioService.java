package br.com.fiap.gs_rise.service;

import br.com.fiap.gs_rise.dto.usuario.UsuarioRequestDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.exception.BusinessException;
import br.com.fiap.gs_rise.exception.ResourceNotFoundException;
import br.com.fiap.gs_rise.model.Usuario;
import br.com.fiap.gs_rise.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private UsuarioResponseDTO toResponse(Usuario entity) {
        return UsuarioResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .tipo(entity.getTipo())
                .telefone(entity.getTelefone())
                .descricao(entity.getDescricao())
                .habilidades(entity.getHabilidades())
                .build();
    }

    private void updateEntityFromDto(UsuarioRequestDTO dto, Usuario entity) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(passwordEncoder.encode(dto.getSenha())); // <<< AQUI
        entity.setTipo(dto.getTipo());
        entity.setTelefone(dto.getTelefone());
        entity.setDescricao(dto.getDescricao());
        entity.setHabilidades(dto.getHabilidades());
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "usuarios", key = "#id")
    public UsuarioResponseDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado")); // depois podemos trocar por exception customizada
        return toResponse(usuario);
    }

    @Transactional
    @CacheEvict(value = "usuarios", key = "#result.id", condition = "#result != null")
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        updateEntityFromDto(dto, usuario);
        Usuario salvo = usuarioRepository.save(usuario);
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        return toResponse(salvo);

    }

    @Transactional
    @CacheEvict(value = "usuarios", key = "#id")
    public UsuarioResponseDTO atualizar(Integer id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        updateEntityFromDto(dto, usuario);
        Usuario atualizado = usuarioRepository.save(usuario);
        return toResponse(atualizado);
    }

    @Transactional
    @CacheEvict(value = "usuarios", key = "#id")
    public void deletar(Integer id) {

        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
