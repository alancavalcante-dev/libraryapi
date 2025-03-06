package io.github.alanpcavalcante.libraryapi.service;


import io.github.alanpcavalcante.libraryapi.exception.AutorDuplicado;
import io.github.alanpcavalcante.libraryapi.exception.NenhumAutorEncontradoException;
import io.github.alanpcavalcante.libraryapi.model.Autor;
import io.github.alanpcavalcante.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    public final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public List<Autor> autoresRepetidos(Autor autor) {
        return repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );
    }

    public List<Autor> todosAutores() {
        return repository.findAll();
    }

    public Optional<Autor> pegarAutorPorId(String id) {
        return repository.findById(UUID.fromString(id));
    }

    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }

    public Autor atualizarAutor(String id, Autor autorAtualizado) {

    }


    public void deletarAutor(Autor autor) {
        repository.delete(autor);
    }


}
