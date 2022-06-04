package marianoesteban.notas.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NotaDto {

	@NotEmpty
	@Size(max = 80)
	private String titulo;

	@NotNull
	@Size(max = 500)
	private String texto;
	
	private List<String> etiquetas;

	public NotaDto(String titulo, String texto, List<String> etiquetas) {
		this.titulo = titulo;
		this.texto = texto;
		this.etiquetas = etiquetas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<String> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<String> etiquetas) {
		this.etiquetas = etiquetas;
	}

}
