package marianoesteban.notas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import marianoesteban.notas.model.Nota;

@Service
public interface NotaService {

	Nota findById(long idNota);

	List<Nota> findAll();

	Nota add(Nota nota);

	Nota update(Nota nota);

	void delete(long idNota);
}
