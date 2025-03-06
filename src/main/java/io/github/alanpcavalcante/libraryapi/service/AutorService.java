package io.github.alanpcavalcante.libraryapi.service;



import io.github.alanpcavalcante.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.alanpcavalcante.libraryapi.model.Autor;
import io.github.alanpcavalcante.libraryapi.repository.AutorRepository;
import io.github.alanpcavalcante.libraryapi.repository.LivroRepository;
import io.github.alanpcavalcante.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    public final AutorRepository repository;
    public final AutorValidator validator;
    public final LivroRepository livroRepository;

    public AutorService(
            AutorRepository repository,
            AutorValidator validator,
            LivroRepository livroRepository) {
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }



    public Optional<Autor> autoresRepetidos(Autor autor) {
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
        validator.validar(autor);
        return repository.save(autor);
    }



    public void deletarAutor(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um autor que possui livros cadastrados!"
            );
        }
        repository.delete(autor);
    }



    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

}
