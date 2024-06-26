package br.ufrn.imd.universitymanagement.dto;

import br.ufrn.imd.universitymanagement.enuns.Genero;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.Set;

public record AlunoRecordDTO(@NotBlank String name,
                             @CPF @NotBlank String cpf,
                             int matricula,
                             @NotNull Genero genero,
                             @NotBlank String curso,
                             @NotNull String dataNascimento) {

    public void putMetodo(AlunoEntity alunoEntity) {
        if (this.name() != null) {
            alunoEntity.setName(this.name());
        }
        if (this.cpf() != null) {
            alunoEntity.setCpf(this.cpf());
        }
        if (this.matricula() != 0) {
            alunoEntity.setMatricula(this.matricula());
        }
        if (this.genero() != null) {
            alunoEntity.setGenero(this.genero());
        }
        if (this.curso() != null) {
            alunoEntity.setCurso(this.curso());
        }
        if (this.dataNascimento() != null) {
            alunoEntity.setDataNascimento(this.dataNascimento());
        }
    }
}
