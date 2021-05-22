package br.com.escola.services;

import br.com.escola.entities.AlunoEntity;

import java.util.List;

public interface AlunoService {

    public AlunoEntity salvar(AlunoEntity aluno);
    public List<AlunoEntity> listar();
    public AlunoEntity pesquisarPorId(Long idAluno);
    public void excluir(AlunoEntity aluno);
}
