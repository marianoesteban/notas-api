package marianoesteban.notas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import marianoesteban.notas.model.Etiqueta;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {

	Optional<Etiqueta> findByEtiqueta(String etiqueta);
}
