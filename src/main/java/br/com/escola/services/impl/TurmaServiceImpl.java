package br.com.escola.services.impl;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import br.com.escola.repositories.TurmaRepository;
import br.com.escola.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaServiceImpl implements TurmaService {

    @Autowired
    TurmaRepository turmaRepository;

    @Override
    public TurmaEntity salvar(TurmaEntity turma){
        return turmaRepository.save(turma);
    }

    @Override
    public List<TurmaEntity> listar() {
        return turmaRepository.listarTurmasAtivas();
    }

    @Override
    public void excluir(TurmaEntity turmaEntity) {
        turmaEntity.setAtivo(false);
        turmaRepository.save(turmaEntity);
    }

    @Override
    public TurmaEntity pesquisarPorId(Long idTurma) throws EmptyResultDataAccessException{
        return  turmaRepository.pesquisarTurmaAtivaPorId(idTurma)
                .orElseThrow(() -> new EmptyResultDataAccessException(0));
    }

    @Override
    public void excluirAlunoTurma(TurmaEntity turma, AlunoEntity aluno) {
        turmaRepository.excluirAluno(turma.getId(), aluno.getId());
    }
}
