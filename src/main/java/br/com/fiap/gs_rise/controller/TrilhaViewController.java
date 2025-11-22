package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoResponseDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.service.TrilhaProgressoService;
import br.com.fiap.gs_rise.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/trilhas")
@RequiredArgsConstructor
public class TrilhaViewController {

    private final TrilhaProgressoService trilhaService;
    private final UsuarioService usuarioService;

    @ModelAttribute("usuariosDisponiveis")
    public List<UsuarioResponseDTO> usuariosDisponiveis() {
        return usuarioService.listarTodos();
    }

    @ModelAttribute("usuariosPorId")
    public Map<Integer, String> usuariosPorId() {
        return usuarioService.listarTodos().stream()
                .collect(Collectors.toMap(UsuarioResponseDTO::getId, UsuarioResponseDTO::getNome, (a, b) -> a));
    }

    @GetMapping
    public String listar(Model model) {
        List<TrilhaProgressoResponseDTO> trilhas = trilhaService
                .listar(PageRequest.of(0, 50, Sort.by("titulo")))
                .getContent();

        model.addAttribute("trilhas", trilhas);
        return "trilhas/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("trilha", new TrilhaProgressoRequestDTO());
        model.addAttribute("tituloPagina", "Cadastrar trilha");
        return "trilhas/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("trilha") TrilhaProgressoRequestDTO trilha,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tituloPagina", "Cadastrar trilha");
            return "trilhas/form";
        }

        trilhaService.criar(trilha);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Trilha criada com sucesso!");
        return "redirect:/app/trilhas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        TrilhaProgressoResponseDTO trilha = trilhaService.buscarPorId(id);
        TrilhaProgressoRequestDTO form = new TrilhaProgressoRequestDTO();
        form.setTitulo(trilha.getTitulo());
        form.setCategoria(trilha.getCategoria());
        form.setUsuarioId(trilha.getUsuarioId());

        model.addAttribute("trilha", form);
        model.addAttribute("trilhaId", id);
        model.addAttribute("tituloPagina", "Editar trilha");
        return "trilhas/form";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("trilha") TrilhaProgressoRequestDTO trilha,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trilhaId", id);
            model.addAttribute("tituloPagina", "Editar trilha");
            return "trilhas/form";
        }

        trilhaService.atualizar(id, trilha);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Trilha atualizada com sucesso!");
        return "redirect:/app/trilhas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        trilhaService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Trilha removida!");
        return "redirect:/app/trilhas";
    }
}
