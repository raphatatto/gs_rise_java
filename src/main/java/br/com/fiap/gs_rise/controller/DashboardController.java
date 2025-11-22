package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.bemestar.BemEstarRequestDTO;
import br.com.fiap.gs_rise.dto.curriculo.CurriculoRequestDTO;
import br.com.fiap.gs_rise.dto.curso.CursoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaobjetivo.TrilhaObjetivoRequestDTO;
import br.com.fiap.gs_rise.dto.trilhaprogresso.TrilhaProgressoRequestDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioRequestDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.service.BemEstarService;
import br.com.fiap.gs_rise.service.CurriculoService;
import br.com.fiap.gs_rise.service.CursoService;
import br.com.fiap.gs_rise.service.TrilhaObjetivoService;
import br.com.fiap.gs_rise.service.TrilhaProgressoService;
import br.com.fiap.gs_rise.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@RequiredArgsConstructor
public class DashboardController {

    private final UsuarioService usuarioService;
    private final CursoService cursoService;
    private final CurriculoService curriculoService;
    private final TrilhaProgressoService trilhaProgressoService;
    private final TrilhaObjetivoService trilhaObjetivoService;
    private final BemEstarService bemEstarService;

    @GetMapping
    public String dashboard(Model model) {
        loadDashboardData(model);
        return "dashboard";
    }

    @PostMapping("/usuarios")
    public String criarUsuario(@Valid @ModelAttribute("usuarioForm") UsuarioRequestDTO dto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            loadDashboardData(model);
            model.addAttribute("activeSection", "usuarios");
            return "dashboard";
        }

        usuarioService.criar(dto);
        return "redirect:/app?created=usuario";
    }

    @PostMapping("/cursos")
    public String criarCurso(@Valid @ModelAttribute("cursoForm") CursoRequestDTO dto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            loadDashboardData(model);
            model.addAttribute("activeSection", "cursos");
            return "dashboard";
        }

        cursoService.criar(dto);
        return "redirect:/app?created=curso";
    }

    @PostMapping("/curriculos")
    public String criarCurriculo(@Valid @ModelAttribute("curriculoForm") CurriculoRequestDTO dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            loadDashboardData(model);
            model.addAttribute("activeSection", "curriculos");
            return "dashboard";
        }

        curriculoService.criar(dto);
        return "redirect:/app?created=curriculo";
    }

    @PostMapping("/trilhas")
    public String criarTrilha(@Valid @ModelAttribute("trilhaForm") TrilhaProgressoRequestDTO dto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            loadDashboardData(model);
            model.addAttribute("activeSection", "trilhas");
            return "dashboard";
        }

        trilhaProgressoService.criar(dto);
        return "redirect:/app?created=trilha";
    }

    @PostMapping("/objetivos")
    public String criarObjetivo(@Valid @ModelAttribute("objetivoForm") TrilhaObjetivoRequestDTO dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            loadDashboardData(model);
            model.addAttribute("activeSection", "objetivos");
            return "dashboard";
        }

        trilhaObjetivoService.criar(dto);
        return "redirect:/app?created=objetivo";
    }

    @PostMapping("/bemestar")
    public String criarBemEstar(@Valid @ModelAttribute("bemEstarForm") BemEstarRequestDTO dto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            loadDashboardData(model);
            model.addAttribute("activeSection", "bemestar");
            return "dashboard";
        }

        bemEstarService.criar(dto);
        return "redirect:/app?created=bemestar";
    }

    private void loadDashboardData(Model model) {
        Page<UsuarioResponseDTO> usuarios = usuarioService.listar(PageRequest.of(0, 20));
        model.addAttribute("usuarios", usuarios.getContent());
        model.addAttribute("usuariosTotal", usuarios.getTotalElements());

        var cursos = cursoService.listar(PageRequest.of(0, 20));
        model.addAttribute("cursos", cursos.getContent());
        model.addAttribute("cursosTotal", cursos.getTotalElements());

        var curriculos = curriculoService.listar(PageRequest.of(0, 20));
        model.addAttribute("curriculos", curriculos.getContent());
        model.addAttribute("curriculosTotal", curriculos.getTotalElements());

        var trilhas = trilhaProgressoService.listar(PageRequest.of(0, 20));
        model.addAttribute("trilhas", trilhas.getContent());
        model.addAttribute("trilhasTotal", trilhas.getTotalElements());

        var objetivos = trilhaObjetivoService.listar(PageRequest.of(0, 20));
        model.addAttribute("objetivos", objetivos.getContent());
        model.addAttribute("objetivosTotal", objetivos.getTotalElements());

        var bemEstar = bemEstarService.listar(PageRequest.of(0, 20));
        model.addAttribute("bemEstar", bemEstar.getContent());
        model.addAttribute("bemEstarTotal", bemEstar.getTotalElements());

        addFormDefaults(model);
    }

    private void addFormDefaults(Model model) {
        if (!model.containsAttribute("usuarioForm")) {
            model.addAttribute("usuarioForm", new UsuarioRequestDTO());
        }
        if (!model.containsAttribute("cursoForm")) {
            model.addAttribute("cursoForm", new CursoRequestDTO());
        }
        if (!model.containsAttribute("curriculoForm")) {
            model.addAttribute("curriculoForm", new CurriculoRequestDTO());
        }
        if (!model.containsAttribute("trilhaForm")) {
            model.addAttribute("trilhaForm", new TrilhaProgressoRequestDTO());
        }
        if (!model.containsAttribute("objetivoForm")) {
            model.addAttribute("objetivoForm", new TrilhaObjetivoRequestDTO());
        }
        if (!model.containsAttribute("bemEstarForm")) {
            model.addAttribute("bemEstarForm", new BemEstarRequestDTO());
        }
    }
}

