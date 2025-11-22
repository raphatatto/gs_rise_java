package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.curso.CursoRequestDTO;
import br.com.fiap.gs_rise.dto.curso.CursoResponseDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.service.CursoService;
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
@RequestMapping("/app/cursos")
@RequiredArgsConstructor
public class CursoViewController {

    private final CursoService cursoService;
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
        List<CursoResponseDTO> cursos = cursoService
                .listar(PageRequest.of(0, 50, Sort.by("nome")))
                .getContent();

        model.addAttribute("cursos", cursos);
        return "cursos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("curso", new CursoRequestDTO());
        model.addAttribute("tituloPagina", "Cadastrar curso");
        return "cursos/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("curso") CursoRequestDTO curso,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tituloPagina", "Cadastrar curso");
            return "cursos/form";
        }

        cursoService.criar(curso);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Curso criado com sucesso!");
        return "redirect:/app/cursos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        CursoResponseDTO curso = cursoService.buscarPorId(id);
        CursoRequestDTO form = new CursoRequestDTO();
        form.setNome(curso.getNome());
        form.setDescricao(curso.getDescricao());
        form.setArea(curso.getArea());
        form.setLink(curso.getLink());
        form.setUsuarioId(curso.getUsuarioId());

        model.addAttribute("curso", form);
        model.addAttribute("cursoId", id);
        model.addAttribute("tituloPagina", "Editar curso");
        return "cursos/form";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("curso") CursoRequestDTO curso,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cursoId", id);
            model.addAttribute("tituloPagina", "Editar curso");
            return "cursos/form";
        }

        cursoService.atualizar(id, curso);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Curso atualizado com sucesso!");
        return "redirect:/app/cursos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cursoService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Curso removido!");
        return "redirect:/app/cursos";
    }
}
