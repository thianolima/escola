package br.com.escola.services;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.repositories.AlunoRepository;
import br.com.escola.services.impl.AlunoServiceImpl;
import br.com.escola.templates.AlunoEntityTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AlunoServiceTest {

    AlunoService alunoService;

    @MockBean
    AlunoRepository alunoRepository;


    @BeforeEach
    public void setup(){
        this.alunoService = new AlunoServiceImpl(alunoRepository);
    }

    @Test
    public void deveSalvar(){
        AlunoEntity alunoMock = AlunoEntityTemplate.getInstance().getObjectValid();

        when(alunoRepository.save(alunoMock)).thenReturn(alunoMock);

        AlunoEntity alunoSalvo = alunoService.salvar(alunoMock);

        assertThat(alunoSalvo.getId()).isEqualTo(alunoMock.getId());
        assertThat(alunoSalvo.getNome()).isEqualTo(alunoMock.getNome());
        assertThat(alunoSalvo.getEmail()).isEqualTo(alunoMock.getEmail());

        verify(alunoRepository,atLeastOnce()).save(alunoMock);
    }

    @Test
    public void deveListar(){
        List<AlunoEntity> alunosMock = Arrays.asList(
                AlunoEntityTemplate.getInstance().getObjectValid(),
                AlunoEntityTemplate.getInstance().getObjectValid(),
                AlunoEntityTemplate.getInstance().getObjectValid(),
                AlunoEntityTemplate.getInstance().getObjectValid()
        );

        when(alunoRepository.listarAlunosAtivos()).thenReturn(alunosMock);

        List<AlunoEntity> alunosRecuperados = alunoService.listar();

        assertThat(alunosRecuperados.size()).isEqualTo(alunosMock.size());
    }

    @Test
    public void devePesquisarPorId(){
        AlunoEntity alunoMock = AlunoEntityTemplate.getInstance().getObjectValid();

        when(alunoRepository.pesquisarAlunoAtivoPorId(anyLong())).thenReturn(Optional.of(alunoMock));

        AlunoEntity alunoRecuperado = alunoService.pesquisarPorId(1L);

        assertThat(alunoRecuperado.getId()).isEqualTo(alunoMock.getId());
        assertThat(alunoRecuperado.getNome()).isEqualTo(alunoMock.getNome());
        assertThat(alunoRecuperado.getEmail()).isEqualTo(alunoMock.getEmail());

        verify(alunoRepository,atLeastOnce()).pesquisarAlunoAtivoPorId(1L);
    }

    @Test
    public void deveExcluir(){
        AlunoEntity alunoMock = AlunoEntityTemplate.getInstance().getObjectValid();
        assertDoesNotThrow(() -> alunoService.excluir(alunoMock));
        verify(alunoRepository, atLeastOnce()).save(alunoMock);
    }
}
