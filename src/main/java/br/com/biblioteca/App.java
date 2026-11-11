package br.com.biblioteca;

import br.com.biblioteca.exception.RecursoNaoEncontradoException;
import br.com.biblioteca.exception.RegraDeNegocioException;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Genero;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.RepositorioEmMemoria;
import br.com.biblioteca.service.BibliotecaService;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        // "Montando" a aplicacao: criamos as dependencias e injetamos no servico.
        var service = new BibliotecaService(
                new RepositorioEmMemoria<>(),
                new RepositorioEmMemoria<>(),
                new RepositorioEmMemoria<>()
        );

        service.cadastrarLivro(new Livro(1L, "Clean Code", "Robert C. Martin", 2008, Genero.TECNOLOGIA));
        service.cadastrarLivro(new Livro(2L, "Effective Java", "Joshua Bloch", 2018, Genero.TECNOLOGIA));
        service.cadastrarLivro(new Livro(3L, "Dom Casmurro", "Machado de Assis", 1899, Genero.FICCAO));
        service.cadastrarUsuario(new Usuario(1L, "Ana", "ana@email.com"));
        service.cadastrarUsuario(new Usuario(2L, "Bruno", "bruno@email.com"));

        System.out.println("=== Livros ===");
        service.listarLivros().forEach(System.out::println);

        LocalDate hoje = LocalDate.of(2026, 11, 1);
        Emprestimo e1 = service.emprestar(1L, 1L, hoje);
        System.out.println("\nEmprestado: " + e1);

        System.out.println("\n=== Testando regras ===");
        tentar("Emprestar livro ja emprestado", () -> service.emprestar(1L, 2L, hoje));
        tentar("Emprestar livro inexistente", () -> service.emprestar(99L, 1L, hoje));
        tentar("Cadastrar livro sem titulo", () -> service.cadastrarLivro(new Livro(4L, " ", "X", 2020, Genero.HISTORIA)));

        LocalDate devolucao = hoje.plusDays(20);
        service.devolver(e1.id(), devolucao);
        System.out.println("\nDevolvido: " + e1);
        System.out.println("Dias de atraso: " + e1.diasDeAtraso(devolucao));
        tentar("Devolver de novo", () -> service.devolver(e1.id(), devolucao));
    }

    /**
     * CONCEITO: lambda + interface funcional (Runnable).
     * Passamos um "pedaco de codigo" como parametro e executamos dentro do try/catch.
     * Repare na ORDEM dos catch: do mais especifico para o mais generico.
     */
    private static void tentar(String descricao, Runnable acao) {
        try {
            acao.run();
            System.out.println("[OK]    " + descricao);
        } catch (RecursoNaoEncontradoException | RegraDeNegocioException e) {
            System.out.println("[NEGOCIO] " + descricao + " -> " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[INVALIDO] " + descricao + " -> " + e.getMessage());
        }
    }
}
