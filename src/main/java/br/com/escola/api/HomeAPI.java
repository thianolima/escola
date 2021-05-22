package br.com.escola.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "Pagina Inicial")
@RequestMapping("/")
public interface HomeAPI {

    @ApiOperation(value = "Pagina inicial da API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Redireciona para o Swagger UI."),
            @ApiResponse(code = 500, message = "Um erro não esperado ocorreu.")
    })
    @GetMapping()
    ResponseEntity<Void> getHome();

}
