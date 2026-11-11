package br.com.biblioteca.exception;

/**
 * CONCEITO: excecao customizada UNCHECKED (estende RuntimeException).
 * Unchecked = o compilador nao obriga a tratar com try/catch.
 * E o padrao no Spring: excecoes de negocio costumam ser unchecked
 * e sao tratadas num lugar so (veremos @RestControllerAdvice na Semana 6).
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super("%s com id %d nao encontrado".formatted(recurso, id));
    }
}
