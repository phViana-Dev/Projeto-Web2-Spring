package br.ufrn.imd.universitymanagement.controller;

import br.ufrn.imd.universitymanagement.dto.AlunoRecordDTO;
import br.ufrn.imd.universitymanagement.dto.ProfessorRecordDTO;
import br.ufrn.imd.universitymanagement.model.ProfessorEntity;
import br.ufrn.imd.universitymanagement.repository.ProfessorRepository;
import br.ufrn.imd.universitymanagement.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProfessorController {

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    ProfessorService professorService;

    @PostMapping("/professores")
    public ResponseEntity<Object> salvarProfessor(@RequestBody @Valid ProfessorRecordDTO professorRecordDTO){
        var professor = professorService.criarProfessor(professorRecordDTO);

        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @GetMapping("/professores")
    public ResponseEntity<Object> getAll(){
        List<ProfessorEntity> listProfessor = professorService.getAll();

        if (listProfessor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professores not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listProfessor);
    }

    @GetMapping("/professores/ativos")
    public ResponseEntity<Object> getAllAtivo(){
        List<ProfessorEntity> listProfessor = professorService.getAllAtivo();

        if (listProfessor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No exists Professor activated");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listProfessor);
    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){

        Optional<ProfessorEntity> professor0 = professorService.findById(id);

        if(professor0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(professor0.get());
    }

    @PutMapping("professores/{id}")
    public ResponseEntity<Object> updateProfessor(@PathVariable(value = "id") Long id, @RequestBody ProfessorRecordDTO professorRecordDTO){

        if (professorService.putProfessor(id, professorRecordDTO) == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Update was successfully");
    }

    @DeleteMapping("/professores/{id}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable(value = "id") Long id){

        if (!professorService.deletarProfessor(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Professor deleted successfully");
    }

    @DeleteMapping("professores/deleteLogico/{id}")
    public ResponseEntity<Object> deleteLogico(@PathVariable(value = "id") Long id){
        if (!professorService.deletarLogico(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Aluno deleted successfully");
    }


}
