package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.curriculo.CurriculoRequestDTO;
import br.com.fiap.gs_rise.dto.curriculo.CurriculoResponseDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.service.CurriculoService;
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
@RequestMapping("/app/curriculos")
@RequiredArgsConstructor
public class CurriculoViewController {

    private final CurriculoService curriculoService;
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
        List<CurriculoResponseDTO> curriculos = curriculoService
                .listar(PageRequest.of(0, 50, Sort.by("titulo")))
                .getContent();

        model.addAttribute("curriculos", curriculos);
        return "curriculos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("curriculo", new CurriculoRequestDTO());
        model.addAttribute("tituloPagina", "Cadastrar currículo");
        return "curriculos/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("curriculo") CurriculoRequestDTO curriculo,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tituloPagina", "Cadastrar currículo");
            return "curriculos/form";
        }

        curriculoService.criar(curriculo);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Currículo criado com sucesso!");
        return "redirect:/app/curriculos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        CurriculoResponseDTO curriculo = curriculoService.buscarPorId(id);
        CurriculoRequestDTO form = new CurriculoRequestDTO();
        form.setTitulo(curriculo.getTitulo());
        form.setFormacao(curriculo.getFormacao());
        form.setHabilidades(curriculo.getHabilidades());
        form.setProjetos(curriculo.getProjetos());
        form.setLinks(curriculo.getLinks());
        form.setExperienciaProfissional(curriculo.getExperienciaProfissional());
        form.setUsuarioId(curriculo.getUsuarioId());

        model.addAttribute("curriculo", form);
        model.addAttribute("curriculoId", id);
        model.addAttribute("tituloPagina", "Editar currículo");
        return "curriculos/form";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("curriculo") CurriculoRequestDTO curriculo,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("curriculoId", id);
            model.addAttribute("tituloPagina", "Editar currículo");
            return "curriculos/form";
        }

        curriculoService.atualizar(id, curriculo);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Currículo atualizado com sucesso!");
        return "redirect:/app/curriculos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        curriculoService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Currículo removido!");
        return "redirect:/app/curriculos";
    }
}
