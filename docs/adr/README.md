# Guia rápido de ADR

Uma ADR registra uma decisão técnica: **o problema, a escolha, o motivo e como verificá-la**.

## Como criar

1. Defina um problema específico.
2. Avalie alternativas.
3. Escolha uma única decisão.
4. Crie `docs/adr/0000-nome-da-decisao.md`.
5. Escreva o contexto, a decisão, o escopo e os testes.
6. Revise antes de alterar o código.
7. Implemente apenas o que foi decidido.
8. Teste e atualize o status.

## Template mínimo

```markdown
# ADR-0000: Título

- **Status:** Proposed
- **Data:** AAAA-MM-DD

## Contexto
Qual problema existe?

## Decisão
O que foi escolhido?

## Alternativas
Quais outras opções foram descartadas?

## Implementação
Quais arquivos serão alterados?

## Aceitação
Como testar o resultado?

## Fora do escopo
O que não será feito?

## Dependências
Existe alguma? A ADR pode ser feita sozinha?

## Rollback
Como desfazer a alteração?
```

## Status possíveis

- `Proposed`: criada e aguardando revisão.
- `Accepted`: aprovada para implementação.
- `Implemented`: implementada e testada.
- `Superseded`: substituída por outra ADR.
- `Rejected`: não escolhida.

## Regras

- Uma tarefa independente deve ter uma ADR independente.
- Não misture decisões diferentes no mesmo documento.
- Não altere o escopo durante a implementação sem atualizar a ADR.
- Não faça commit, `git add`, push ou operações de histórico sem autorização explícita.
