package com.getservices.service;

import com.getservices.dto.SolicitacaoOrcamentoDTO;
import com.getservices.exception.NegocioException;
import com.getservices.model.Servico;
import com.getservices.model.SolicitacaoOrcamento;
import com.getservices.model.TipoUsuario;
import com.getservices.model.Usuario;
import com.getservices.repository.ServicoRepository;
import com.getservices.repository.SolicitacaoOrcamentoRepository;
import com.getservices.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoOrcamentoService {
    private final SolicitacaoOrcamentoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;

    public SolicitacaoOrcamentoService(SolicitacaoOrcamentoRepository solicitacaoRepository,
                                       UsuarioRepository usuarioRepository,
                                       ServicoRepository servicoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicoRepository = servicoRepository;
    }

    public SolicitacaoOrcamento criar(SolicitacaoOrcamentoDTO dto) {
        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado."));

        if (cliente.getTipoUsuario() != TipoUsuario.CLIENTE) {
            throw new NegocioException("Apenas usuários do tipo CLIENTE podem solicitar orçamento.");
        }

        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new NegocioException("Serviço não encontrado."));

        SolicitacaoOrcamento solicitacao = new SolicitacaoOrcamento();
        solicitacao.setCliente(cliente);
        solicitacao.setServico(servico);
        solicitacao.setDescricao(dto.getDescricao());

        return solicitacaoRepository.save(solicitacao);
    }
}
