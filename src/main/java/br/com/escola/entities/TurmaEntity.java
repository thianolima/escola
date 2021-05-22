package br.com.escola.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@Table(name = "turma", uniqueConstraints = @UniqueConstraint(columnNames= {"nome"}, name="uk_turma_nome"))
@DynamicUpdate
public class TurmaEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "turma_aluno",
            joinColumns = @JoinColumn(name = "idturma", foreignKey = @ForeignKey(name = "fk_alunoturma_turma")),
            inverseJoinColumns = @JoinColumn(name = "idaluno"), foreignKey = @ForeignKey(name = "fk_alunoturma_aluno"))
    private List<AlunoEntity> alunos = new ArrayList<AlunoEntity>();

    public void inserirAluno(AlunoEntity aluno){
        alunos.add(aluno);
    }

    public void excluirAluno(AlunoEntity aluno){
        int idx = this.alunos.lastIndexOf(aluno);
        this.alunos.remove(idx);
    }
}
