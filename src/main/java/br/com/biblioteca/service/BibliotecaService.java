package br.com.biblioteca.service;

import br.com.biblioteca.exception.RecursoNaoEncontradoException;
import br.com.biblioteca.exception.RegraDeNegocioException;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.Repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * CONCEITO: camada de servico.
 * Aqui ficam as REGRAS DE NEGOCIO. O servico recebe os repositorios pelo construtor
 * (injecao de dependencia "na mao"). No Spring, o framework faz essa injecao por voce.
 */
public class BibliotecaService {

    private final Repositorio<Livro> livros;
    private final Repositorio<Usuario> usuarios;
    private final Repositorio<Emprestimo> emprestimos;
    private final AtomicLong sequenciaEmprestimo = new AtomicLong(1);

    public BibliotecaService(Repositorio<Livro> livros,
                             Repositorio<Usuario> usuarios,
                             Repositorio<Emprestimo> emprestimos) {
        this.livros = livros;
        this.usuarios = usuarios;
        this.emprestimos = emprestimos;
    }

    public Livro cadastrarLivro(Livro livro) {
        return livros.salvar(livro);
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarios.salvar(usuario);
    }

    public Emprestimo emprestar(Long livroId, Long usuarioId, LocalDate data) {
        // Optional.orElseThrow: "me da o valor ou lanca essa excecao"
        Livro livro = livros.buscarPorId(livroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro", livroId));
        Usuario usuario = usuarios.buscarPorId(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario", usuarioId));

        if (!estaDisponivel(livro)) {
            throw new RegraDeNegocioException("Livro '%s' ja esta emprestado".formatted(livro.titulo()));
        }

        // TODO EXERCICIO 1: um usuario pode ter no maximo 3 emprestimos ATIVOS.
        //   Se ja tiver 3, lance RegraDeNegocioException.

        Emprestimo emprestimo = new Emprestimo(sequenciaEmprestimo.getAndIncrement(), livro, usuario, data);
        return emprestimos.salvar(emprestimo);
    }

    public Emprestimo devolver(Long emprestimoId, LocalDate data) {
        Emprestimo emprestimo = emprestimos.buscarPorId(emprestimoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Emprestimo", emprestimoId));
        emprestimo.devolver(data);
        return emprestimo;
    }

    /**
     * Versao com laco "for" tradicional, de proposito.
     * Na Semana 2 voce vai reescrever isso com Streams (anyMatch) em uma linha.
     */
    public boolean estaDisponivel(Livro livro) {
        for (Emprestimo e : emprestimos.listarTodos()) {
            if (e.estaAtivo() && e.getLivro().equals(livro)) {
                return false;
            }
        }
        return true;
    }

    public List<Livro> listarLivros() {
        return livros.listarTodos();
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos.listarTodos();
    }

    // TODO EXERCICIO 3: List<Emprestimo> listarAtrasados(LocalDate hoje)
    // TODO EXERCICIO 4: List<Livro> buscarPorAutor(String autor)  -> ignorando maiusculas/minusculas
}
