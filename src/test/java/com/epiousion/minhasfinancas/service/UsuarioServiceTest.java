package com.epiousion.minhasfinancas.service;

import com.epiousion.minhasfinancas.exception.RegraNegocioException;
import com.epiousion.minhasfinancas.model.entity.Usuario;
import com.epiousion.minhasfinancas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveValidarEmail(){
        //cenário
        repository.deleteAll();

        //execução
        service.validarEmail("usuario@email.com");
    }

    @Test
    public void deveRetornarErroQuandoExistirEmailCadastrado(){
        //cenário
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        repository.save(usuario);

        boolean result = false;
        //execução
        try {
            service.validarEmail("usuario@email.com");
            result = true;
        } catch(RegraNegocioException exception) {
            System.out.println("Já existe um e-mail cadastrado");
        }
    }
}
