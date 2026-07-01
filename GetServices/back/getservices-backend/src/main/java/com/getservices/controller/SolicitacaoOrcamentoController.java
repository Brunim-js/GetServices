package com.getservices.controller;

import com.getservices.dto.SolicitacaoOrcamentoDTO;
import com.getservices.dto.SolicitacaoOrcamentoRespostaDTO;
import com.getservices.model.SolicitacaoOrcamento;
import com.getservices.service.SolicitacaoOrcamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orcamentos")
public class SolicitacaoOrcamentoController {
    private final SolicitacaoOrcamentoService solicitacaoService;

    public SolicitacaoOrcamentoController(SolicitacaoOrcamentoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public ResponseEntity<SolicitacaoOrcamentoRespostaDTO> criar(@Valid @RequestBody SolicitacaoOrcamentoDTO dto) {
        SolicitacaoOrcamento solicitacao = solicitacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SolicitacaoOrcamentoRespostaDTO(solicitacao));
    }
}
