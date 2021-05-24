package br.com.escola.controllers;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.services.AlunoService;
import br.com.escola.templates.AlunoEntityTemplate;
import br.com.escola.templates.AlunoVOTemplate;
import br.com.escola.vos.AlunoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AlunoService alunoService;

    @Test
    public void deveInserirUmNovoAluno() throws Exception {
        AlunoVO vo = AlunoVOTemplate.getInstance().getObjectValid();
        AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();

        String json = new ObjectMapper().writeValueAsString(vo);

        BDDMockito.given(alunoService.salvar(any(AlunoEntity.class)))
                .willReturn(entity);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").isNotEmpty())
                .andExpect(jsonPath("email").isNotEmpty());
    }

    @Test
    public void deveLancarExceptionAoInserirUmNovoAlunoSemOsCamposObrigatorio() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new AlunoVO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].mensagem").isNotEmpty());
    }

    @Test
    public void deveListarTodosAlunosAtivo() throws Exception {
        List<AlunoEntity> alunos = Arrays.asList(
                AlunoEntityTemplate.getInstance().getObjectValid(),
                AlunoEntityTemplate.getInstance().getObjectValid(),
                AlunoEntityTemplate.getInstance().getObjectValid()
        );

        BDDMockito.given(alunoService.listar())
                .willReturn(alunos);

        mvc.perform(get("/alunos"))
                .andDo(print())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].nome").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("*", hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void deveLancarExceptionAoListarTodosAlunosComBancoVazio() throws Exception {
        BDDMockito.given(alunoService.listar())
                .willReturn(null);

        mvc.perform(get("/alunos"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveExcluirUmAluno() throws Exception {
        AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(entity);

        BDDMockito.willDoNothing().given(alunoService)
                .excluir(entity);

        mvc.perform(delete("/alunos/{idAluno}",1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveLancarExceptionAoTentarExcluirUmAlunoNaoEncontrado() throws Exception {
        AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        mvc.perform(delete("/alunos/{idAluno}",1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveAlterarUmAluno() throws Exception {
        AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();
        AlunoVO vo = AlunoVOTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(entity);

        BDDMockito.given(alunoService.salvar(any(AlunoEntity.class)))
                .willReturn(entity);

        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/alunos/{idAluno}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("nome").isNotEmpty())
                .andExpect(jsonPath("email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void deveLancarExceptionAoTentarAlterarUmAlunoNaoEncontrado() throws Exception {
        AlunoVO vo = AlunoVOTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/alunos/{idAluno}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveListarTumasDoluno() throws Exception {
       AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();

        BDDMockito.given(alunoService.pesquisarPorId(anyLong()))
                .willReturn(entity);

        mvc.perform(get("/alunos/{idAluno}/turmas",1L))
                .andDo(print())
                .andExpect(jsonPath("*", hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void deveLancarExceptionAoTentarListarTurmasDeUmAlunoNaoEncontrado() throws Exception {
        BDDMockito.given(alunoService.pesquisarPorId(anyLong())).
                willThrow(new EmptyResultDataAccessException(1));

        mvc.perform(get("/alunos/{idAluno}/turmas",1L))
                .andDo(print())
                .andExpect(jsonPath("mensagem").isNotEmpty())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveLancarExceptionAoTentarListarTurmasDeUmAlunoSemTurmas() throws Exception {
        AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();
        entity.setTurmas(null);

        BDDMockito.given(alunoService.pesquisarPorId(anyLong())).
                willReturn(entity);

        mvc.perform(get("/alunos/{idAluno}/turmas",1L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
