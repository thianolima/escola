package br.com.escola.services;

import br.com.escola.entities.AlunoEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface AlunoService {

    @Transactional
    public AlunoEntity salvar(AlunoEntity aluno);

    public List<AlunoEntity> listar();

    public AlunoEntity pesquisarPorId(Long idAluno);

    @Transactional
    public void excluir(AlunoEntity aluno);
}
