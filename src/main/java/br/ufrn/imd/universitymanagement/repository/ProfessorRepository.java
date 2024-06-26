package br.ufrn.imd.universitymanagement.repository;

import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import br.ufrn.imd.universitymanagement.model.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    List<ProfessorEntity> findByAtivoTrue();
}
