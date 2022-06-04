package marianoesteban.notas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marianoesteban.notas.exception.NotFoundException;
import marianoesteban.notas.model.Etiqueta;
import marianoesteban.notas.repository.EtiquetaRepository;

@Service
public class EtiquetaServiceImpl implements EtiquetaService {
	
	@Autowired
	private EtiquetaRepository etiquetaRepository;

	@Override
	public Etiqueta findByEtiqueta(String etiqueta) {
		return etiquetaRepository.findByEtiqueta(etiqueta)
				.orElseThrow(() -> new NotFoundException("No existe la etiqueta especificada"));
	}

	@Override
	public void add(Etiqueta etiqueta) {
		etiquetaRepository.save(etiqueta);
	}

}
