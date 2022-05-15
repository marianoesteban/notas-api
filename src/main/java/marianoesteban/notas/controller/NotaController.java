package marianoesteban.notas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import marianoesteban.notas.exception.NotFoundException;
import marianoesteban.notas.model.Nota;
import marianoesteban.notas.rest.Envelope;
import marianoesteban.notas.service.NotaService;

@Api(tags = "Notas")
@RestController
@RequestMapping("/notas")
public class NotaController {

	@Autowired
	private NotaService notaService;
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Obtener una nota")
	@GetMapping("/{idNota}")
	public ResponseEntity<?> getNotaById(@PathVariable("idNota") long idNota, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_LOCATION, request.getRequestURI())
				.body(new Envelope<Nota>(notaService.findById(idNota)));
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

		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_LOCATION, request.getRequestURI())
				.body(new Envelope<List<Nota>>(notas));
	}
}
