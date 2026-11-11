package br.com.biblioteca.model;

public record Usuario(Long id, String nome, String email) implements Identificavel {

    public Usuario {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome e obrigatorio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalido: " + email);
        }
    }
}
