package br.ufrn.imd.universitymanagement.repository;

import br.ufrn.imd.universitymanagement.model.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

    List<TurmaEntity> findByAtivoTrue();
}
