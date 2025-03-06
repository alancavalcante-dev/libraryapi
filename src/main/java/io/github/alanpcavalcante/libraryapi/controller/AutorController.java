package io.github.alanpcavalcante.libraryapi.controller;

import io.github.alanpcavalcante.libraryapi.controller.dto.AutorDTO;
import io.github.alanpcavalcante.libraryapi.controller.dto.ErroResposta;
import io.github.alanpcavalcante.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.alanpcavalcante.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.alanpcavalcante.libraryapi.model.Autor;
import io.github.alanpcavalcante.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> todosAutores(@PathVariable(required = false) String nome) {
        List<Autor> autores = service.todosAutores();
        if (autores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<AutorDTO> autoresDto = autores.stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(autoresDto);
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
        try {
            Autor autor = autorDTO.mapearParaAutor();
            service.salvar(autor);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
            }

        catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarAutor(@PathVariable String id, @RequestBody AutorDTO autorDTO) {
        try {
            Autor autorMapeado = autorDTO.mapearParaAutor();
            Optional<Autor> autorPego = service.pegarAutorPorId(id);
            if (autorPego.isPresent()) {
                service.salvar(autorMapeado);
                return ResponseEntity.ok(autorDTO);
            }
            return ResponseEntity.notFound().build();
        }
        catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable String id) {
        try {
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
        catch (OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }

    }



}
