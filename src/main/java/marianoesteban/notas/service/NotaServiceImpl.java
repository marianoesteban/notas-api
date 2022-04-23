package marianoesteban.notas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marianoesteban.notas.exception.NotFoundException;
import marianoesteban.notas.model.Nota;
import marianoesteban.notas.repository.NotaRepository;

@Service
public class NotaServiceImpl implements NotaService {

	@Autowired
	private NotaRepository notaRepository;

	@Override
	public Nota findById(long idNota) {
		return notaRepository.findById(idNota)
				.orElseThrow(() -> new NotFoundException("No existe una nota con el ID especificado"));
	}

}
