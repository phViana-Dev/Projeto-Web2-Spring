package br.ufrn.imd.universitymanagement.model;

import br.ufrn.imd.universitymanagement.enuns.Genero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "professores")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cpf;
    private int matricula;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String departamento;
    private String dataNascimento;
    private double salario;
    private boolean ativo;

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<TurmaEntity> turmas;

    public ProfessorEntity(){
        this.ativo = true;
    }
}
