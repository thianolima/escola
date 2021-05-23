package br.com.escola.services;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import br.com.escola.repositories.TurmaRepository;
import br.com.escola.services.impl.TurmaServiceImpl;
import br.com.escola.templates.AlunoEntityTemplate;
import br.com.escola.templates.TurmaEntityTemplate;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TurmaServiceTest {

    TurmaService turmaService;

    @MockBean
    TurmaRepository turmaRepository;

    @BeforeEach
    public void setup(){
        turmaService = new TurmaServiceImpl(turmaRepository);
    }

    @Test
    public void deveSalvar(){
        TurmaEntity turmaMock = TurmaEntityTemplate.getInstance().getObjectValid();

        when(turmaRepository.save(turmaMock)).thenReturn(turmaMock);

        TurmaEntity turmaSalva = turmaService.salvar(turmaMock);

        assertThat(turmaSalva.getId()).isEqualTo(turmaMock.getId());
        assertThat(turmaSalva.getNome()).isEqualTo(turmaMock.getNome());

        verify(turmaRepository,atLeastOnce()).save(turmaMock);
    }

    @Test
    public void deveListar(){
        List<TurmaEntity> turmaMock = Arrays.asList(
                TurmaEntityTemplate.getInstance().getObjectValid(),
                TurmaEntityTemplate.getInstance().getObjectValid(),
                TurmaEntityTemplate.getInstance().getObjectValid()
        );

        when(turmaRepository.listarTurmasAtivas()).thenReturn(turmaMock);

        List<TurmaEntity> alunosRecuperados = turmaService.listar();

        assertThat(alunosRecuperados.size()).isEqualTo(turmaMock.size());
    }

    @Test
    public void devePesquisarPorId(){
        TurmaEntity turmaMock = TurmaEntityTemplate.getInstance().getObjectValid();

        when(turmaRepository.pesquisarTurmaAtivaPorId(anyLong())).thenReturn(Optional.of(turmaMock));

        TurmaEntity turmaRecuperada = turmaService.pesquisarPorId(1L);

        assertThat(turmaRecuperada.getId()).isEqualTo(turmaMock.getId());
        assertThat(turmaRecuperada.getNome()).isEqualTo(turmaMock.getNome());

        verify(turmaRepository,atLeastOnce()).pesquisarTurmaAtivaPorId(1L);
    }

    @Test
    public void deveExcluir(){
        TurmaEntity turmaMock = TurmaEntityTemplate.getInstance().getObjectValid();
        assertDoesNotThrow(() -> turmaService.excluir(turmaMock));
        verify(turmaRepository, atLeastOnce()).save(turmaMock);
    }

    @Test
    public void deveExcluirAlunoTurma(){
        TurmaEntity turmaMock = TurmaEntityTemplate.getInstance().getObjectValid();
        AlunoEntity alunoMOck = AlunoEntityTemplate.getInstance().getObjectValid();

        doNothing().when(turmaRepository).excluirAluno(turmaMock.getId(), alunoMOck.getId());

        turmaService.excluirAlunoTurma(turmaMock, alunoMOck);

        verify(turmaRepository, atLeastOnce()).excluirAluno(turmaMock.getId(), alunoMOck.getId());
    }
}
