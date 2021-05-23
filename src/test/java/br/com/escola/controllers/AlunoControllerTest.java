package br.com.escola.controllers;

import br.com.escola.dtos.AlunoDTO;
import br.com.escola.entities.AlunoEntity;
import br.com.escola.services.AlunoService;
import br.com.escola.templates.AlunoDTOTemplate;
import br.com.escola.templates.AlunoEntityTemplate;
import br.com.escola.templates.AlunoVOTemplate;
import br.com.escola.vos.AlunoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        /*
        AlunoVO vo = AlunoVOTemplate.getInstance().getObjectValid();
        AlunoDTO dto = AlunoDTOTemplate.getInstance().getObjectValid();
        AlunoEntity entity = AlunoEntityTemplate.getInstance().getObjectValid();

        String json = new ObjectMapper().writeValueAsString(vo);

        when(alunoService.salvar(any(AlunoEntity.class))).thenReturn(entity);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("nome").value(vo.getNome()));
         */
    }

    @Test
    public void deveListarTodosAlunos() throws Exception {

    }

    @Test
    public void deveExcluirUmAluno() throws Exception {

    }

    @Test
    public void deveAlterarUmAluno() throws Exception {

    }

    @Test
    public void deveListarTumasDoluno() throws Exception {

    }
}
