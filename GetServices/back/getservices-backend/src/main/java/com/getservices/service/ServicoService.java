package com.getservices.service;

import com.getservices.dto.ServicoDTO;
import com.getservices.exception.NegocioException;
import com.getservices.model.Categoria;
import com.getservices.model.Servico;
import com.getservices.model.TipoUsuario;
import com.getservices.model.Usuario;
import com.getservices.repository.CategoriaRepository;
import com.getservices.repository.ServicoRepository;
import com.getservices.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ServicoService(ServicoRepository servicoRepository,
                          CategoriaRepository categoriaRepository,
                          UsuarioRepository usuarioRepository) {
        this.servicoRepository = servicoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Servico criar(ServicoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NegocioException("Categoria não encontrada."));

        Usuario profissional = usuarioRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new NegocioException("Profissional não encontrado."));

        if (profissional.getTipoUsuario() != TipoUsuario.PROFISSIONAL) {
            throw new NegocioException("Apenas usuários do tipo PROFISSIONAL podem cadastrar serviços.");
        }

        Servico servico = new Servico();
        servico.setTitulo(dto.getTitulo().trim());
        servico.setDescricao(dto.getDescricao() == null ? null : dto.getDescricao().trim());
        servico.setCategoria(categoria);
        servico.setProfissional(profissional);

        return servicoRepository.save(servico);
    }
}
