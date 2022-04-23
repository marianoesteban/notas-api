package marianoesteban.notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import marianoesteban.notas.model.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

}
