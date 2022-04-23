package marianoesteban.notas.service;

import org.springframework.stereotype.Service;

import marianoesteban.notas.model.Nota;

@Service
public interface NotaService {

	Nota findById(long idNota);
}
