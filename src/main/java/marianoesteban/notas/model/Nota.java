package marianoesteban.notas.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nota")
	private Long id;

	private String titulo;

	private String texto;

	private LocalDateTime fechaCreacion;

	private LocalDateTime fechaModificacion;

	@ManyToMany
	@JoinTable(name = "etiqueta_nota", joinColumns = @JoinColumn(name = "id_nota"), inverseJoinColumns = @JoinColumn(name = "id_etiqueta"))
	private Set<Etiqueta> etiquetas;

	public Nota() {
	}

	public Nota(Long id, String titulo, String texto, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
			Set<Etiqueta> etiquetas) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.etiquetas = etiquetas;
	}

	public Nota(Long id, String titulo, String texto, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
		this.id = id;
		this.titulo = titulo;
		this.texto = texto;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
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

	public Set<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Set<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	@Override
	public String toString() {
		return "Nota [id=" + id + ", titulo=" + titulo + ", texto=" + texto + ", fechaCreacion=" + fechaCreacion
				+ ", fechaModificacion=" + fechaModificacion + ", etiquetas=" + etiquetas + "]";
	}

}
