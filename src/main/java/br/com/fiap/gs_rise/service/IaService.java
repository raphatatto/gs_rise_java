package br.com.fiap.gs_rise.service;

import br.com.fiap.gs_rise.dto.ia.SugestaoTrilhaRequestDTO;
import br.com.fiap.gs_rise.dto.ia.SugestaoTrilhaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class IaService {

    // ChatClient da API nova (org.springframework.ai.chat.client.ChatClient)
    private final ChatClient chatClient;

    public SugestaoTrilhaResponseDTO gerarSugestaoTrilha(SugestaoTrilhaRequestDTO dto) {

        String template = """
                Você é um assistente especializado em desenvolvimento de carreira e bem-estar.

                Usuário: {nome}
                Objetivo principal: {objetivo}
                Interesses: {interesses}
                Contexto atual: {contexto}

                Gere uma resposta estruturada com:
                1. Uma mensagem motivacional curta para o usuário.
                2. 3 a 5 sugestões de trilhas (com nome e breve descrição).
                3. Para cada trilha, sugira:
                   - habilidades a desenvolver
                   - tipos de cursos/atividades que ele pode buscar
                   - pequenos objetivos de curto prazo.
                Escreva em linguagem simples e direta.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);

        Prompt prompt = promptTemplate.create(Map.of(
                "nome", dto.getNomeUsuario(),
                "objetivo", dto.getObjetivoPrincipal(),
                "interesses", dto.getInteresses() == null ? "" : dto.getInteresses(),
                "contexto", dto.getContextoAtual() == null ? "" : dto.getContextoAtual()
        ));

        // ✅ API correta do ChatClient na 1.1.x
        String texto = chatClient
                .prompt(prompt)   // passa o Prompt
                .call()           // chama o modelo
                .content();       // pega só o texto da resposta

        return SugestaoTrilhaResponseDTO.builder()
                .sugestaoGerada(texto)
                .build();
    }
}
