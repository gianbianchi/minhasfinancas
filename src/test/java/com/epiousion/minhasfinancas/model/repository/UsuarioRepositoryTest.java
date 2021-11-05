package com.epiousion.minhasfinancas.model.repository;

import com.epiousion.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){
        //cenário
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        entityManager.persist(usuario);
        //repository.save(usuario);

        //ação e execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveSerFalsoSeNaoHouverUsuarioCadastradoComEmail(){
        //ação e execução
        boolean result = repository.existsByEmail("usuario2@email.com");

        //verificação
       Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUsuarioBaseDeDados(){
        //cenário
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();

        //ação e execução
        Usuario usuarioSalvo = repository.save(usuario);

        //Verificação
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUsuarioPorEmail(){
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
        entityManager.persist(usuario);

        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioBuscarUsuarioPorEmail(){
        //cenario

        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isFalse();
    }
}
