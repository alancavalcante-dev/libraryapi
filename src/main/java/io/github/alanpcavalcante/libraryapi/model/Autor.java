package io.github.alanpcavalcante.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @Column(name = "data_cadastro")
    @CreatedDate
    private LocalDate dataCadastro;

    @Column(name = "ultima_atualizacao")
    @LastModifiedDate
    private LocalDateTime ultimaAtualizacao;



    // @Transient = ignora a propriedade


    @OneToMany(
            mappedBy = "autor",
            fetch = FetchType.LAZY
    )
    private List<Livro> livros;


//    @Deprecated
//    public Autor() {
//        // Para uso do framework
//    }

}
