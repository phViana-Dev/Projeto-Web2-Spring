package br.ufrn.imd.universitymanagement.dto;

import br.ufrn.imd.universitymanagement.enuns.Genero;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.model.ProfessorEntity;
import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record ProfessorRecordDTO(@NotBlank String nome,
                                 @CPF String cpf,
                                 int matricula,
                                 @NotNull Genero genero,
                                 String departamento,
                                 @NotBlank String dataNascimento,
                                 double salario,
                                 boolean ativo) {

    public void putMetodo(ProfessorEntity professorEntity) {
        if (this.nome() != null) {
            professorEntity.setNome(this.nome());
        }
        if (this.cpf() != null) {
            professorEntity.setCpf(this.cpf());
        }
        if (this.matricula() != 0) {
            professorEntity.setMatricula(this.matricula());
        }
        if (this.genero() != null) {
            professorEntity.setGenero(this.genero());
        }
        if (this.departamento() != null) {
            professorEntity.setDepartamento(this.departamento());
        }
        if (this.salario() != 0) {
            professorEntity.setSalario(this.salario());
        }
        if (this.dataNascimento() != null) {
            professorEntity.setDataNascimento(this.dataNascimento());
        }
        professorEntity.setAtivo(this.ativo());
    }

}
