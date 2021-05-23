package br.com.escola.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@Table(name = "aluno", uniqueConstraints = @UniqueConstraint(columnNames= {"email"}, name="uk_aluno_email"))
@DynamicUpdate
public class AlunoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "turma_aluno",
            joinColumns = @JoinColumn(name = "idaluno", foreignKey = @ForeignKey(name = "fk_alunoturma_aluno")),
            inverseJoinColumns = @JoinColumn(name = "idturma"), foreignKey = @ForeignKey(name = "fk_alunoturma_turma"))
    private List<TurmaEntity> turmas = new ArrayList<TurmaEntity>();
}
