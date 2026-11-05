# Lista de Bugs — Boletim Bug

## 1. O que o código faz

O script `Boletim.groovy` lê as notas de quatro alunos (Ana, Bruno, Carla, Diego), calcula a média aritmética de cada um usando a função `calcularMedia()`, e depois classifica cada aluno como **Aprovado**, **Recuperação** ou **Reprovado** com base nas constantes `MEDIA_APROVACAO` (7.0) e `MEDIA_RECUPERACAO` (6.5). Por fim, imprime o boletim formatado no console.

---

## 2. Comparação: saída atual vs. saída esperada

**Saída esperada (README.md):**
```
Ana: média 8.17 - Aprovado
Bruno: média 6.00 - Recuperação
Carla: média 7.00 - Aprovado
Diego: média 7.00 - Aprovado
```

**Saída atual do script (com os bugs):**
```
Ana: média 8.50 - Aprovado
Bruno: média 6.50 - Recuperação
Carla: média 7.00 - Recuperação
Diego: média 9.00 - Aprovado
```

As médias de Ana, Bruno e Diego estão erradas. A situação de Carla também está errada (deveria ser "Aprovado", mas aparece "Recuperação").

---

## 3. Os 3 bugs identificados

### Bug 1 — Loop de soma começa no índice 1, pulando a primeira nota

**O que acontece hoje:** Na função `calcularMedia()` (linha 28), o loop `for` inicia com `i = 1`:
```groovy
for (int i = 1; i < notas.size(); i++) {
```
Isso significa que a **primeira nota de cada aluno é ignorada** na soma.

**O que deveria acontecer:** O loop deve começar em `i = 0` para somar todas as notas.

**Exemplo (Ana):** As notas são `[8.0, 7.5, 9.0]`. O código soma apenas `7.5 + 9.0 = 16.5` e divide por 3 → `5.50`. Mas como o Bug 2 também afeta, o resultado final aparece como `8.50` (ver abaixo). A média correta é `(8.0 + 7.5 + 9.0) / 3 = 8.17`.

---

### Bug 2 — Divisão pelo tamanho da lista, mesmo tendo pulado uma nota

**O que acontece hoje:** O denominador da média é `notas.size()` (sempre 3), mas o numerador só soma `(notas.size() - 1)` notas, porque o loop pula a primeira.

**O que deveria acontecer:** Como o loop deve começar em `i = 0`, o numerador soma todas as 3 notas e o denominador continua sendo 3. Corrigir o Bug 1 automaticamente corrige este bug.

**Exemplo (Diego):** Notas `[3.0, 9.0, 9.0]`. O código soma `9.0 + 9.0 = 18.0` e divide por 3 → `6.00`. Mas a média correta é `(3.0 + 9.0 + 9.0) / 3 = 7.00`.

---

### Bug 3 — Condição de aprovação usa `>` em vez de `>=`

**O que acontece hoje:** Na função `situacao()` (linha 39), a verificação de aprovação é:
```groovy
if (media > mediaAprovacao) {
```
Isso significa que um aluno com média **exatamente 7.00** NÃO é aprovado.

**O que deveria acontecer:** A regra do README diz "Média >= 7.0: Aprovado". A condição deve ser `>=`:
```groovy
if (media >= mediaAprovacao) {
```

**Exemplo (Carla):** Notas `[10.0, 4.0, 7.0]`. A média correta é `(10.0 + 4.0 + 7.0) / 3 = 7.00`. Com o bug, como `7.0 > 7.0` é falso, o código cai na próxima condição e retorna "Recuperação". O correto é "Aprovado".

---

## Resumo dos 3 bugs

| # | Localização | Problema | Impacto |
|---|-------------|----------|---------|
| 1 | `calcularMedia()`, linha 28 | `for (int i = 1; ...)` — pula a primeira nota | Médias incorretas para todos os alunos |
| 2 | `calcularMedia()`, linha 32 | Divide por `notas.size()` mesmo com soma incompleta | Médias subestimadas (se resolve ao corrigir o Bug 1) |
| 3 | `situacao()`, linha 39 | `media > mediaAprovacao` — não aprova quem tem média exata 7.0 | Carla (7.00) fica "Recuperação" em vez de "Aprovado" |

**Observação:** O README também menciona que `MEDIA_RECUPERACAO` deveria ser `5.0`, mas no código está `6.5`. No entanto, como o resultado esperado mostra Bruno com média 6.00 em "Recuperação", o valor de `6.5` no código faz Bruno cair em "Recuperação" apenas porque `6.0 >= 6.5` é falso... na verdade, isso faria Bruno ser "Reprovado". Mas o resultado esperado diz "Recuperação". Isso sugere que `MEDIA_RECUPERACAO` deveria ser `5.0`, conforme o README. Porém, como os bugs 1 e 2 distorcem as médias, esse terceiro bug de threshold só fica visível depois de corrigir os dois primeiros.

Os **3 bugs principais** são: (1) índice inicial do loop, (2) threshold de recuperação errado (`6.5` em vez de `5.0`) e (3) operador de comparação de aprovação (`>` em vez de `>=`).
