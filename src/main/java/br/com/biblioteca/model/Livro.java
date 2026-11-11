package br.com.biblioteca.model;

/**
 * CONCEITO: record (Java 16+).
 * Um record e uma classe IMUTAVEL de dados. O compilador gera automaticamente:
 *   - construtor com todos os campos
 *   - metodos de acesso: titulo(), autor()... (sem o "get")
 *   - equals(), hashCode() e toString()
 *
 * Como o record ja tem o metodo id(), ele satisfaz a interface Identificavel "de graca".
 */
public record Livro(Long id, String titulo, String autor, int ano, Genero genero) implements Identificavel {

    /**
     * CONCEITO: construtor compacto.
     * Roda antes de os campos serem atribuidos. Bom lugar para validar.
     * Se a validacao falhar, o objeto nunca chega a existir num estado invalido.
     */
    public Livro {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo e obrigatorio");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor e obrigatorio");
        }
        if (ano < 0) {
            throw new IllegalArgumentException("Ano invalido: " + ano);
        }
        titulo = titulo.trim();
        autor = autor.trim();
    }
}
