package br.ufrn.imd.universitymanagement.controller;

import br.ufrn.imd.universitymanagement.dto.TurmaRecordDTO;
import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import br.ufrn.imd.universitymanagement.service.AlunoService;
import br.ufrn.imd.universitymanagement.service.ProfessorService;
import br.ufrn.imd.universitymanagement.service.TurmaService;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    TurmaService turmaService;

    @Autowired
    ProfessorService professorService;

    @Autowired
    AlunoService alunoService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        Optional<TurmaEntity> turma = turmaService.getById(id);

        if (turma.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        var turma = turmaService.getAll();

        if (turma.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TurmaAtivo not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Object> getAllAtivo(){
        var turmaAtivo = turmaService.getAllAtivo();

        if (turmaAtivo.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TurmaAtivo not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(turmaAtivo);
    }

    @PostMapping("/criar")
    public ResponseEntity<TurmaEntity> postTurma( @RequestBody TurmaRecordDTO turmaRecordDTO){
        var turma = turmaService.salvarTurma(turmaRecordDTO);

        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }

    @PreUpdate
    @Transactional
    @PutMapping("/{idTurma}/associarProfToTurma/{idProf}")
    public ResponseEntity<Object> associarProfToTurma(@PathVariable Long idTurma,
                                                      @PathVariable Long idProf){
        if (!turmaService.associarProfessorToTurma(idProf, idTurma)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to associate Professor to Turma");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Associate was successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putTurma(@PathVariable(name = "id") Long id, @RequestBody TurmaRecordDTO turmaNovoDTO){
        Optional<TurmaEntity> turma = turmaService.getById(id);
        if (turma.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma not found");
        }

        TurmaEntity turmaEntity = turma.get();
        turmaService.putTurma(id, turmaNovoDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Updated was successfully");
    }

    @DeleteMapping("/deleteFisico/{id}")
    public ResponseEntity<Object> deleteTurma(@PathVariable Long id){
        if (turmaService.deletarTurma(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Turma deleted successfully");
    }

    @DeleteMapping("/deleteLogico/{id}")
    public ResponseEntity<Object> deleteLogic(@PathVariable(name = "id") Long id){
        if (turmaService.deletarLogico(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma not found");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma deleted successfully");
    }

    @DeleteMapping("/{idTurma}/removerProfessor/{idProfessor}")
    public ResponseEntity<Object> removerProfessor(@PathVariable Long idTurma,
                                                   @PathVariable Long idProfessor){

        if (!professorService.removerProfToTurma(idTurma, idProfessor)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete Professor from Turma");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Delete was successfully");
    }

    @DeleteMapping("/{idTurma}/removerAluno/{idAluno}")
    public ResponseEntity<String> removerAlunoToTurma(@PathVariable (value = "idTurma") Long idTurma,
                                                      @PathVariable (value = "idAluno") Long idAluno){
        if(!alunoService.removerAluno(idTurma, idAluno)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to remove Aluno from Turma");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Remove was successfully");
    }

    @PreUpdate
    @Transactional
    @PutMapping("{idTurma}/matricularAluno/{idAluno}")
    public ResponseEntity<Object> matricularAlunoToTurma(@PathVariable Long idTurma,
                                                         @PathVariable Long idAluno){
        if(!alunoService.matricularAluno(idTurma, idAluno)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to matriculate Aluno from Turma");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Matriculate was successfully");
    }

}
