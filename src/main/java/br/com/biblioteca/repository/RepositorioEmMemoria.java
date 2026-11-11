package br.com.biblioteca.repository;

import br.com.biblioteca.model.Identificavel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CONCEITO: Map.
 * Usamos LinkedHashMap<id, entidade>: busca por id em O(1) e mantem a ordem de insercao.
 * (HashMap nao garante ordem; TreeMap ordena pela chave.)
 */
public class RepositorioEmMemoria<T extends Identificavel> implements Repositorio<T> {

    private final Map<Long, T> dados = new LinkedHashMap<>();

    @Override
    public T salvar(T entidade) {
        dados.put(entidade.id(), entidade);
        return entidade;
    }

    @Override
    public Optional<T> buscarPorId(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<T> listarTodos() {
        // Devolvemos uma COPIA para ninguem de fora alterar nossa colecao interna.
        return new ArrayList<>(dados.values());
    }

    @Override
    public void remover(Long id) {
        dados.remove(id);
    }
}
