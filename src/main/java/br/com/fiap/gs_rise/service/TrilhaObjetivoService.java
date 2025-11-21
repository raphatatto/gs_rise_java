package br.com.fiap.gs_rise.service;

import br.com.fiap.gs_rise.dto.trilhaobjetivo.TrilhaObjetivoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaobjetivo.TrilhaObjetivoResponseDTO;
import br.com.fiap.gs_rise.exception.ResourceNotFoundException;
import br.com.fiap.gs_rise.model.TrilhaObjetivo;
import br.com.fiap.gs_rise.repository.TrilhaObjetivoRepository;
import br.com.fiap.gs_rise.repository.TrilhaProgressoRepository;
import br.com.fiap.gs_rise.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.fiap.gs_rise.config.RabbitMQConfig.EXCHANGE_OBJETIVOS;
import static br.com.fiap.gs_rise.config.RabbitMQConfig.ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class TrilhaObjetivoService {

    private final RabbitTemplate rabbitTemplate;
    private final TrilhaObjetivoRepository repository;
    private final TrilhaProgressoRepository trilhaRepository;
    private final UsuarioRepository usuarioRepository;

    private TrilhaObjetivoResponseDTO toResponse(TrilhaObjetivo objetivo) {
        return TrilhaObjetivoResponseDTO.builder()
                .id(objetivo.getId())
                .titulo(objetivo.getTitulo())
                .categoria(objetivo.getCategoria())
                .concluido(objetivo.getConcluido())
                .trilhaId(objetivo.getTrilha().getId())
                .build();
    }

    @Transactional
    public TrilhaObjetivoResponseDTO criar(TrilhaObjetivoRequestDTO dto) {
        var trilha = trilhaRepository.findById(dto.getTrilhaId())
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada"));

        var objetivo = TrilhaObjetivo.builder()
                .titulo(dto.getTitulo())
                .categoria(dto.getCategoria())
                .concluido(dto.getConcluido())
                .trilha(trilha)
                .build();

        var salvo = repository.save(objetivo);

        // 🔥 ENVIA PARA A FILA
        rabbitTemplate.convertAndSend(
                EXCHANGE_OBJETIVOS,
                ROUTING_KEY,
                "Novo objetivo criado: " + salvo.getTitulo()
        );

        return toResponse(salvo);
    }
    @Transactional
    public TrilhaObjetivoResponseDTO atualizar(Integer id, TrilhaObjetivoRequestDTO dto) {
        var objetivo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objetivo não encontrado"));

        var trilha = trilhaRepository.findById(dto.getTrilhaId())
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada"));

        objetivo.setTitulo(dto.getTitulo());
        objetivo.setCategoria(dto.getCategoria());
        objetivo.setConcluido(dto.getConcluido());
        objetivo.setTrilha(trilha);

        return toResponse(repository.save(objetivo));
    }
    @Transactional(readOnly = true)
    public Page<TrilhaObjetivoResponseDTO> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TrilhaObjetivoResponseDTO> listarPorTrilha(Integer trilhaId, Pageable pageable) {
        return repository.findByTrilhaId(trilhaId, pageable)
                .map(this::toResponse);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Objetivo não encontrado");
        }
        repository.deleteById(id);
    }

}

