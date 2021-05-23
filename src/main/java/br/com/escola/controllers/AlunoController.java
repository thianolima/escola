package br.com.escola.controllers;

import br.com.escola.api.AlunoAPI;
import br.com.escola.dtos.AlunoDTO;
import br.com.escola.dtos.TurmaDTO;
import br.com.escola.entities.AlunoEntity;
import br.com.escola.services.AlunoService;
import br.com.escola.vos.AlunoVO;
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
public class AlunoController implements AlunoAPI {

    private AlunoService alunoService;
    private ModelMapper modelMapper;

    public AlunoController(AlunoService alunoService, ModelMapper modelMapper){
        this.alunoService = alunoService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<AlunoDTO> inserir(AlunoVO alunoVO, UriComponentsBuilder uriBuilder) {
        AlunoEntity alunoEntity = alunoService.salvar(modelMapper.map(alunoVO, AlunoEntity.class));
        AlunoDTO dto = modelMapper.map(alunoEntity, AlunoDTO.class);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    public ResponseEntity<List<AlunoDTO>> listar() {
        List<AlunoEntity> alunos = alunoService.listar();

        if(Objects.isNull(alunos) || alunos.size() == 0){
            return ResponseEntity.notFound().build();
        }

        List<AlunoDTO> dtos = alunos.stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<HttpStatus> excluir(Long idAluno) {
        AlunoEntity aluno = alunoService.pesquisarPorId(idAluno);
        alunoService.excluir(aluno);
        return ResponseEntity.noContent().build();    }

    @Override
    public ResponseEntity<AlunoDTO> alterar(AlunoVO alunoVO, Long idAluno) {
        AlunoEntity aluno = alunoService.pesquisarPorId(idAluno);
        aluno.setNome(alunoVO.getNome());
        aluno.setEmail(alunoVO.getEmail());

        alunoService.salvar(aluno);
        return ResponseEntity.ok(modelMapper.map(aluno, AlunoDTO.class));
    }

    @Override
    public ResponseEntity<List<TurmaDTO>> listarTurmas(Long idAluno) {
        AlunoEntity aluno = alunoService.pesquisarPorId(idAluno);

        if(Objects.isNull(aluno.getTurmas()) || aluno.getTurmas().size() == 0){
            return ResponseEntity.notFound().build();
        }

        List<TurmaDTO> dtos = aluno.getTurmas().stream()
                .filter(turma -> turma.getAtivo())
                .map(turma -> modelMapper.map(turma, TurmaDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
