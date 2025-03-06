package io.github.alanpcavalcante.libraryapi.controller;

import io.github.alanpcavalcante.libraryapi.controller.dto.AutorDTO;
import io.github.alanpcavalcante.libraryapi.controller.dto.ErroResposta;
import io.github.alanpcavalcante.libraryapi.exception.AutorDuplicado;
import io.github.alanpcavalcante.libraryapi.exception.NenhumAutorEncontradoException;
import io.github.alanpcavalcante.libraryapi.model.Autor;
import io.github.alanpcavalcante.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("api/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Autor>> todosAutores(@PathVariable(required = false) String nome) {
        List<Autor> autores = service.todosAutores();
        if (autores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(autores);
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> pegarAutorPorId(@PathVariable String id) {
        Optional<Autor> autorPego = service.pegarAutorPorId(id);

        if (autorPego.isPresent()) {
            Autor autor = autorPego.get();
            AutorDTO autorDTO = new AutorDTO(
                    autor.getId(), autor.getNome(),
                    autor.getDataNascimento(), autor.getNacionalidade()
            );
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> criarAutor(@RequestBody AutorDTO autorDTO) {
        Autor autor = autorDTO.mapearParaAutor();
        List<Autor> autores = service.autoresRepetidos(autor);
        if (autores.isEmpty()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            service.salvar(autor);
            return ResponseEntity.created(location).build();
        }

        Map<String, Object> respostaErro = new HashMap<>();
        respostaErro.put("status", 409);
        respostaErro.put("message", "Registro Duplicado");
        respostaErro.put("errors", Collections.emptyList());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(respostaErro);
    }

    @PutMapping("{id}")
    public ResponseEntity<Autor> atualizarAutor(@PathVariable String idAutor, @RequestBody AutorDTO autorDTO) {
        Autor autorMapeado = autorDTO.mapearParaAutor();
        Optional<Autor> autorPego = service.pegarAutorPorId(idAutor);

        if (autorPego.isPresent()) {
            Autor autor = autorPego.get();
            List<Autor> autores = service.autoresRepetidos(autorMapeado);

            if (autores.isEmpty()) {
                autor.setNome(autorMapeado.getNome());
                autor.setDataNascimento(autorMapeado.getDataNascimento());
                autor.setNacionalidade(autorMapeado.getNacionalidade());
                service.salvar(autor);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Autor> deletarAutor(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Autor> possivelAutor = service.pegarAutorPorId(id);
        if (possivelAutor.isPresent()) {
            Autor autor = possivelAutor.get();
            if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
                service.deletarAutor(autor);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.notFound().build();
    }



}
