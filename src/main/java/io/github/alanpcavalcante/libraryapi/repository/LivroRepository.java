package io.github.alanpcavalcante.libraryapi.repository;

import io.github.alanpcavalcante.libraryapi.model.Autor;
import io.github.alanpcavalcante.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    boolean existsByAutor(Autor autor);
}
