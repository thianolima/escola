package br.com.escola.controllers;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import br.com.escola.services.AlunoService;
import br.com.escola.services.TurmaService;
import br.com.escola.templates.AlunoEntityTemplate;
import br.com.escola.templates.TurmaEntityTemplate;
import br.com.escola.templates.TurmaVOTemplate;
import br.com.escola.vos.AlunoVO;
import br.com.escola.vos.TurmaVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TurmaController.class)
public class TurmaControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TurmaService turmaService;

    @MockBean
    AlunoService alunoService;

    @Test
    public void deveInserirUmaNovaTurma() throws Exception {
        TurmaVO vo = TurmaVOTemplate.getInstance().getObjectValid();
        TurmaEntity entity = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.salvar(any(TurmaEntity.class))).willReturn(entity);

        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").isNotEmpty());
    }

    @Test
    public void deveLancarExceptionAoInserirUmaNovaTurmaSemOsCamposObrigatorio() throws Exception{

        String json = new ObjectMapper().writeValueAsString(new AlunoVO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].mensagem").isNotEmpty());
    }

    @Test
    public void deveListarTodasTurmasAtiva() throws Exception {
        List<TurmaEntity> turmas = Arrays.asList(
                TurmaEntityTemplate.getInstance().getObjectValid(),
                TurmaEntityTemplate.getInstance().getObjectValid(),
                TurmaEntityTemplate.getInstance().getObjectValid()
        );

        BDDMockito.given(turmaService.listar())
                .willReturn(turmas);

        mvc.perform(get("/turmas"))
                .andDo(print())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].nome").isNotEmpty())
                .andExpect(jsonPath("*", hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void deveLancarExceptionAoListarTodasTurmasComBancoVazio() throws Exception {
        BDDMockito.given(turmaService.listar())
                .willReturn(null);

        mvc.perform(get("/turmas"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveExcluirUmaTurma() throws Exception {
        TurmaEntity entity = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong()))
                .willReturn(entity);

        BDDMockito.willDoNothing().given(turmaService)
                .excluir(entity);

        mvc.perform(delete("/turmas/{idTurma}",1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveLancarExceptionAoTentarExcluirUmaTurmaNaoEncontrada() throws Exception {
        TurmaEntity entity = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        mvc.perform(delete("/turmas/{idTurma}",1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveAlterarUmaTurma() throws Exception {
        TurmaEntity entity = TurmaEntityTemplate.getInstance().getObjectValid();
        TurmaVO vo = TurmaVOTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong()))
                .willReturn(entity);

        BDDMockito.given(turmaService.salvar(any(TurmaEntity.class)))
                .willReturn(entity);

        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/turmas/{idTurma}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("nome").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void deveLancarExceptionAoTentarAlterarUmaTurmaNaoEncontrada() throws Exception {
        TurmaVO vo = TurmaVOTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/turmas/{idTurma}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveInserirUmAlunoNaTurma() throws Exception {
        AlunoEntity aluno = AlunoEntityTemplate.getInstance().getObjectValid();
        TurmaEntity turma = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong()))
                .willReturn(turma);

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(aluno);

        BDDMockito.given(turmaService.salvar(turma))
                .willReturn(null);

        mvc.perform(post("/turmas/{idTurma}/aluno/{idAluno}",1L,1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deveLancarExceptionAoInserirUmAlunoInativoNaTurma() throws Exception {
        TurmaEntity turma = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        BDDMockito.given(turmaService.pesquisarPorId(anyLong()))
                .willReturn(turma);

        mvc.perform(post("/turmas/{idTurma}/aluno/{idAluno}",1L,1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveLancarExceptionAoInserirUmAlunoNumaTurmaInativo() throws Exception {
        AlunoEntity aluno = AlunoEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(aluno);

        mvc.perform(post("/turmas/{idTurma}/aluno/{idAluno}",1L,1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveExcluirUmAlunoDeUmaTurmaAtiva() throws Exception {
        AlunoEntity aluno = AlunoEntityTemplate.getInstance().getObjectValid();
        TurmaEntity turma = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong()))
                .willReturn(turma);

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(aluno);

        BDDMockito.willDoNothing().given(turmaService)
                .excluirAlunoTurma(turma, aluno);

        mvc.perform(delete("/turmas/{idTurma}/aluno/{idAluno}",1L,1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveLancarExceptionAoExcluirUmAlunoInativoDeUmaTurmaAtiva() throws Exception {
        AlunoEntity aluno = AlunoEntityTemplate.getInstance().getObjectValid();
        TurmaEntity turma = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(turmaService.pesquisarPorId(anyLong()))
                .willReturn(turma);

        BDDMockito.given(alunoService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        BDDMockito.willDoNothing().given(turmaService)
                .excluirAlunoTurma(turma, aluno);

        mvc.perform(delete("/turmas/{idTurma}/aluno/{idAluno}",1L,1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveLancarExceptionAoExcluirUmAlunoDeUmaTurmaInativa() throws Exception {
        AlunoEntity aluno = AlunoEntityTemplate.getInstance().getObjectValid();
        TurmaEntity turma = TurmaEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(aluno);

        BDDMockito.given(turmaService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        BDDMockito.willDoNothing().given(turmaService)
                .excluirAlunoTurma(turma, aluno);

        mvc.perform(delete("/turmas/{idTurma}/aluno/{idAluno}",1L,1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }
}
