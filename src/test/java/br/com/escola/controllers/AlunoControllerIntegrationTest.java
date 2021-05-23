package br.com.escola.controllers;

import br.com.escola.templates.AlunoVOTemplate;
import br.com.escola.vos.AlunoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AlunoControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void deveInserirUmNovoAluno() throws Exception {
        AlunoVO vo = AlunoVOTemplate.getInstance().getObjectValid();
        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").value(vo.getNome()));
    }

    @Test
    public void deveListarTodosAlunos() throws Exception {
        mvc.perform(get("/alunos"))
                .andDo(print())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].nome").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void deveExcluirUmAluno() throws Exception {
        mvc.perform(delete("/alunos/{idAluno}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveAlterarUmAluno() throws Exception {
        AlunoVO vo = AlunoVOTemplate.getInstance().getObjectValid();
        vo.setEmail("novo@email.com");

        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/alunos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").isNotEmpty())
                .andExpect(jsonPath("email").value(vo.getEmail()));
    }

    @Test
    public void deveListarTumasDoluno() throws Exception {
        mvc.perform(get("/alunos/{idAluno}/turmas",1L))
                .andDo(print())
                .andExpect(jsonPath("$[0].nome").value("Conhecendo Spring de A a Z"))
                .andExpect(jsonPath("*", hasSize(2)))
                .andExpect(status().isOk());
    }
}
