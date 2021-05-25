package br.com.escola.integrations;

import br.com.escola.templates.TurmaVOTemplate;
import br.com.escola.vos.TurmaVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class TurmaControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void deveInserirUmaNovaTurma() throws Exception {
        TurmaVO vo = TurmaVOTemplate.getInstance().getObjectValid();
        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").value(vo.getNome()));
    }

    @Test
    public void deveListarTodasTurmas() throws Exception {
        mvc.perform(get("/turmas"))
                .andDo(print())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].nome").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void deveExcluirUmaTurma() throws Exception {
        mvc.perform(delete("/turmas/{idTurma}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveAlterarUmaTurma() throws Exception {
        TurmaVO vo = TurmaVOTemplate.getInstance().getObjectValid();
        vo.setNome("Nova Turma");
        String json = new ObjectMapper().writeValueAsString(vo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/turmas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").value(vo.getNome()));
    }

    @Test
    public void deveInserirUmNovoAlunoNaTurma() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/turmas/{idTurma}/aluno/{idAluno}",1L, 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void deveExcluirUmAlunoDaTurma() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/turmas/{idTurma}/aluno/{idAluno}",1L, 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }
}
