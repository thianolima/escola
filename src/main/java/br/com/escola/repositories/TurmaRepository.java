package br.com.escola.repositories;

import br.com.escola.entities.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

    @Query("from TurmaEntity t where t.ativo = true")
    List<TurmaEntity> listarTurmasAtivas();

    @Query("from TurmaEntity t where t.ativo = true and t.id = :idTurma")
    Optional<TurmaEntity> pesquisarTurmaAtivaPorId(@Param("idTurma") Long idTurma);

    @Modifying
    @Query(value = "delete turma_aluno where idaluno = :idAluno and idturma = :idTurma", nativeQuery = true)
    void excluirAluno(@Param("idTurma") Long idTurma, @Param("idAluno") Long idAluno);
}
