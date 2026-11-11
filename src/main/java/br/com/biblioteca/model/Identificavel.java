package br.com.biblioteca.model;

/**
 * CONCEITO: interface como "contrato".
 * Qualquer classe que implementa Identificavel promete ter um metodo id().
 * Isso permite escrever UM repositorio generico que funciona para Livro, Usuario e Emprestimo.
 */
public interface Identificavel {
    Long id();
}
