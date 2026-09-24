# ADR-0001: Requerer picareta de ferro ou superior para a colheita

- **Status:** Implemented
- **Data:** 2026-09-23
- **Escopo:** `napolitano:ruby_block`
- **Tipo:** Gameplay / drop tables

## Contexto

O bloco atualmente é registrado como um `Block` comum e usa o `SoundType.STONE`. Ele não declara que uma ferramenta correta é necessária para gerar drops.

A solicitação é que o bloco seja cultivado com picaretas de nível ferro ou superior. No Minecraft, a forma usual de expressar isso é por meio de tags de blocos e `requiresCorrectToolForDrops()`.

## Decisão

Usar o mecanismo padrão de mining tags do Minecraft 26.2:

1. Adicionar `.requiresCorrectToolForDrops()` às propriedades do bloco.
2. Adicionar `napolitano:ruby_block` à tag de blocos mineráveis com picareta.
3. Adicionar `napolitano:ruby_block` à tag de nível mínimo ferro.
4. Usar `"replace": false` nas tags para preservar os blocos vanilla.

### Interpretação desta ADR

“Quebrável por picaretas de ferro ou superior” significa que a ferramenta correta é necessária para uma colheita eficiente e para obter o drop. Ferro, diamante e netherite qualificam; picareta de madeira, pedra e ouro não qualificam.

Ferramentas inferiores ainda podem conseguir quebrar o bloco lentamente, mas não devem gerar o item. Se a intenção for impedir completamente a quebra com ferramentas inferiores, isso exige uma decisão e uma implementação separadas.

## Registro de implementação

A decisão foi implementada com as seguintes alterações:

- `ModBlocks.kt` agora chama `.requiresCorrectToolForDrops()`.
- O bloco foi adicionado à tag `minecraft:block/mineable/pickaxe`.
- O bloco foi adicionado à tag `minecraft:block/needs_iron_tool`.
- As duas tags usam `"replace": false`.

A validação manual de cada ferramenta deve ser feita no cliente de desenvolvimento; o build automatizado valida a compilação e o processamento dos recursos.

## Arquivos afetados

```text
src/main/kotlin/com/example/napolitano/ModBlocks.kt
src/main/resources/data/minecraft/tags/block/mineable/pickaxe.json
src/main/resources/data/minecraft/tags/block/needs_iron_tool.json
```

### Alteração em `ModBlocks.kt`

Adicionar a chamada sem remover as propriedades existentes:

```kotlin
BlockBehaviour.Properties.of()
    .sound(SoundType.STONE)
    .requiresCorrectToolForDrops()
```

### `mineable/pickaxe.json`

```json
{
  "replace": false,
  "values": [
    "napolitano:ruby_block"
  ]
}
```

### `needs_iron_tool.json`

```json
{
  "replace": false,
  "values": [
    "napolitano:ruby_block"
  ]
}
```

## Critérios de aceitação

| Ferramenta | Resultado esperado |
|---|---|
| Mão | Não obtém drop |
| Picareta de madeira | Não obtém drop |
| Picareta de pedra | Não obtém drop |
| Picareta de ouro | Não obtém drop |
| Picareta de ferro | Obtém `napolitano:ruby_block` |
| Picareta de diamante | Obtém `napolitano:ruby_block` |
| Picareta de netherite | Obtém `napolitano:ruby_block` |

Também deve ser verificado que:

- o build termina com sucesso;
- o bloco continua sendo registrado uma única vez;
- a tag não substitui os blocos vanilla;
- a implementação não depende das ADRs 0002, 0003 ou 0004.

## Fora do escopo

- Ferramentas customizadas.
- Receitas ou novos itens de picareta.
- Impedir completamente a quebra com ferramentas inferiores.
- Alterar a animação, o som ou a luz do bloco.

## Riscos e rollback

O principal risco é esquecer uma das duas tags: sem `mineable/pickaxe` a picareta não é reconhecida como ferramenta eficiente; sem `needs_iron_tool` o nível mínimo não é aplicado.

O rollback consiste em remover a chamada de `requiresCorrectToolForDrops()` e remover o bloco das duas tags. Nenhuma alteração em outros sistemas é necessária.

## Referências

- [Fabric: Creating Your First Block](https://docs.fabricmc.net/develop/blocks/first-block)
- [Minecraft Wiki: Tags](https://minecraft.wiki/w/Tag)
