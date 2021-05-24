package br.com.escola.services;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.transaction.Transactional;
import java.util.List;

public interface TurmaService {

    @Transactional
    public TurmaEntity salvar(TurmaEntity turma);

    public List<TurmaEntity> listar();

    @Transactional
    public void excluir(TurmaEntity turma);

    public TurmaEntity pesquisarPorId(Long idTurma) throws EmptyResultDataAccessException;

    @Transactional
    public void excluirAlunoTurma(TurmaEntity turma, AlunoEntity aluno);
}
