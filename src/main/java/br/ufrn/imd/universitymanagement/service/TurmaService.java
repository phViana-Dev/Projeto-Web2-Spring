package br.ufrn.imd.universitymanagement.service;

import br.ufrn.imd.universitymanagement.dto.TurmaRecordDTO;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.model.ProfessorEntity;
import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import br.ufrn.imd.universitymanagement.repository.AlunoRepository;
import br.ufrn.imd.universitymanagement.repository.ProfessorRepository;
import br.ufrn.imd.universitymanagement.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TurmaService {

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    ProfessorRepository professorRepository;

    public TurmaEntity salvarTurma(TurmaRecordDTO turmaRecordDTO){
        if (turmaRecordDTO.nome() == null || turmaRecordDTO.codigo() == null){
            throw new RuntimeException("Passe o nome e o código da turma");
        }

        var turma = new TurmaEntity();
        BeanUtils.copyProperties(turmaRecordDTO, turma);

        if (turma.getProfessor() != null){
            var professor = professorRepository.findById(turma.getProfessor().getId());
            if (professor.isEmpty()){
                throw new RuntimeException("Esse professor não existe");
            }
            turma.setProfessor(professor.get());
        }

        if (turma.getListaAlunos() != null && !turma.getListaAlunos().isEmpty()){
            Set<AlunoEntity> alunosExists = new HashSet<>();
            for (AlunoEntity aluno : turma.getListaAlunos()){
                var alunoOpt = alunoRepository.findById(aluno.getId());
                if (alunoOpt.isEmpty()){
                    throw new RuntimeException("O aluno com ID igual a: " + aluno.getId() + " não existe");
                }
                alunosExists.add(alunoOpt.get());
            }
            turma.setListaAlunos(alunosExists);
        }

        return turmaRepository.save(turma);
    }

    public Optional<TurmaEntity> getById(Long id){
        return turmaRepository.findById(id);
    }

    public List<TurmaEntity> getAll(){
        return turmaRepository.findAll();
    }

    public List<TurmaEntity> getAllAtivo(){
        return turmaRepository.findByAtivoTrue();
    }

    public boolean associarProfessorToTurma(Long idProf, Long idTurma){
        var professor = professorRepository.findById(idProf);
        if (professor.isEmpty()){
            throw new RuntimeException("Professor not found");
        }

        var turma = turmaRepository.findById(idTurma);
        if (turma.isEmpty()){
            throw new RuntimeException("Turma not found");
        }

        turma.get().setProfessor(professor.get());
        turmaRepository.save(turma.get());
        return true;
    }

    public TurmaEntity putTurma(Long id, @Valid TurmaRecordDTO turmaRecordDTO){
        var turma = getById(id);
        if (turma.isEmpty()){
            return null;
        }
        turmaRecordDTO.putMetodo(turma.get());
        turmaRepository.save(turma.get());
        return turma.get();
    }

    public Long deletarTurma(Long id){
        Optional<TurmaEntity> turma = getById(id);
        if (turma.isEmpty()){
            return null;
        }
        Set<AlunoEntity> alunos = turma.get().getListaAlunos();

        for (AlunoEntity aluno : alunos){
            aluno.setTurmas(null);
            alunoRepository.save(aluno);
        }
        turma.get().setProfessor(null);
        turmaRepository.deleteById(id);

        return id;
    }

    public Long deletarLogico(Long id){
        Optional<TurmaEntity> turma = getById(id);
        if (turma.isEmpty()){
            return null;
        }
        turma.get().setAtivo(false);
        turmaRepository.save(turma.get());
        return id;
    }

    public void adicionarAluno(Long idTurma, Long idAluno) {
        Optional<TurmaEntity> turmaOpt = turmaRepository.findById(idTurma);
        Optional<AlunoEntity> alunoOpt = alunoRepository.findById(idAluno);

        if (turmaOpt.isPresent() && alunoOpt.isPresent()) {
            TurmaEntity turma = turmaOpt.get();
            AlunoEntity aluno = alunoOpt.get();

            turma.getListaAlunos().add(aluno);
            aluno.getTurmas().add(turma);

            turmaRepository.save(turma);
            alunoRepository.save(aluno);
        } else {
            throw new RuntimeException("Turma ou Aluno não encontrado");
        }
    }
}
