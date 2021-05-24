package br.com.escola.templates;

import br.com.escola.entities.AlunoEntity;
import br.com.escola.entities.TurmaEntity;
import lombok.Getter;

import java.util.ArrayList;

public class TurmaEntityTemplate extends BaseTemplate {

    @Getter
    private static final TurmaEntityTemplate instance = new TurmaEntityTemplate();

    public TurmaEntity getObjectValid(){
        return TurmaEntity.builder()
                .id(faker.number().randomNumber())
                .nome(faker.educator().course())
                .alunos(new ArrayList<AlunoEntity>())
                .build();
    }
}
