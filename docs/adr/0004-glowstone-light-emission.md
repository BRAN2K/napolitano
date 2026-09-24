# ADR-0004: Emissão de luz no nível 15

- **Status:** Proposed
- **Data:** 2026-09-23
- **Escopo:** `napolitano:ruby_block`
- **Tipo:** Lighting / gameplay

## Contexto

O bloco é um `Block` comum e não possui BlockEntity. A solicitação é que ele emita luz como a Glowstone, sem introduzir um sistema de estados ou lógica de atualização.

## Decisão

Definir a emissão de luz diretamente nas propriedades do bloco, com nível 15:

```kotlin
BlockBehaviour.Properties.of()
    .sound(SoundType.STONE)
    .lightLevel { 15 }
```

O nível 15 é o nível máximo usado pela Glowstone no vanilla. A luz será estática: o bloco emite luz enquanto existir e para de emitir quando for removido.

Não será criado um renderer, BlockEntity, tick ou mixin.

## Arquivos afetados

```text
src/main/kotlin/com/example/napolitano/ModBlocks.kt
```

A alteração deve preservar todas as outras propriedades que já existam na cadeia.

## Critérios de aceitação

- Colocar `napolitano:ruby_block` em uma área escura ilumina os blocos ao redor.
- O nível de luz do bloco é 15.
- Remover o bloco remove a emissão imediatamente.
- A propagação funciona no servidor integrado e em um mundo com um segundo cliente, se o teste multiplayer for executado.
- Não há ticks, erros de BlockEntity ou dependências client-side.
- A tarefa pode ser implementada e testada sem 0001, 0002 ou 0003.

## Fora do escopo

- Luz Liga/desliga com redstone.
- Luz com intensidade variável.
- Cor ou luz emissora por entidade.
- BlockEntity ou modelo customizado.
- Alteração na propagação de luz vanilla.

Uma luz condicional deve ser definida em uma ADR futura, pois exigiria um `BlockState` ou uma classe de bloco com lógica própria.

## Riscos e rollback

O maior risco é usar um valor diferente de 15 sem жела-lo. O rollback consiste em remover somente `.lightLevel { 15 }`; nenhuma alteração de modelo, som ou drop é necessária.

## Referências

- [Fabric: Creating Your First Block](https://docs.fabricmc.net/develop/blocks/first-block)
- [Minecraft Wiki: Light](https://minecraft.wiki/w/Light)
