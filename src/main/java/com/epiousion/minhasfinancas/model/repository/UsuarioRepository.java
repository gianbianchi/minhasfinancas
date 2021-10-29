package com.epiousion.minhasfinancas.model.repository;

import com.epiousion.minhasfinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    boolean existsByEmail(String email);
}
