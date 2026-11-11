package br.com.biblioteca.model;

/**
 * CONCEITO: enum.
 * Um conjunto FECHADO de valores possiveis. Melhor que usar String ("ficcao", "Ficcao", "FICÇÃO"...),
 * porque o compilador impede valores invalidos.
 * Enums podem ter campos, construtor e metodos, como uma classe.
 */
public enum Genero {
    FICCAO("Ficcao"),
    TECNOLOGIA("Tecnologia"),
    HISTORIA("Historia"),
    BIOGRAFIA("Biografia");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
