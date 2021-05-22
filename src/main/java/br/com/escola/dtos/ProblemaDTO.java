package br.com.escola.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemaDTO {

	private Integer status_code;
	private String menssagem;
}
