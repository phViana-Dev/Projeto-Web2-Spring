package br.ufrn.imd.universitymanagement.service;

import br.ufrn.imd.universitymanagement.dto.AlunoRecordDTO;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import br.ufrn.imd.universitymanagement.repository.AlunoRepository;
import br.ufrn.imd.universitymanagement.repository.TurmaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    TurmaRepository turmaRepository;

    public AlunoEntity criarAluno(AlunoRecordDTO alunoRecordDTO){
        AlunoEntity aluno = new AlunoEntity();

        BeanUtils.copyProperties(alunoRecordDTO, aluno);

        return alunoRepository.save(aluno);
    }
    public boolean matricularAluno(Long idTurma, Long idAluno){
        var turmaOpt = turmaRepository.findById(idTurma);
        var alunoOpt = alunoRepository.findById(idAluno);
        if (turmaOpt.isEmpty()){
            return false;
        }
        if (alunoOpt.isEmpty()){
            return false;
        }

        var turma = turmaOpt.get();
        var aluno = alunoOpt.get();

        if (turma.getListaAlunos().contains(aluno)){
            return false;
        }

        turma.getListaAlunos().add(aluno);
        aluno.getTurmas().add(turma);
        return true;
    }

    public AlunoEntity criarAluno2(AlunoEntity alunoEntity){
        return alunoRepository.save(alunoEntity);
    }

    public Optional<AlunoEntity> findById(Long id){
        return alunoRepository.findById(id);
    }

    public List<AlunoEntity> getAll(){
        return alunoRepository.findAll();
    }

    public List<AlunoEntity> getAllAtivo(){
        return alunoRepository.findByAtivoTrue();
    }

    public AlunoEntity putAluno(Long id, AlunoRecordDTO alunoRecordDTO){
        var aluno = findById(id);
        if (aluno.isEmpty()){
            return null;
        }
        alunoRecordDTO.putMetodo(aluno.get());
        alunoRepository.save(aluno.get());
        return aluno.get();
    }

    public boolean deletarAluno(Long id){
        var alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isEmpty()){
            return false;
        }
        if (alunoOpt.isPresent()){
            var aluno = alunoOpt.get();
            Set<TurmaEntity> turmas = aluno.getTurmas();
            for (TurmaEntity turma : turmas){
                turma.removeAluno(aluno);
                turmaRepository.save(turma);
            }
            alunoRepository.delete(aluno);
            return true;
        } else {
            throw new RuntimeException("Aluno não foi encontrado");
        }
    }

    public boolean removerAluno(Long idTurma, Long idAluno){
        var turmaOpt = turmaRepository.findById(idTurma);
        if (turmaOpt.isEmpty()){
            return false;
        }

        var turma = turmaOpt.get();
        var alunoOpt = alunoRepository.findById(idAluno);

        if (alunoOpt.isPresent()){
            var aluno = alunoOpt.get();
            if (turma.getListaAlunos().contains(aluno)){
                turma.removeAluno(aluno);
                turmaRepository.save(turma);
            }
            return true;
        }
        return false;
    }

    public boolean deletarLogico(Long id){
        var aluno = findById(id);

        if (aluno.isEmpty()) {
            return false;
        }

        aluno.get().setAtivo(false);
        alunoRepository.save(aluno.get());
        return true;
    }

    public boolean aluno_turma(Long id){
        var alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isEmpty()){
            return false;
        }

        var aluno = alunoOpt.get();
        Set<TurmaEntity> turmas = aluno.getTurmas();

        List<String> nomeTurma = turmas.stream().map(TurmaEntity::getNome).toList();

        return true;
    }

}
