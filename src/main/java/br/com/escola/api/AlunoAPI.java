package br.com.escola.api;

import br.com.escola.dtos.TurmaDTO;
import br.com.escola.vos.AlunoVO;
import br.com.escola.dtos.AlunoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Alunos", description = "Gerencia a ficha cadastral do aluno.")
@RequestMapping("/alunos")
public interface AlunoAPI {

    @PostMapping
    @ApiOperation("Inserir")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Aluno cadastrado.")
    })
    public ResponseEntity<AlunoDTO> inserir(@Valid @RequestBody AlunoVO vo, UriComponentsBuilder uriBuilder);

    @GetMapping
    @ApiOperation("Listar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno encontrado.")
    })
    public ResponseEntity<List<AlunoDTO>> listar();

    @GetMapping("{idAluno}/turmas")
    @ApiOperation("Listar Tumas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno encontrado."),
    })
    public ResponseEntity<List<TurmaDTO>> listarTurmas(@PathVariable Long idAluno);

    @DeleteMapping("{idAluno}")
    @ApiOperation("Excluir")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno excluído.")
    })
    public ResponseEntity<HttpStatus> excluir(@PathVariable Long idAluno);

    @PutMapping("{idAluno}")
    @ApiOperation("Alterar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno alterado.")
    })
    public ResponseEntity<AlunoDTO> alterar(@Valid @RequestBody AlunoVO vo, @PathVariable Long idAluno);
}
