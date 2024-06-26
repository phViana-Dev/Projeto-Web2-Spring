package br.ufrn.imd.universitymanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Entity
@AllArgsConstructor
@Table(name = "turmas")
public class TurmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private Integer semestre;
    private String codigo;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = true)
    private ProfessorEntity professor;

    @ManyToMany
    @JoinTable(name = "turmas_alunos",
            joinColumns = @JoinColumn(name = "turma_fk"),
            inverseJoinColumns = @JoinColumn(name = "aluno_fk"))
    private Set<AlunoEntity> listaAlunos;

    public TurmaEntity() {
        this.ativo = true;
    }

    public void removeAluno(AlunoEntity aluno) {
        this.listaAlunos.remove(aluno);
        aluno.getTurmas().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity professor) {
        this.professor = professor;
    }

    public Set<AlunoEntity> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(Set<AlunoEntity> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }
}
