package com.getservices.controller;

import com.getservices.dto.CadastroUsuarioDTO;
import com.getservices.dto.LoginDTO;
import com.getservices.dto.UsuarioRespostaDTO;
import com.getservices.model.Usuario;
import com.getservices.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioRespostaDTO> cadastrar(@Valid @RequestBody CadastroUsuarioDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioRespostaDTO(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRespostaDTO> login(@Valid @RequestBody LoginDTO dto) {
        Usuario usuario = usuarioService.login(dto);
        return ResponseEntity.ok(new UsuarioRespostaDTO(usuario));
    }
}
