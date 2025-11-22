package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.ia.SugestaoTrilhaRequestDTO;
import br.com.fiap.gs_rise.dto.ia.SugestaoTrilhaResponseDTO;
import br.com.fiap.gs_rise.service.IaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/ia")
@RequiredArgsConstructor
public class IaViewController {

    private final IaService iaService;

    @GetMapping
    public String formulario(Model model) {
        model.addAttribute("requisicao", new SugestaoTrilhaRequestDTO());
        return "ia/sugestoes";
    }

    @PostMapping
    public String gerarSugestoes(@Valid @ModelAttribute("requisicao") SugestaoTrilhaRequestDTO requisicao,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            return "ia/sugestoes";
        }

        SugestaoTrilhaResponseDTO resposta = iaService.gerarSugestaoTrilha(requisicao);
        model.addAttribute("sugestao", resposta.getSugestaoGerada());
        return "ia/sugestoes";
    }
}
