package com.getservices.service;

import com.getservices.dto.CadastroUsuarioDTO;
import com.getservices.dto.LoginDTO;
import com.getservices.exception.NegocioException;
import com.getservices.model.TipoUsuario;
import com.getservices.model.Usuario;
import com.getservices.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(CadastroUsuarioDTO dto) {
        String email = normalizarEmail(dto.getEmail());

        if (usuarioRepository.existsByEmail(email)) {
            throw new NegocioException("Já existe uma conta cadastrada com esse email.");
        }

        TipoUsuario tipoUsuario = converterTipo(dto.getTipoUsuario());

        if (tipoUsuario == TipoUsuario.PROFISSIONAL && campoVazio(dto.getAreaAtuacao())) {
            throw new NegocioException("Área de atuação é obrigatória para profissionais.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome().trim());
        usuario.setEmail(email);
        usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setAreaAtuacao(tipoUsuario == TipoUsuario.PROFISSIONAL ? dto.getAreaAtuacao().trim() : null);

        return usuarioRepository.save(usuario);
    }

    public Usuario login(LoginDTO dto) {
        TipoUsuario tipoUsuario = converterTipo(dto.getTipoUsuario());
        String email = normalizarEmail(dto.getEmail());

        Usuario usuario = usuarioRepository.findByEmailAndTipoUsuario(email, tipoUsuario)
                .orElseThrow(() -> new NegocioException("Email, senha ou tipo de usuário inválido."));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenhaHash())) {
            throw new NegocioException("Email, senha ou tipo de usuário inválido.");
        }

        return usuario;
    }

    private TipoUsuario converterTipo(String tipo) {
        try {
            return TipoUsuario.valueOf(tipo.trim().toUpperCase());
        } catch (Exception e) {
            throw new NegocioException("Tipo de usuário deve ser CLIENTE ou PROFISSIONAL.");
        }
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }

    private boolean campoVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
