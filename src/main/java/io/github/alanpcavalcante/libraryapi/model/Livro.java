package io.github.alanpcavalcante.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity // Define a classe como uma entidade JPA (mapeia para uma tabela no banco)
@Table(name = "Livro", schema = "public") // Especifica o nome da tabela (Livro) e o esquema
@Data // Gera automaticamente getters, setters, toString(), equals(), hashCode() e um construtor para os atributos não final (da biblioteca Lombok).
@Getter // Gera apenas os métodos get e set para todos os atributos.
@Setter // Gera apenas os métodos get e set para todos os atributos.
@ToString // Gera um método toString() que inclui todos os campos
@EqualsAndHashCode // Gera os métodos equals() e hashCode()
@RequiredArgsConstructor // Cria um construtor para os atributos final e com @NonNull
//@NoArgsConstructor // (comentado) → Criaria um construtor sem argumentos (necessário para JPA em algumas situações
@AllArgsConstructor // Gera um construtor com todos os atributos
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30,nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
