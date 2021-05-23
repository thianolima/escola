package br.com.escola.vos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoVO {

    @ApiModelProperty(example = "Carlos Alberto", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "aluno@escola.com.br", required = true)
    @NotBlank
    @Email
    private String email;
}
