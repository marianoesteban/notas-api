package marianoesteban.notas.service;

import org.springframework.stereotype.Service;

import marianoesteban.notas.model.Etiqueta;

@Service
public interface EtiquetaService {

	Etiqueta findByEtiqueta(String etiqueta);
	
	void add(Etiqueta etiqueta);
}
