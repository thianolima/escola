package br.com.escola.repositories;

import br.com.escola.entities.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {

    @Query("from AlunoEntity a where a.ativo = true and a.id = :idAluno")
    Optional<AlunoEntity> pesquisarAlunoAtivoPorId(@Param("idAluno") Long idAluno);

    @Query("from AlunoEntity a where a.ativo = true")
    List<AlunoEntity> listarAlunosAtivos();
}
