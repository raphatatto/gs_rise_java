package br.com.fiap.gs_rise.controller;

import br.com.fiap.gs_rise.dto.usuario.UsuarioRequestDTO;
import br.com.fiap.gs_rise.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthViewController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("usuario", new UsuarioRequestDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("usuario") UsuarioRequestDTO usuario,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        usuarioService.criar(usuario);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Conta criada com sucesso! Faça login para continuar.");
        return "redirect:/login";
    }
}
