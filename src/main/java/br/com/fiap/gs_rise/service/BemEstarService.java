package br.com.fiap.gs_rise.service;

import br.com.fiap.gs_rise.dto.bemestar.BemEstarRequestDTO;
import br.com.fiap.gs_rise.dto.bemestar.BemEstarResponseDTO;
import br.com.fiap.gs_rise.exception.ResourceNotFoundException;
import br.com.fiap.gs_rise.model.BemEstar;
import br.com.fiap.gs_rise.model.Usuario;
import br.com.fiap.gs_rise.repository.BemEstarRepository;
import br.com.fiap.gs_rise.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BemEstarService {

    private final BemEstarRepository bemEstarRepository;
    private final UsuarioRepository usuarioRepository;

    private BemEstarResponseDTO toResponse(BemEstar entity) {
        return BemEstarResponseDTO.builder()
                .id(entity.getId())
                .dataRegistro(entity.getDataRegistro())
                .nivelHumor(entity.getNivelHumor())
                .horasEstudo(entity.getHorasEstudo())
                .descricaoAtividade(entity.getDescricaoAtividade())
                .usuarioId(entity.getUsuario().getId())
                .build();
    }

    @Transactional
    public BemEstarResponseDTO criar(BemEstarRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        BemEstar registro = BemEstar.builder()
                .dataRegistro(dto.getDataRegistro())
                .nivelHumor(dto.getNivelHumor())
                .horasEstudo(dto.getHorasEstudo())
                .descricaoAtividade(dto.getDescricaoAtividade())
                .usuario(usuario)
                .build();

        return toResponse(bemEstarRepository.save(registro));
    }

    @Transactional
    public BemEstarResponseDTO atualizar(Integer id, BemEstarRequestDTO dto) {
        BemEstar registro = bemEstarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de bem-estar não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        registro.setDataRegistro(dto.getDataRegistro());
        registro.setNivelHumor(dto.getNivelHumor());
        registro.setHorasEstudo(dto.getHorasEstudo());
        registro.setDescricaoAtividade(dto.getDescricaoAtividade());
        registro.setUsuario(usuario);

        return toResponse(bemEstarRepository.save(registro));
    }

    @Transactional(readOnly = true)
    public Page<BemEstarResponseDTO> listar(Pageable pageable) {
        return bemEstarRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public void deletar(Integer id) {
        bemEstarRepository.deleteById(id);
    }
}
