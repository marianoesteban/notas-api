package marianoesteban.notas.service;

import java.time.LocalDateTime;
import java.util.List;

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

	@Override
	public List<Nota> findAll() {
		return notaRepository.findAll();
	}

	@Override
	public Nota add(Nota nota) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		nota.setFechaCreacion(currentDateTime);
		nota.setFechaModificacion(currentDateTime);
		return notaRepository.save(nota);
	}

	@Override
	public Nota update(Nota nota) {
		Nota oldNota = notaRepository.findById(nota.getId())
				.orElseThrow(() -> new NotFoundException("No existe una nota con el ID especificado"));
		nota.setFechaCreacion(oldNota.getFechaCreacion());
		nota.setFechaModificacion(LocalDateTime.now());
		return notaRepository.save(nota);
	}

}
