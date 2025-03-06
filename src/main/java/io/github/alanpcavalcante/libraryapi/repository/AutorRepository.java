package io.github.alanpcavalcante.libraryapi.repository;

import io.github.alanpcavalcante.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNomeAndDataNascimentoAndNacionalidade(
            String nome,
            LocalDate dataNascimento,
            String nacionalidade
    );


}
