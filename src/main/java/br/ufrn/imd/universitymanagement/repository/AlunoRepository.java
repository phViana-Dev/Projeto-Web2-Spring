package br.ufrn.imd.universitymanagement.repository;

import br.ufrn.imd.universitymanagement.model.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {

    List<AlunoEntity> findByAtivoTrue();
}
