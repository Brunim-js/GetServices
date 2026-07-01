package com.getservices.repository;

import com.getservices.model.TipoUsuario;
import com.getservices.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailAndTipoUsuario(String email, TipoUsuario tipoUsuario);
}
