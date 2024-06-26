package br.ufrn.imd.universitymanagement.controller;

import br.ufrn.imd.universitymanagement.dto.AlunoRecordDTO;
import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.repository.AlunoRepository;
import br.ufrn.imd.universitymanagement.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @PostMapping("/alunos")
    public ResponseEntity<AlunoEntity> salvarAluno(@RequestBody @Valid AlunoRecordDTO alunoRecordDTO){
        var aluno = alunoService.criarAluno(alunoRecordDTO);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @GetMapping("/alunos")
    public ResponseEntity<Object> getAll(){
        List<AlunoEntity> listAluno = alunoService.getAll();
        if (listAluno.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alunos not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listAluno);
    }

    @GetMapping("/alunos/ativos")
    public ResponseEntity<Object> findByAtivoTrue(){
        List<AlunoEntity> listAluno = alunoService.getAllAtivo();
        if (listAluno.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alunos ativos not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listAluno);
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<Object> getOneAluno(@PathVariable(value = "id") Long id){
        Optional<AlunoEntity> aluno = alunoService.findById(id);

        if(aluno.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(aluno.get());
    }

    @PutMapping("/alunos/{id}")
    public ResponseEntity<Object> updateAluno(@PathVariable Long id, @RequestBody AlunoRecordDTO alunoRecordDTO){

        Optional<AlunoEntity> aluno0 = alunoService.findById(id);

        if (alunoService.putAluno(id, alunoRecordDTO) == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Update was successfully");
    }


    @DeleteMapping("alunos/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable(value = "id") Long id){

        if (!alunoService.deletarAluno(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Aluno deleted successfully");
    }

    @DeleteMapping("/alunos/deleteLogico/{id}")
    public ResponseEntity<Object> deleteLogico(@PathVariable(value = "id") Long id){
        if (!alunoService.deletarAluno(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Aluno deleted successfully");
    }
}
