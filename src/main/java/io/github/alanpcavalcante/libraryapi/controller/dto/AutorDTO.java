package io.github.alanpcavalcante.libraryapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.alanpcavalcante.libraryapi.model.Autor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AutorDTO {

//    @JsonIgnore
    private UUID id;
    private String nome;
    private LocalDate dataNascimento;
    private String nacionalidade;

    public AutorDTO() {

    }

    public AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
    }

    public AutorDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
    }

    public Autor mapearParaAutor() {
        Autor autorEntidade = new Autor();
        autorEntidade.setNome(nome);
        autorEntidade.setDataNascimento(dataNascimento);
        autorEntidade.setNacionalidade(nacionalidade);
        return autorEntidade;
    }

}
