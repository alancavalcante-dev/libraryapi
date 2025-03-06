package io.github.alanpcavalcante.libraryapi.repository;

import io.github.alanpcavalcante.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Alan");
        autor.setDataNascimento(LocalDate.of(2003, 3, 16));
        autor.setNacionalidade("Brasileiro");

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);

    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("a66fa2ef-c2e9-4540-9198-b7e9c5e753b2");

        /*
        Optional<T> é uma classe do Java usada para evitar NullPointerException ao lidar com valores que podem ser null.
        Se o autor for encontrado → findById(id) retorna Optional<Autor> contendo o objeto Autor.
        Se o autor NÃO for encontrado → findById(id) retorna Optional.empty(), indicando que não há um autor com esse ID.
        */
        Optional<Autor> possivelAutor = autorRepository.findById(id);


        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: " + autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(2003, 3, 16));

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("a66fa2ef-c2e9-4540-9198-b7e9c5e753b2");
        autorRepository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("35cb4e54-00be-4798-b991-07c601b8ece9");

        // .get() = pega o objeto
        var maria = autorRepository.findById(id).get();
        autorRepository.delete(maria);
    }
}
