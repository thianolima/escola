package br.com.escola.controllers;

import br.com.escola.api.TurmaAPI;
import br.com.escola.dtos.TurmaDTO;
import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import br.com.escola.services.AlunoService;
import br.com.escola.services.TurmaService;
import br.com.escola.vos.TurmaVO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class TurmaController implements TurmaAPI {

    private TurmaService turmaService;
    private AlunoService alunoService;
    private ModelMapper modelMapper;

    public TurmaController(TurmaService turmaService, ModelMapper modelMapper, AlunoService alunoService){
        this.turmaService = turmaService;
        this.modelMapper = modelMapper;
        this.alunoService = alunoService;
    }

    @Override
    public ResponseEntity<TurmaDTO> inserir(TurmaVO turmaVO, UriComponentsBuilder uriBuilder) {
        TurmaEntity turmaEntity = turmaService.salvar(modelMapper.map(turmaVO, TurmaEntity.class));
        TurmaDTO dto = modelMapper.map(turmaEntity, TurmaDTO.class);
        URI uri = uriBuilder.path("/turmas/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    public ResponseEntity<List<TurmaDTO>> listar() {
        List<TurmaEntity> turmas = turmaService.listar();

        if(Objects.isNull(turmas) || turmas.size() == 0){
            return ResponseEntity.notFound().build();
        }

        List<TurmaDTO> dtos = turmas.stream()
                .map(turma -> modelMapper.map(turma, TurmaDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<HttpStatus> excluir(Long idTurma) {
        TurmaEntity turma = turmaService.pesquisarPorId(idTurma);
        turmaService.excluir(turma);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TurmaDTO> alterar(TurmaVO turmaVO, Long idTurma) {
        TurmaEntity turma = turmaService.pesquisarPorId(idTurma);
        turma.setNome(turmaVO.getNome());
        turmaService.salvar(turma);
        return ResponseEntity.ok(modelMapper.map(turma, TurmaDTO.class));
    }

    @Override
    public ResponseEntity<TurmaDTO> inserirAluno(Long idTurma, Long idAluno) {
        AlunoEntity aluno = alunoService.pesquisarPorId(idAluno);
        TurmaEntity turma = turmaService.pesquisarPorId(idTurma);
        turma.inserirAluno(aluno);
        turmaService.salvar(turma);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<HttpStatus> excluirAluno(Long idTurma, Long idAluno) {
        AlunoEntity aluno = alunoService.pesquisarPorId(idAluno);
        TurmaEntity turma = turmaService.pesquisarPorId(idTurma);
        //turma.excluirAluno(aluno);
        turmaService.excluirAlunoTurma(turma, aluno);
        return ResponseEntity.noContent().build();
    }

}
