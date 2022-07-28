package marianoesteban.notas.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Etiqueta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_etiqueta")
	private Long id;
	
	@Column(length = 45)
	private String etiqueta;
	
	@ManyToMany(mappedBy = "etiquetas")
	private Set<Nota> notas;
	
	public Etiqueta() {
	}

	public Etiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Etiqueta(Long id, String etiqueta) {
		this.id = id;
		this.etiqueta = etiqueta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	@Override
	public String toString() {
		return "Etiqueta [id=" + id + ", etiqueta=" + etiqueta + "]";
	}
}
