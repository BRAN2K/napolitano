# ADR-0003: Usar som de ametista ao andar sobre o bloco

- **Status:** Proposed
- **Data:** 2026-09-23
- **Escopo:** `napolitano:ruby_block`
- **Tipo:** Audio / block properties

## Contexto

O bloco usa atualmente `SoundType.STONE`, portanto andar, colocar, quebrar e atingir o bloco usam sons de pedra. A tarefa exige somente um som diferente ao andar sobre `napolitano:ruby_block`, usando o som vanilla de passo de bloco de ametista.

## Decisão

Criar um `SoundType` específico para `ruby_block`. Seu campo de passo usará `SoundEvents.AMETHYST_BLOCK_STEP`; os campos de breaking, placing, hit e fall continuarão usando os sons vanilla de pedra. Não será necessário registrar um `SoundEvent` nem incluir arquivos de áudio próprios.

A ideia é manter a alteração localizada e não substituir globalmente `SoundType.STONE` para todos os blocos.

### Arquivos previstos

```text
src/main/kotlin/com/example/napolitano/ModBlocks.kt
```

## Configuração do `SoundType`

O tipo deve seguir este desenho, adaptando os imports e a localização da propriedade à versão-alvo:

```kotlin
val RUBY_BLOCK_SOUND_TYPE = SoundType(
    1.0F,
    1.0F,
    SoundEvents.STONE_BREAK,
    SoundEvents.AMETHYST_BLOCK_STEP,
    SoundEvents.STONE_PLACE,
    SoundEvents.STONE_HIT,
    SoundEvents.STONE_FALL
)
```

E as propriedades do bloco devem usar:

```kotlin
.sound(RUBY_BLOCK_SOUND_TYPE)
```

## Critérios de aceitação

- Andar sobre o bloco reproduz o som vanilla de passo de bloco de ametista (`SoundEvents.AMETHYST_BLOCK_STEP`).
- Quebrar, colocar, atingir e cair continuam usando os sons vanilla de pedra.
- Não há registro de `SoundEvent` customizado nem erro de som ausente no log.
- O som funciona em cliente e servidor integrado.
- A tarefa não depende de 0001, 0002 ou 0004.

## Fora do escopo

- Sons customizados de colocar, quebrar, atingir ou cair.
- Sons condicionais por bioma, hora ou estado do bloco.
- Mixins de áudio.

## Riscos e investigação

A API e o construtor de `SoundType`, assim como o nome da constante vanilla de passo de ametista, são sensíveis à versão do Minecraft. Antes de implementar, confirmar `SoundEvents.AMETHYST_BLOCK_STEP` no código gerado para Minecraft 26.2.

## Referências

- [Minecraft Wiki: sounds.json](https://minecraft.wiki/w/Sounds.json)
