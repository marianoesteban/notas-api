package marianoesteban.notas.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import marianoesteban.notas.dto.NotaDto;
import marianoesteban.notas.exception.NotFoundException;
import marianoesteban.notas.model.Etiqueta;
import marianoesteban.notas.model.Nota;
import marianoesteban.notas.service.EtiquetaService;
import marianoesteban.notas.service.NotaService;

@WebMvcTest(NotaController.class)
public class NotaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private NotaService notaService;
	
	@MockBean
	private EtiquetaService etiquetaService;

	private static final Etiqueta ETIQUETA_1 = new Etiqueta(1L, "mi-etiqueta");

	private static final Nota NOTA_1 = new Nota(1L, "Título", "El contenido de la nota",
			LocalDateTime.of(2022, Month.APRIL, 17, 21, 42), LocalDateTime.of(2022, Month.APRIL, 17, 21, 42));
	private static final Nota NOTA_2 = new Nota(2L, "Nota con etiquetas", "Esta nota contiene etiquetas",
			LocalDateTime.of(2022, Month.MARCH, 18, 20, 15), LocalDateTime.of(2022, Month.APRIL, 17, 22, 01),
			Set.of(ETIQUETA_1));
	
	@Test
	public void shouldFetchNotaWithId1() throws Exception {
		when(notaService.findById(1L)).thenReturn(NOTA_1);
		
		mockMvc.perform(get("/notas/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Content-Location", "/notas/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.id", is(1)))
			.andExpect(jsonPath("$.data.titulo", is("Título")))
			.andExpect(jsonPath("$.data.texto", is("El contenido de la nota")))
			.andExpect(jsonPath("$.data.fechaCreacion", is("2022-04-17T21:42:00")))
			.andExpect(jsonPath("$.data.fechaModificacion", is("2022-04-17T21:42:00")));
	}
	
	@Test
	public void shouldFetchNotaWithId2() throws Exception {
		when(notaService.findById(2L)).thenReturn(NOTA_2);
		
		mockMvc.perform(get("/notas/2").contentType(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Content-Location", "/notas/2"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.id", is(2)))
			.andExpect(jsonPath("$.data.titulo", is("Nota con etiquetas")))
			.andExpect(jsonPath("$.data.texto", is("Esta nota contiene etiquetas")))
			.andExpect(jsonPath("$.data.fechaCreacion", is("2022-03-18T20:15:00")))
			.andExpect(jsonPath("$.data.fechaModificacion", is("2022-04-17T22:01:00")))
			.andExpect(jsonPath("$.data.etiquetas[?(@.id == 1)][?(@.etiqueta == 'mi-etiqueta')]").exists());
	}
	
	@Test
	public void shouldReturn404WhenFindNotaBydId() throws Exception {
		when(notaService.findById(1L)).thenThrow(new NotFoundException("No existe una nota con el ID especificado"));
		
		mockMvc.perform(get("/notas/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturn400WhenFindNotaByInvalidId() throws Exception {
		mockMvc.perform(get("/notas/1a").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldFetchAllNotas() throws Exception {
		List<Nota> notas = Arrays.asList(NOTA_1, NOTA_2);
		when(notaService.findAll()).thenReturn(notas);

		mockMvc.perform(get("/notas/").contentType(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Content-Location", "/notas/"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.length()", is(2)))
			.andExpect(jsonPath("$.data[?(@.id == 1)]").exists())
			.andExpect(jsonPath("$.data[?(@.id == 2)]").exists());
	}

	@Test
	public void shouldReturn204WhenNotasIsEmpty() throws Exception {
		when(notaService.findAll()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/notas/").contentType(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Content-Location", "/notas/"))
			.andExpect(status().isNoContent());
	}

	@Test
	public void shouldCreateNewNota() throws Exception {
		NotaDto notaDto = new NotaDto("Nueva nota", "El texto de la nota.", List.of(ETIQUETA_1.getEtiqueta()));
		Nota nota = new Nota(1L, notaDto.getTitulo(), notaDto.getTexto(), LocalDateTime.of(2022, Month.MAY, 20, 22, 00),
				LocalDateTime.of(2022, Month.MAY, 20, 22, 00), Set.of(ETIQUETA_1));

		when(notaService.add(any(Nota.class))).thenReturn(nota);

		mockMvc.perform(post("/notas/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(notaDto)))
			.andExpect(header().string("Location", "/notas/1"))
			.andExpect(header().string("Content-Location", "/notas/1"))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.titulo", is("Nueva nota")))
			.andExpect(jsonPath("$.data.texto", is("El texto de la nota.")));
	}

	@Test
	public void shouldReturn400WhenTituloIsEmpty() throws Exception {
		NotaDto notaDto = new NotaDto("", "El texto de la nota.", List.of(ETIQUETA_1.getEtiqueta()));

		mockMvc.perform(post("/notas/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(notaDto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldReturn400WhenTextoIsNull() throws Exception {
		NotaDto notaDto = new NotaDto("Nueva nota", null, List.of(ETIQUETA_1.getEtiqueta()));

		mockMvc.perform(post("/notas/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(notaDto)))
			.andExpect(status().isBadRequest());
	}
}
