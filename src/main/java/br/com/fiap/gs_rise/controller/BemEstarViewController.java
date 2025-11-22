package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.bemestar.BemEstarRequestDTO;
import br.com.fiap.gs_rise.dto.bemestar.BemEstarResponseDTO;
import br.com.fiap.gs_rise.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.gs_rise.service.BemEstarService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app/bemestar")
@RequiredArgsConstructor
public class BemEstarViewController {

    private final BemEstarService bemEstarService;
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
        List<BemEstarResponseDTO> registros = bemEstarService
                .listar(PageRequest.of(0, 50, Sort.by("dataRegistro").descending()))
                .getContent();

        model.addAttribute("registros", registros);
        return "bemestar/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        BemEstarRequestDTO form = new BemEstarRequestDTO();
        form.setDataRegistro(LocalDateTime.now());
        model.addAttribute("registro", form);
        model.addAttribute("tituloPagina", "Registrar bem-estar");
        return "bemestar/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("registro") BemEstarRequestDTO registro,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tituloPagina", "Registrar bem-estar");
            return "bemestar/form";
        }

        bemEstarService.criar(registro);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Registro salvo com sucesso!");
        return "redirect:/app/bemestar";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        BemEstarResponseDTO registro = bemEstarService.buscarPorId(id);
        BemEstarRequestDTO form = new BemEstarRequestDTO();
        form.setDataRegistro(registro.getDataRegistro());
        form.setNivelHumor(registro.getNivelHumor());
        form.setHorasEstudo(registro.getHorasEstudo());
        form.setDescricaoAtividade(registro.getDescricaoAtividade());
        form.setUsuarioId(registro.getUsuarioId());

        model.addAttribute("registro", form);
        model.addAttribute("registroId", id);
        model.addAttribute("tituloPagina", "Editar registro de bem-estar");
        return "bemestar/form";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("registro") BemEstarRequestDTO registro,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registroId", id);
            model.addAttribute("tituloPagina", "Editar registro de bem-estar");
            return "bemestar/form";
        }

        bemEstarService.atualizar(id, registro);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Registro atualizado com sucesso!");
        return "redirect:/app/bemestar";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        bemEstarService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Registro removido!");
        return "redirect:/app/bemestar";
    }
}
