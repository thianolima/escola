package br.com.escola.controllers;

import br.com.escola.services.TurmaService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TurmaController.class)
public class TurmControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TurmaService turmaService;
}
