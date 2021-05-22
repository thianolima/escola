package br.com.escola.api;

import br.com.escola.dtos.TurmaDTO;
import br.com.escola.vos.TurmaVO;
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

@Api(tags = "Turmas", description = "Gerencia as turmas de alunos.")
@RequestMapping("/turmas")
public interface TurmaAPI {

    @PostMapping
    @ApiOperation("Inserir")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Turma cadastrado.")
    })
    public ResponseEntity<TurmaDTO> inserir(@Valid @RequestBody TurmaVO vo, UriComponentsBuilder uriBuilder);

    @GetMapping
    @ApiOperation("Listar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Turma encontrada.")
    })
    public ResponseEntity<List<TurmaDTO>> listar();

    @DeleteMapping("{idTurma}")
    @ApiOperation("Excluir")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Turma excluída.")
    })
    public ResponseEntity<HttpStatus> excluir(@PathVariable Long idTurma);

    @DeleteMapping("{idTurma}/aluno/{idAluno}")
    @ApiOperation("Excluir Aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aluno excluído da turma.")
    })
    public ResponseEntity<HttpStatus> excluirAluno(@PathVariable Long idTurma, @PathVariable Long idAluno);

    @PostMapping("{idTurma}/aluno/{idAluno}")
    @ApiOperation("Inserir Aluno")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Turma cadastrado.")
    })
    public ResponseEntity<TurmaDTO> inserirAluno(@PathVariable Long idTurma, @PathVariable Long idAluno);

    @PutMapping("{idTurma}")
    @ApiOperation("Alterar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Turma alterada.")
    })
    public ResponseEntity<TurmaDTO> alterar(@Valid @RequestBody TurmaVO vo, @PathVariable Long idTurma);
}
