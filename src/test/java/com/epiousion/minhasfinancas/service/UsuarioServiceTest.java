package com.epiousion.minhasfinancas.service;

import com.epiousion.minhasfinancas.exception.ErroAutenticacao;
import com.epiousion.minhasfinancas.exception.RegraNegocioException;
import com.epiousion.minhasfinancas.model.entity.Usuario;
import com.epiousion.minhasfinancas.model.repository.UsuarioRepository;
import com.epiousion.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveAutenticarUsuario(){
        //cenario
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //acao
        Usuario result = service.autenticar(email, senha);

        //verificacao
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void deveSalvarUsuario(){
        //cenario
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder().id(1L).email("email@email.com").senha("senha").nome("nome").build();
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //açao
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        //verificaçao
        Assertions.assertThat(usuarioSalvo).isNotNull();
    }

    @Test
    public void naoDeveSalvarUsuarioComEmailCadastrado(){
        //cenario
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        try{
            service.salvarUsuario(usuario);
            System.out.println("Deu bom");
        } catch (Exception e) {
            System.out.println("Erro ao salvar");
        }
    }

    @Test
    public void deveLancarErroQuandoNaoHouverUsuarioCadastradoComEmailInformado(){
        //cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //açao
        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senha"));

        //verificacao
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email não informado.");
    }

    @Test
    public void deveLancarErroQuandoSenhaIncorreta(){
        //cenario
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senhaErrada") );
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
    }

    @Test
    public void deveValidarEmail(){
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        //execução
        service.validarEmail("usuario@email.com");
    }

    @Test
    public void deveRetornarErroQuandoExistirEmailCadastrado(){
        //cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //execução
        try {
            service.validarEmail("usuario@email.com");
            System.out.println("Não existia um email cadastrado");
        } catch(RegraNegocioException exception) {
            System.out.println("Já existe um e-mail cadastrado");
        }
    }
}
