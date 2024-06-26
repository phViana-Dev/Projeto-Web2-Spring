package br.ufrn.imd.universitymanagement.service;

import br.ufrn.imd.universitymanagement.dto.AlunoRecordDTO;
import br.ufrn.imd.universitymanagement.dto.ProfessorRecordDTO;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.model.ProfessorEntity;
import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import br.ufrn.imd.universitymanagement.repository.ProfessorRepository;
import br.ufrn.imd.universitymanagement.repository.TurmaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    TurmaRepository turmaRepository;

    public ProfessorEntity criarProfessor(ProfessorRecordDTO professorRecordDTO){
        ProfessorEntity professorEntity = new ProfessorEntity();

        BeanUtils.copyProperties(professorRecordDTO, professorEntity);

        return professorRepository.save(professorEntity);
    }

    public Optional<ProfessorEntity> findById(Long id){
        return professorRepository.findById(id);
    }

    public List<ProfessorEntity> getAll(){
        return professorRepository.findAll();
    }

    public List<ProfessorEntity> getAllAtivo(){
        return professorRepository.findByAtivoTrue();
    }

    public ProfessorEntity putProfessor(Long id, ProfessorRecordDTO professorRecordDTO){
        var professor = findById(id);

        if (professor.isEmpty()){
            return null;
        }

        professorRecordDTO.putMetodo(professor.get());
        professorRepository.save(professor.get());
        return professor.get();
    }

    public boolean deletarProfessor(Long id){
        var professor = findById(id);

        if (professor.isEmpty()) {
            return false;
        }

        professorRepository.delete(professor.get());
        return true;
    }

    public boolean removerProfToTurma(Long idTurma, Long idProf){
        var turmaOpt = turmaRepository.findById(idTurma);
        var professorOpt = professorRepository.findById(idProf);

        if (turmaOpt.isEmpty()){
            throw new RuntimeException("Turma not found");
        }
        if (professorOpt.isEmpty()){
            throw new RuntimeException("Professor not found");
        }

        var turma = turmaOpt.get();
        if (turma.getProfessor().getId() == idProf){
            turma.setProfessor(null);
            turmaRepository.save(turma);
            return true;
        }
        throw new RuntimeException("Algo deu errado");
    }

    public boolean professor_turma(Long id){
        var professorOpt = professorRepository.findById(id);
        if (professorOpt.isEmpty()){
            return false;
        }

        var professor = professorOpt.get();
        List<TurmaEntity> turmas = professor.getTurmas();

        List<String> nomesTurmas = turmas.stream().map(TurmaEntity::getNome).collect(Collectors.toList());

        return true;
    }


    public boolean deletarLogico(Long id){
        var professor = findById(id);

        if (professor.isEmpty()) {
            return false;
        }

        professor.get().setAtivo(false);
        professorRepository.save(professor.get());
        return true;
    }

}
