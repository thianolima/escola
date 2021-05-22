package br.com.escola.services;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface TurmaService {

    public TurmaEntity salvar(TurmaEntity turma);

    public List<TurmaEntity> listar();

    public void excluir(TurmaEntity turma);

    public TurmaEntity pesquisarPorId(Long idTurma) throws EmptyResultDataAccessException;

    public void excluirAlunoTurma(TurmaEntity turma, AlunoEntity aluno);
}
