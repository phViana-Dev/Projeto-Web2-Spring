package br.ufrn.imd.universitymanagement.dto;

import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.ufrn.imd.universitymanagement.model.ProfessorEntity;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;

import java.util.Set;

public record TurmaRecordDTO(String nome,
                             Integer semestre,
                             ProfessorEntity professor,
                             String codigo,
                             Set<AlunoEntity> alunos) {

    public void putMetodo(TurmaEntity turmaEntity){
        if (this.nome() != null) {
            turmaEntity.setNome(this.nome());
        }
        if (this.semestre() != null) {
            turmaEntity.setSemestre(this.semestre());
        }
        if (this.codigo() != null){
            turmaEntity.setCodigo(this.codigo);
        }
        if (this.professor() != null) {
            turmaEntity.setProfessor(this.professor());
        }
        if (this.alunos() != null) {
            turmaEntity.setListaAlunos(this.alunos());
        }
    }
}
