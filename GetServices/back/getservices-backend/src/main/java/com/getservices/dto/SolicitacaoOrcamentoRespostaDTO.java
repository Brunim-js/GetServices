package com.getservices.dto;

import com.getservices.model.SolicitacaoOrcamento;
import com.getservices.model.StatusSolicitacao;

import java.time.LocalDateTime;

public class SolicitacaoOrcamentoRespostaDTO {
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private Long servicoId;
    private String servicoTitulo;
    private String descricao;
    private StatusSolicitacao status;
    private LocalDateTime criadoEm;

    public SolicitacaoOrcamentoRespostaDTO(SolicitacaoOrcamento solicitacao) {
        this.id = solicitacao.getId();
        this.clienteId = solicitacao.getCliente().getId();
        this.clienteNome = solicitacao.getCliente().getNome();
        this.servicoId = solicitacao.getServico().getId();
        this.servicoTitulo = solicitacao.getServico().getTitulo();
        this.descricao = solicitacao.getDescricao();
        this.status = solicitacao.getStatus();
        this.criadoEm = solicitacao.getCriadoEm();
    }

    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public String getClienteNome() { return clienteNome; }
    public Long getServicoId() { return servicoId; }
    public String getServicoTitulo() { return servicoTitulo; }
    public String getDescricao() { return descricao; }
    public StatusSolicitacao getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
