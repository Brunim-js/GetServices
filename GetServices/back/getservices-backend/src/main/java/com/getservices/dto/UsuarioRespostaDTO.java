package com.getservices.dto;

import com.getservices.model.TipoUsuario;
import com.getservices.model.Usuario;

public class UsuarioRespostaDTO {
    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;
    private String areaAtuacao;

    public UsuarioRespostaDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.areaAtuacao = usuario.getAreaAtuacao();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public String getAreaAtuacao() { return areaAtuacao; }
}
