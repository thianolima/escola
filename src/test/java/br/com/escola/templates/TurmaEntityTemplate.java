package br.com.escola.templates;

import br.com.escola.entities.TurmaEntity;
import lombok.Getter;

public class TurmaEntityTemplate extends BaseTemplate {

    @Getter
    private static final TurmaEntityTemplate instance = new TurmaEntityTemplate();

    public TurmaEntity getObjectValid(){
        return TurmaEntity.builder()
                .id(faker.number().randomNumber())
                .nome(faker.educator().course())
                .build();
    }
}
