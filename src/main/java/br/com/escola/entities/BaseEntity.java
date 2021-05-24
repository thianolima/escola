package br.com.escola.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @CreationTimestamp
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    private Boolean ativo = Boolean.TRUE;
}
