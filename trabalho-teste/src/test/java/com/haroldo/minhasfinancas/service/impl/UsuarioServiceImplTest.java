package com.haroldo.minhasfinancas.service.impl;

import com.haroldo.minhasfinancas.exception.RegraNegocioException;
import com.haroldo.minhasfinancas.model.entity.Usuario;
import com.haroldo.minhasfinancas.model.repository.UsuarioRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UsuarioServiceImplTest {

    @Mock
     UsuarioRepository repository;

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Test
    void testDeveSalvarUsuario(){
        //given
        Usuario usuario =  Usuario.builder()
                .email("paulo@gmail.com")
                .senha("admin123")
                .nome("paulo")
                .build();

        //when

        Mockito.when(repository.existsByEmail(usuario.getEmail())).thenReturn(false);

        //then

        Mockito.when(repository.save(usuario)).thenReturn(usuario);
        Usuario result = this.usuarioService.salvarUsuario(usuario);

        //assert

        org.assertj.core.api.Assertions.assertThat(result.getEmail()).isEqualTo("paulo@gmail.com");
        org.assertj.core.api.Assertions.assertThat(result.getNome()).isEqualTo("paulo");
        org.assertj.core.api.Assertions.assertThat(result.getSenha()).isEqualTo("admin123");
    }

    @Test
    void testNaoSalvarUsuarioExistente(){

        //given

        Usuario usuario =  Usuario.builder()
                .email("paulo@gmail.com")
                .senha("admin123")
                .nome("paulo")
                .build();

        //when

        Mockito.when(repository.existsByEmail(usuario.getEmail())).thenReturn(false);

        //assert

        Assertions.assertThrows(RegraNegocioException.class, ()-> this.usuarioService.salvarUsuario(usuario));

    }

    @Test
    void testUsuarioExistente(){

        //given

        Usuario usuario =  Usuario.builder()
                .email("paulo@gmail.com")
                .senha("admin123")
                .nome("paulo")
                .build();

        //when

        Mockito.when(repository.existsByEmail(usuario.getEmail())).thenReturn(true);

        //assert

        Assertions.assertThrows(RegraNegocioException.class, ()-> this.usuarioService.salvarUsuario(usuario));

        //retorna erro por existir usuario
    }

    @Test
    void testVerificaSenha(){

        //given
        Usuario usuario =  Usuario.builder()
                .email("paulo@gmail.com")
                .senha("admin123")
                .nome("paulo")
                .build();

        //when

        Mockito.when(repository.findByEmail("paulo@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario result = this.usuarioService.autenticar("paulo@gmail.com","admin123");

        //assert

        org.assertj.core.api.Assertions.assertThat(result.getSenha()).isEqualTo("admin123");
    }

    @Test
    void testDeveValidarEmail(){

        //given
        Usuario usuario =  Usuario.builder()
                .email("paulo@gmail.com")
                .senha("admin123")
                .nome("paulo")
                .build();

        //when

        Mockito.when(repository.findByEmail("paulo@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario result = this.usuarioService.autenticar("paulo@gmail.com","admin123");

      //assert

        org.assertj.core.api.Assertions.assertThat(result.getEmail()).isEqualTo("paulo@gmail.com");
    }
}