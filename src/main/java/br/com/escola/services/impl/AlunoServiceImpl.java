package br.com.escola.services.impl;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.repositories.AlunoRepository;
import br.com.escola.services.AlunoService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlunoServiceImpl implements AlunoService {

    AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }

    @Override
    public AlunoEntity salvar(AlunoEntity aluno) {
        return alunoRepository.save(aluno);
    }

    @Override
    public List<AlunoEntity> listar() {
        return alunoRepository.listarAlunosAtivos();
    }

    @Override
    public AlunoEntity pesquisarPorId(Long idAluno) {
        return alunoRepository.pesquisarAlunoAtivoPorId(idAluno)
                .orElseThrow(() -> new EmptyResultDataAccessException(0));
    }

    @Override
    public void excluir(AlunoEntity aluno) {
        aluno.setAtivo(false);
        alunoRepository.save(aluno);
    }
}
