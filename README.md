# Boletim da turma

Script que calcula a média de cada aluno a partir de três notas
e informa a situação final:

- Média >= 7.0: Aprovado
- Média >= 5.0: Recuperação
- Abaixo disso: Reprovado

## Pré-requisitos

- Java 17 ou superior instalado (confira com `java -version`)

Não é preciso instalar o Gradle nem o Groovy: o projeto usa o Gradle Wrapper,
que baixa tudo automaticamente na primeira execução.

## Como executar

Na pasta do projeto, rode:

Linux / macOS:

    ./gradlew run -q

Windows:

    gradlew.bat run -q

A opção `-q` esconde as mensagens do Gradle e mostra apenas a saída do programa.
A primeira execução pode demorar um pouco, pois as dependências serão baixadas.

> Se aparecer "Permission denied" no Linux/macOS, rode `chmod +x gradlew` e tente novamente.

## Estrutura do projeto

    cacandoBugMediaEscolar/
    ├── build.gradle                     # configuração do build
    ├── settings.gradle                  # nome do projeto
    ├── gradlew / gradlew.bat            # Gradle Wrapper
    └── src/main/groovy/Boletim.groovy   # código do boletim

## Problema relatado

Os professores conferiram as notas manualmente e o resultado
esperado é:

    Ana: média 8.17 - Aprovado
    Bruno: média 6.00 - Recuperação
    Carla: média 7.00 - Aprovado
    Diego: média 7.00 - Aprovado

Mas o script está mostrando valores diferentes.

## Seu desafio

Existem **pelo menos 3 bugs** escondidos no `Boletim.groovy`.
Seu objetivo é encontrar e corrigir todos, com a ajuda de um agente de IA,
até que a saída do script fique **idêntica** ao resultado esperado acima.

Os bugs se escondem uns atrás dos outros: corrigir um deles pode fazer
aparecer um problema que antes não dava para ver. Por isso, não pare no
primeiro acerto.

### Dicas para caçar os bugs

- **Rode o script depois de cada correção** e compare a saída, linha por
  linha, com o resultado esperado. Se ainda houver diferença, ainda há bug.
- **Um código sem erros não é um código correto.** O programa pode rodar
  sem nenhuma mensagem de erro e ainda assim seguir uma regra diferente da
  que a escola definiu.
- **Dê contexto para a IA.** Ela só sabe o que você mostra a ela. Compare
  as respostas quando você envia apenas o código e quando envia também as
  regras da escola e o resultado esperado descritos neste README.
- **Preste atenção nos casos de fronteira.** Pense no que deveria acontecer
  com um aluno que tira exatamente a nota de corte.
- **Entenda antes de aceitar.** Peça para a IA explicar por que cada linha
  estava errada. Se a explicação não fizer sentido para você, pergunte de
  novo.

### Checklist

- [ ] A média de todos os alunos está igual à esperada
- [ ] A situação de todos os alunos está igual à esperada
- [ ] Consigo explicar, com minhas palavras, cada bug que corrigi

## Sobre os bugs resolvidos

### 1. Índice do loop da média

**Problema original:** Na função `calcularMedia()`, o loop `for` iniciava com `i = 1`, pulando a primeira nota de cada aluno ao calcular a soma. Isso resultava em médias incorretas para todos os alunos.

**Correção aplicada:** O índice inicial do loop foi alterado de `i = 1` para `i = 0`, garantindo que todas as notas sejam somadas corretamente antes de dividir pelo total.

### 2. Operador da condição de aprovação

**Problema original:** Na função `situacao()`, a verificação de aprovação utilizava o operador `>` (`media > mediaAprovacao`). Consequentemente, um aluno com média exatamente igual a 7.0 não era aprovado, pois `7.0 > 7.0` é falso.

**Correção aplicada:** O operador foi alterado de `>` para `>=` (`media >= mediaAprovacao`), aprovando corretamente alunos com média igual ou superior a 7.0, conforme as regras da escola.

### 3. Valor da constante de recuperação

**Problema original:** A constante `MEDIA_RECUPERACAO` estava definida como `6.5`, enquanto as regras da escola estabelecem que o limite para recuperação é `5.0`. Isso classificava incorretamente alunos cujas médias estavam entre 5.0 e 6.5.

**Correção aplicada:** O valor da constante foi ajustado de `6.5` para `5.0`, alinhando o código com as regras definidas no README.

Autor: Michel Lavanere Sampaio
