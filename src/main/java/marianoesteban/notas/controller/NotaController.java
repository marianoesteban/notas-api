package marianoesteban.notas.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import marianoesteban.notas.dto.NotaDto;
import marianoesteban.notas.exception.NotFoundException;
import marianoesteban.notas.model.Etiqueta;
import marianoesteban.notas.model.Nota;
import marianoesteban.notas.rest.Envelope;
import marianoesteban.notas.service.EtiquetaService;
import marianoesteban.notas.service.NotaService;

@Api(tags = "Notas")
@RestController
@RequestMapping("/notas")
public class NotaController {

	@Autowired
	private NotaService notaService;
	
	@Autowired
	private EtiquetaService etiquetaService;

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Obtener una nota")
	@GetMapping("/{idNota}")
	public ResponseEntity<?> getNotaById(@PathVariable("idNota") long idNota, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_LOCATION, request.getRequestURI())
				.body(new Envelope<NotaDto>(toDto(notaService.findById(idNota))));
	}

	@ApiOperation(value = "Obtener todas las notas")
	@GetMapping("/")
	public ResponseEntity<?> getNotas(HttpServletRequest request) {
		List<Nota> notas = notaService.findAll();

		if (notas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.header(HttpHeaders.CONTENT_LOCATION, request.getRequestURI())
					.build();
		}

		List<NotaDto> notaDtos = new ArrayList<NotaDto>();
		for (Nota nota : notas)
			notaDtos.add(toDto(nota));

		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_LOCATION, request.getRequestURI())
				.body(new Envelope<List<NotaDto>>(notaDtos));
	}

	@ApiOperation(value = "Crear una nueva nota")
	@PostMapping("/")
	public ResponseEntity<?> createNota(@Valid @RequestBody NotaDto notaDto, HttpServletRequest request) {
		Nota nota = toNota(notaDto);
		Nota newNota = notaService.add(nota);

		String locationUri = request.getRequestURI() + newNota.getId();

		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, locationUri)
				.header(HttpHeaders.CONTENT_LOCATION, locationUri)
				.body(new Envelope<NotaDto>(toDto(newNota)));
	}

	@ApiOperation(value = "Modificar una nota")
	@PutMapping("/{idNota}")
	public ResponseEntity<?> updateNota(@PathVariable("idNota") long idNota, @Valid @RequestBody NotaDto notaDto,
			HttpServletRequest request) {
		Nota nota = toNota(notaDto);
		nota.setId(idNota);
		Nota updatedNota = notaService.update(nota);

		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_LOCATION, request.getRequestURI())
				.body(new Envelope<NotaDto>(toDto(updatedNota)));
	}

	@ApiOperation(value = "Eliminar nota")
	@DeleteMapping("/{idNota}")
	public ResponseEntity<?> deleteNota(@PathVariable("idNota") long idNota) {
		notaService.delete(idNota);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	private NotaDto toDto(Nota nota) {
		Long id = nota.getId();
		String titulo = nota.getTitulo();
		String texto = nota.getTexto();
		LocalDateTime fechaCreacion = nota.getFechaCreacion();
		LocalDateTime fechaModificacion = nota.getFechaModificacion();
		List<String> etiquetas = new ArrayList<String>();
		for (Etiqueta etiqueta : nota.getEtiquetas())
			etiquetas.add(etiqueta.getEtiqueta());
		return new NotaDto(id, titulo, texto, fechaCreacion, fechaModificacion, etiquetas);
	}

	private Nota toNota(NotaDto notaDto) {
		Set<Etiqueta> etiquetas = new HashSet<Etiqueta>();
		if (notaDto.getEtiquetas() != null) {
			for (String etiquetaNota : notaDto.getEtiquetas()) {
				Etiqueta etiqueta;
				try {
					etiqueta = etiquetaService.findByEtiqueta(etiquetaNota);
				} catch (NotFoundException notFoundException) {
					etiqueta = new Etiqueta(etiquetaNota);
					etiquetaService.add(etiqueta);
				}
				etiquetas.add(etiqueta);
			}
		}
		return new Nota(notaDto.getTitulo(), notaDto.getTexto(), etiquetas);
	}
}
