package com.epiousion.minhasfinancas.service.impl;

import com.epiousion.minhasfinancas.exception.RegraNegocioException;
import com.epiousion.minhasfinancas.model.entity.Usuario;
import com.epiousion.minhasfinancas.model.repository.UsuarioRepository;
import com.epiousion.minhasfinancas.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe){
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
        }
    }
}
