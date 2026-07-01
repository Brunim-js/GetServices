package com.getservices.controller;

import com.getservices.dto.ServicoDTO;
import com.getservices.dto.ServicoRespostaDTO;
import com.getservices.model.Servico;
import com.getservices.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public List<ServicoRespostaDTO> listar() {
        return servicoService.listarTodos().stream()
                .map(ServicoRespostaDTO::new)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ServicoRespostaDTO> criar(@Valid @RequestBody ServicoDTO dto) {
        Servico servico = servicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ServicoRespostaDTO(servico));
    }
}
