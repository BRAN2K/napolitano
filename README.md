# Napolitano

Mod para **Minecraft Java Edition 26.2**, feito com **Fabric** e **Kotlin**.

O mod atualmente adiciona o bloco `napolitano:ruby_block`, com:

- textura e modelo próprios;
- nome traduzido;
- item e entrada no inventário criativo;
- drop ao ser quebrado.

## Requisitos

- JDK 25
- Visual Studio Code
- Extensão **Extension Pack for Java**
- Extensão **Kotlin by JetBrains**

O projeto já inclui o Gradle Wrapper. Não é necessário instalar o Gradle separadamente.

> A versão do Minecraft, Fabric API e Kotlin deve ser mantida igual à configurada em `gradle.properties`. O código deste projeto não deve ser misturado com outra versão sem adaptação.

## Como testar

Clone o repositório e abra a pasta no VS Code:

```bash
git clone https://github.com/BRAN2K/napolitano.git
cd napolitano
code .
```

No terminal integrado do VS Code, execute:

```powershell
.\gradlew.bat runClient
```

No Linux ou macOS:

```bash
./gradlew runClient
```

O cliente de desenvolvimento do Minecraft será aberto. No modo Criativo, procure **Ruby Block** em **Building Blocks**. Também é possível testar com:

```text
/give @s napolitano:ruby_block
```

Para depurar pelo VS Code, gere os *launch targets* com:

```powershell
.\gradlew.bat vscode
```

Depois abra **Run and Debug** e pressione `F5`.

## Build

```powershell
.\gradlew.bat build
```

O JAR será criado em:

```text
build/libs/
```

## Planejamento

As decisões para futuras melhorias do bloco estão documentadas em [docs/adr/README.md](docs/adr/README.md).

## Licença

Consulte [LICENSE](LICENSE).
