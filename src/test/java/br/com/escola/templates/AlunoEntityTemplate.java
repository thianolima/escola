package br.com.escola.templates;

import br.com.escola.entities.AlunoEntity;
import lombok.Getter;

import java.util.Arrays;

public class AlunoEntityTemplate extends BaseTemplate{

    @Getter
    private static final AlunoEntityTemplate instance = new AlunoEntityTemplate();

    public AlunoEntity getObjectValid(){
        return AlunoEntity.builder()
                .id(faker.number().randomNumber())
                .nome(faker.superhero().name())
                .email(faker.bothify("????##@gmail.com"))
                .turmas(Arrays.asList(TurmaEntityTemplate.getInstance().getObjectValid()))
                .build();
    }
}
