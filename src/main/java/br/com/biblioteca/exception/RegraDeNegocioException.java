package br.com.biblioteca.exception;

/**
 * Lancada quando uma operacao e valida tecnicamente, mas viola uma regra da biblioteca
 * (ex.: emprestar um livro que ja esta emprestado).
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
