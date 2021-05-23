package br.com.escola.templates;

import br.com.escola.dtos.AlunoDTO;
import br.com.escola.entities.AlunoEntity;
import lombok.Getter;

import java.util.Arrays;

public class AlunoDTOTemplate extends BaseTemplate{

    @Getter
    private static final AlunoDTOTemplate instance = new AlunoDTOTemplate();

    public AlunoDTO getObjectValid(){
        return AlunoDTO.builder()
                .id(faker.number().randomNumber())
                .nome(faker.superhero().name())
                .email(faker.bothify("????##@gmail.com"))
                .build();
    }
}
