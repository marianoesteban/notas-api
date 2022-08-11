package marianoesteban.notas.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NotaDto {

	Long id;

	@NotEmpty
	@Size(max = 80)
	private String titulo;

	@NotNull
	@Size(max = 500)
	private String texto;
	
	private LocalDateTime fechaCreacion;

	private LocalDateTime fechaModificacion;

	private List<String> etiquetas;

	public NotaDto() {
	}

	public NotaDto(String titulo, String texto, List<String> etiquetas) {
		this.titulo = titulo;
		this.texto = texto;
		this.etiquetas = etiquetas;
	}

	public NotaDto(Long id, String titulo, String texto, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			List<String> etiquetas) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.etiquetas = etiquetas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public List<String> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<String> etiquetas) {
		this.etiquetas = etiquetas;
	}

}
