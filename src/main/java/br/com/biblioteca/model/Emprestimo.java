package br.com.biblioteca.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * CONCEITO: classe comum x record.
 * Emprestimo MUDA de estado (ATIVO -> DEVOLVIDO), entao aqui usamos uma classe normal,
 * com ENCAPSULAMENTO: campos private e a mudanca de estado so acontece pelo metodo devolver(),
 * que garante as regras. Ninguem de fora consegue fazer "status = DEVOLVIDO" na mao.
 */
public class Emprestimo implements Identificavel {

    public static final int PRAZO_DIAS = 14;

    private final Long id;
    private final Livro livro;
    private final Usuario usuario;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

    public Emprestimo(Long id, Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        this.id = Objects.requireNonNull(id, "id");
        this.livro = Objects.requireNonNull(livro, "livro");
        this.usuario = Objects.requireNonNull(usuario, "usuario");
        this.dataEmprestimo = Objects.requireNonNull(dataEmprestimo, "dataEmprestimo");
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(PRAZO_DIAS);
        this.status = StatusEmprestimo.ATIVO;
    }

    public void devolver(LocalDate data) {
        if (status == StatusEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Emprestimo " + id + " ja foi devolvido");
        }
        if (data.isBefore(dataEmprestimo)) {
            throw new IllegalArgumentException("Data de devolucao anterior ao emprestimo");
        }
        this.dataDevolucao = data;
        this.status = StatusEmprestimo.DEVOLVIDO;
    }

    public boolean estaAtivo() {
        return status == StatusEmprestimo.ATIVO;
    }

    public boolean estaAtrasado(LocalDate hoje) {
        return estaAtivo() && hoje.isAfter(dataPrevistaDevolucao);
    }

    public long diasDeAtraso(LocalDate referencia) {
        LocalDate fim = (dataDevolucao != null) ? dataDevolucao : referencia;
        long dias = ChronoUnit.DAYS.between(dataPrevistaDevolucao, fim);
        return Math.max(0, dias);
    }

    // TODO EXERCICIO 2: implemente calcularMulta(LocalDate referencia)
    //   Regra: R$ 2,00 por dia de atraso, multa maxima de R$ 50,00.
    //   Dica: use BigDecimal (nunca double para dinheiro!) e reaproveite diasDeAtraso().

    @Override
    public Long id() {
        return id;
    }

    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public StatusEmprestimo getStatus() { return status; }

    /**
     * CONCEITO: equals/hashCode.
     * Duas instancias representam o MESMO emprestimo se tem o mesmo id.
     * Regra de ouro: sobrescreveu equals, sobrescreva hashCode (senao HashSet/HashMap quebram).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emprestimo outro)) return false; // pattern matching (Java 16+)
        return id.equals(outro.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Emprestimo#%d [%s -> %s, %s, devolver ate %s]"
                .formatted(id, livro.titulo(), usuario.nome(), status, dataPrevistaDevolucao);
    }
}
