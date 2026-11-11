# Biblioteca Java

Projeto de estudos: sistema de controle de biblioteca em Java 21 puro (sem frameworks).
Base para a migração futura para Spring Boot.

## Como rodar

```bash
mvn compile exec:java
```

## Estrutura

```
src/main/java/br/com/biblioteca/
├── App.java                  -> ponto de entrada (main)
├── model/                    -> dominio: Livro, Usuario, Emprestimo, enums
├── exception/                -> excecoes de negocio
├── repository/               -> interface Repositorio + implementacao em memoria
└── service/                  -> regras de negocio
```

## Semana 1 — conceitos e onde encontrar cada um

| Conceito | Arquivo |
|---|---|
| `record` + construtor compacto (validação) | `model/Livro.java`, `model/Usuario.java` |
| `enum` com campo e construtor | `model/Genero.java` |
| Interface como contrato | `model/Identificavel.java`, `repository/Repositorio.java` |
| Generics (`<T extends Identificavel>`) | `repository/Repositorio.java` |
| Encapsulamento / estado mutável | `model/Emprestimo.java` |
| `equals` / `hashCode` / pattern matching `instanceof` | `model/Emprestimo.java` |
| Exceções unchecked customizadas | `exception/` |
| `Optional` + `orElseThrow` | `service/BibliotecaService.java` |
| Map (`LinkedHashMap`) | `repository/RepositorioEmMemoria.java` |
| Injeção de dependência pelo construtor | `service/BibliotecaService.java`, `App.java` |
| Lambda + `Runnable` + multi-catch | `App.java` |

## Exercícios da Semana 1 (procure por `TODO` no código)

- [ ] **1. Limite de empréstimos** — em `BibliotecaService.emprestar`: no máximo 3 empréstimos ATIVOS por usuário.
- [ ] **2. Multa por atraso** — em `Emprestimo`: `BigDecimal calcularMulta(LocalDate referencia)`, R$ 2,00/dia, teto de R$ 50,00.
- [ ] **3. Empréstimos atrasados** — em `BibliotecaService`: `List<Emprestimo> listarAtrasados(LocalDate hoje)`.
- [ ] **4. Busca por autor** — em `BibliotecaService`: `List<Livro> buscarPorAutor(String autor)`, ignorando maiúsculas/minúsculas.
- [ ] **5. Desafio** — crie um `record Endereco` e adicione ao `Usuario`. Valide o CEP no construtor compacto.
- [ ] **6. Mostre no `App`** — chame cada método novo e imprima o resultado.

### Perguntas para responder (escreva com suas palavras)
1. Por que `Livro` é `record` e `Emprestimo` é `class`?
2. O que aconteceria num `HashSet<Emprestimo>` se tirássemos o `hashCode()`?
3. Qual a diferença entre exceção *checked* e *unchecked*? Por que escolhemos *unchecked*?
4. Por que `listarTodos()` devolve uma cópia da lista?
5. Por que o `BibliotecaService` recebe `Repositorio` (interface) e não `RepositorioEmMemoria`?
