package br.com.escola.vos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurmaVO {

    @ApiModelProperty(example = "Spring Boot de A a Z", required = true)
    @NotBlank
    private String nome;
}
