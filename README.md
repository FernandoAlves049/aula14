# Sistema de Indexação de Dados - Banco de Produtos

## 📋 Descrição

Este projeto implementa um sistema de indexação de dados para um banco de produtos utilizando duas estruturas de dados avançadas:
- **Árvore B+** (ordem 3)
- **Árvore B*** (ordem 3)

O sistema carrega dados de produtos de um arquivo CSV e realiza operações de inserção, busca e remoção, medindo o desempenho de cada estrutura.

## 🏗️ Estrutura do Projeto

```
aula14/
├── src/
│   ├── App.java              # Classe principal com testes
│   ├── Produto.java          # Classe modelo do produto
│   ├── BPlusTree.java        # Implementação da Árvore B+
│   ├── BStarTree.java        # Implementação da Árvore B*
│   └── produtos_corrigido.txt # Base de dados (500 produtos)
├── bin/                      # Arquivos compilados
└── README.md                 # Este arquivo
```

## 📊 Base de Dados

O arquivo `produtos_corrigido.txt` contém **600 produtos** com as seguintes informações:
- **ID**: Identificador único (1001-1600)
- **Nome**: Nome do produto
- **Categoria**: Categoria do produto

> **🆕 Atualização**: A base de dados foi expandida de 500 para 600 produtos para melhor teste de performance das estruturas de dados. Os novos produtos incluem equipamentos gaming, componentes de PC avançados, dispositivos de networking, ferramentas de desenvolvimento e acessórios especializados.

**Formato**: `ID,Nome,Categoria`

**Exemplo**:
```
1001,Smart TV,Acessórios
1002,Smartphone,Armazenamento
1003,Placa de Vídeo,Áudio
```

## 🌳 Estruturas de Dados Implementadas

### Árvore B+ (B-Plus Tree)
- **Ordem**: 3
- **Características**:
  - Todas as chaves ficam nas folhas
  - Nós internos servem apenas como índice
  - Folhas são conectadas em lista ligada
  - Ótima para consultas sequenciais

### Árvore B* (B-Star Tree)
- **Ordem**: 3
- **Características**:
  - Utilização mínima de 2/3 dos nós
  - Redistribuição antes da divisão
  - Melhor aproveitamento do espaço
  - Menos divisões de nós

## 🚀 Como Executar

### Pré-requisitos
- Java 8 ou superior
- Compilador javac

### Compilação
```bash
cd aula14
javac -d bin src/*.java
```

### Execução
```bash
java -cp bin App
```

### Execução com Saída em Arquivo
```bash
cd e:\java\aula14 && java -cp bin App > resultado.txt 2>&1
```

> **Nota**: O comando acima executa o programa e salva toda a saída (incluindo erros) no arquivo `resultado.txt`

## 📈 Funcionalidades

### 1. Carregamento de Dados
- Leitura do arquivo CSV
- Parsing e criação de objetos Produto
- Inserção em ambas as árvores

### 2. Teste de Performance
- **Inserção**: Medição do tempo para inserir todos os **600 produtos**
- **Busca e Remoção**: Teste com 10 chaves aleatórias entre 1000-2000

### 3. Relatório de Resultados
O programa gera um relatório detalhado com:
- Tempo de inserção para cada árvore
- Lista de produtos encontrados/removidos
- Estatísticas de performance
- Comparação entre as estruturas

## 📊 Exemplo de Saída

```
╔══════════════════════════════════════════════════════════════╗
║           SISTEMA DE INDEXAÇÃO - BANCO DE PRODUTOS          ║
╚══════════════════════════════════════════════════════════════╝

📦 Total de produtos carregados: 600

┌──────────────────────────────────────────────────────────────┐
│                    ÁRVORE B+ (Ordem 3)                      │
└──────────────────────────────────────────────────────────────┘

⏱️  INSERÇÃO DOS DADOS
⏰ Tempo de inserção: 3,15 ms
📊 Produtos inseridos: 600

🔍 TESTE DE BUSCA E REMOÇÃO
🎲 Chaves sorteadas: [1032, 1406, 1170, ...]

✅ Produto encontrado - ID: 1032, Nome: Tablet, Categoria: Áudio
   ✓ Produto removido com sucesso

❌ Produto com ID 1686 não foi encontrado

📈 RESUMO DOS TESTES
⏰ Tempo total de remoção: 2,99 ms
✅ Produtos removidos: 2
❌ Produtos não encontrados: 8
📊 Tempo médio por operação: 0,30 ms
```

## 🔧 Classes Principais

### Produto
```java
public class Produto {
    private int id;
    private String nome;
    private String categoria;
    // ... métodos
}
```

### BPlusTree
- Implementa árvore B+ com nós internos e folhas
- Suporte para inserção, busca e remoção
- Divisão automática de nós quando necessário

### BStarTree
- Implementa árvore B* com redistribuição
- Maior eficiência de espaço
- Menos operações de divisão

## 📝 Características Técnicas

- **Ordem das Árvores**: 3
- **Máximo de chaves por nó**: 5 (2 * ordem - 1)
- **Linguagem**: Java 8+
- **Estrutura**: Orientada a objetos
- **Medição de tempo**: Nanossegundos (convertido para ms)

## 🎯 Objetivos do Projeto

1. ✅ Implementar árvores B+ e B* de ordem 3
2. ✅ Carregar dados de arquivo CSV (600 produtos)
3. ✅ Medir tempo de inserção de todos os dados
4. ✅ Sortear 10 produtos aleatórios (1000-2000)
5. ✅ Realizar busca e remoção dos produtos
6. ✅ Calcular tempos de operação
7. ✅ Comparar performance entre as estruturas

## 📚 Conceitos Demonstrados

- Estruturas de dados avançadas
- Árvores balanceadas
- Operações de inserção, busca e remoção
- Análise de performance
- Manipulação de arquivos em Java
- Programação orientada a objetos

## 🔍 Análise de Complexidade

### Árvore B+
- **Inserção**: O(log n)
- **Busca**: O(log n)
- **Remoção**: O(log n)
- **Espaço**: O(n)

### Árvore B*
- **Inserção**: O(log n) com menos divisões
- **Busca**: O(log n)
- **Remoção**: O(log n)
- **Espaço**: O(n) com melhor utilização

## 👨‍💻 Autor

**Fernando Alves de Souza**

Projeto desenvolvido como exercício prático de estruturas de dados avançadas.

---
*Implementação acadêmica - Estruturas de Dados II*
