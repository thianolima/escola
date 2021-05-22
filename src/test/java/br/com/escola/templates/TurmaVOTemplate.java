package br.com.escola.templates;

import br.com.escola.vos.AlunoVO;
import br.com.escola.vos.TurmaVO;
import lombok.Getter;

public class TurmaVOTemplate extends BaseTemplate{

    @Getter
    private static final TurmaVOTemplate instance = new TurmaVOTemplate();

    public TurmaVO getObjectValid(){
        return TurmaVO.builder()
                .nome(faker.educator().course())
                .build();
    }
}
