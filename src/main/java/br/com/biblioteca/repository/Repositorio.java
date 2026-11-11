package br.com.biblioteca.repository;

import br.com.biblioteca.model.Identificavel;

import java.util.List;
import java.util.Optional;

/**
 * CONCEITO: generics + interface.
 * <T extends Identificavel> = "T pode ser qualquer tipo, desde que tenha id()".
 *
 * Por que interface? O servico depende do CONTRATO, nao da implementacao.
 * Hoje guardamos em memoria; na Semana 5 trocamos por banco (Spring Data JPA)
 * sem mexer no servico. Isso e o "D" do SOLID (Inversao de Dependencia).
 */
public interface Repositorio<T extends Identificavel> {

    T salvar(T entidade);

    /**
     * CONCEITO: Optional.
     * Em vez de devolver null (e arriscar NullPointerException),
     * devolvemos um Optional que pode estar vazio. Quem chama e obrigado a pensar no caso vazio.
     */
    Optional<T> buscarPorId(Long id);

    List<T> listarTodos();

    void remover(Long id);
}
