package br.com.escola.templates;

import br.com.escola.vos.AlunoVO;
import lombok.Getter;

public class AlunoVOTemplate extends BaseTemplate{

    @Getter
    private static final AlunoVOTemplate instance = new AlunoVOTemplate();

    public AlunoVO getObjectValid(){
        return AlunoVO.builder()
                .nome(faker.superhero().name())
                .email(faker.bothify("????##@gmail.com"))
                .build();
    }
}
