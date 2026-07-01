package com.getservices.dto;

import com.getservices.model.Servico;

public class ServicoRespostaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Long categoriaId;
    private String categoriaNome;
    private Long profissionalId;
    private String profissionalNome;

    public ServicoRespostaDTO(Servico servico) {
        this.id = servico.getId();
        this.titulo = servico.getTitulo();
        this.descricao = servico.getDescricao();
        this.categoriaId = servico.getCategoria().getId();
        this.categoriaNome = servico.getCategoria().getNome();
        this.profissionalId = servico.getProfissional().getId();
        this.profissionalNome = servico.getProfissional().getNome();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Long getCategoriaId() { return categoriaId; }
    public String getCategoriaNome() { return categoriaNome; }
    public Long getProfissionalId() { return profissionalId; }
    public String getProfissionalNome() { return profissionalNome; }
}
