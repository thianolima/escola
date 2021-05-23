insert into turma (id, ativo, data_criacao, nome) values (1, true, CURRENT_TIMESTAMP, 'Conhecendo Spring de A a Z');
insert into turma (id, ativo, data_criacao, nome) values (2, true, CURRENT_TIMESTAMP, 'Conhecendo JPA de A a Z');
insert into turma (id, ativo, data_criacao, nome) values (3, false, CURRENT_TIMESTAMP, 'Conhecendo Java de A a Z');

insert into aluno (id, ativo, data_criacao, nome, email) values (1, true, CURRENT_TIMESTAMP, 'Pedro', 'pedro@email.com.br');
insert into aluno (id, ativo, data_criacao, nome, email) values (2, true, CURRENT_TIMESTAMP, 'Juliana', 'juliana@email.com.br');
insert into aluno (id, ativo, data_criacao, nome, email) values (3, true, CURRENT_TIMESTAMP, 'Carlos', 'carlos@email.com.br');

insert into turma_aluno(idaluno, idturma) values (1, 1);
insert into turma_aluno(idaluno, idturma) values (1, 2);
insert into turma_aluno(idaluno, idturma) values (1, 3);

insert into turma_aluno(idaluno, idturma) values (2, 1);

insert into turma_aluno(idaluno, idturma) values (3, 2);

