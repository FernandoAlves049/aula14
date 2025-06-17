import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        imprimirCabecalho();
        
        // Carregar dados do arquivo
        List<Produto> produtos = carregarProdutos("produtos_corrigido.txt");
        imprimirEstatisticasCarregamento(produtos.size());
        
        // Testar Árvore B+
        imprimirCabecalhoTeste("ÁRVORE B+", 3);
        testarArvore(produtos, new BPlusTree(), "B+");
        
        imprimirSeparador();
        
        // Testar Árvore B*
        imprimirCabecalhoTeste("ÁRVORE B*", 3);
        testarArvore(produtos, new BStarTree(), "B*");
        
        imprimirRodape();
    }
    
    private static void imprimirCabecalho() {
        System.out.println("╔" + "═".repeat(68) + "╗");
        System.out.println("║" + centralizar("SISTEMA DE INDEXAÇÃO DE DADOS", 68) + "║");
        System.out.println("║" + centralizar("BANCO DE PRODUTOS", 68) + "║");
        System.out.println("╚" + "═".repeat(68) + "╝");
        System.out.println();
    }
    
    private static void imprimirEstatisticasCarregamento(int totalProdutos) {
        System.out.println("📊 CARREGAMENTO DE DADOS");
        System.out.println("┌" + "─".repeat(35) + "┐");
        System.out.printf("│ Total de produtos: %13d │%n", totalProdutos);
        System.out.printf("│ Status: %23s │%n", totalProdutos > 0 ? "✅ Sucesso" : "❌ Falha");
        System.out.println("└" + "─".repeat(35) + "┘");
        System.out.println();
    }
    
    private static void imprimirCabecalhoTeste(String tipoArvore, int ordem) {
        String titulo = String.format("TESTE %s (Ordem %d)", tipoArvore, ordem);
        System.out.println("🌳 " + titulo);
        System.out.println("═".repeat(titulo.length() + 3));
    }
    
    private static void imprimirSeparador() {
        System.out.println();
        System.out.println("╟" + "─".repeat(68) + "╢");
        System.out.println();
    }
    
    private static void imprimirRodape() {
        System.out.println();
        System.out.println("╔" + "═".repeat(68) + "╗");
        System.out.println("║" + centralizar("TESTES CONCLUÍDOS COM SUCESSO", 68) + "║");
        System.out.println("╚" + "═".repeat(68) + "╝");
    }
    
    private static String centralizar(String texto, int largura) {
        int espacos = (largura - texto.length()) / 2;
        return " ".repeat(espacos) + texto + " ".repeat(largura - texto.length() - espacos);
    }
    
    private static List<Produto> carregarProdutos(String nomeArquivo) {
        List<Produto> produtos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String categoria = partes[2];
                    produtos.add(new Produto(id, nome, categoria));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
        
        return produtos;
    }
      private static void testarArvore(List<Produto> produtos, Object arvore, String tipoArvore) {
        // Inserção dos dados
        System.out.println("\n📝 1. INSERÇÃO DOS DADOS");
        System.out.println("┌" + "─".repeat(50) + "┐");
        
        long inicioInsercao = System.nanoTime();
        
        for (Produto produto : produtos) {
            if (arvore instanceof BPlusTree) {
                ((BPlusTree) arvore).insert(produto.getId(), produto);
            } else if (arvore instanceof BStarTree) {
                ((BStarTree) arvore).insert(produto.getId(), produto);
            }
        }
        
        long fimInsercao = System.nanoTime();
        double tempoInsercao = (fimInsercao - inicioInsercao) / 1_000_000.0; // ms
        
        System.out.printf("│ ⏱️  Tempo de inserção: %18.2f ms │%n", tempoInsercao);
        System.out.printf("│ 📦 Produtos inseridos: %18d    │%n", produtos.size());
        System.out.println("└" + "─".repeat(50) + "┘");
        
        // Teste de busca e remoção
        System.out.println("\n🔍 2. TESTE DE BUSCA E REMOÇÃO");
        List<Integer> chavesTeste = gerarChavesAleatorias(10, 1000, 2000);
        
        System.out.println("🎲 Chaves sorteadas: " + chavesTeste);
        System.out.println();
        
        long inicioRemocao = System.nanoTime();
        int removidos = 0;
        int naoEncontrados = 0;
        
        for (int chave : chavesTeste) {
            Produto produto = null;
            
            // Buscar o produto
            if (arvore instanceof BPlusTree) {
                produto = ((BPlusTree) arvore).search(chave);
            } else if (arvore instanceof BStarTree) {
                produto = ((BStarTree) arvore).search(chave);
            }
            
            if (produto != null) {
                System.out.printf("✅ Produto encontrado - ID: %d%n", produto.getId());
                System.out.printf("   📋 Nome: %s | 🏷️  Categoria: %s%n", 
                    produto.getNome(), produto.getCategoria());
                
                // Remover o produto
                boolean removed = false;
                if (arvore instanceof BPlusTree) {
                    removed = ((BPlusTree) arvore).remove(chave);
                } else if (arvore instanceof BStarTree) {
                    removed = ((BStarTree) arvore).remove(chave);
                }
                
                if (removed) {
                    System.out.printf("   🗑️  Produto ID %d removido com sucesso%n", chave);
                    removidos++;
                } else {
                    System.out.printf("   ❌ Erro ao remover produto ID %d%n", chave);
                }
            } else {
                System.out.printf("❓ Produto com ID %d não foi encontrado%n", chave);
                naoEncontrados++;
            }
            System.out.println();
        }
        
        long fimRemocao = System.nanoTime();
        double tempoRemocao = (fimRemocao - inicioRemocao) / 1_000_000.0; // ms
        
        // Resumo estilizado
        System.out.println("📊 3. RESUMO DOS TESTES");
        System.out.println("┌" + "─".repeat(58) + "┐");
        System.out.printf("│ ⏱️  Tempo total de remoção: %24.2f ms │%n", tempoRemocao);
        System.out.printf("│ ✅ Produtos encontrados e removidos: %16d │%n", removidos);
        System.out.printf("│ ❓ Produtos não encontrados: %23d │%n", naoEncontrados);
        System.out.printf("│ ⚡ Tempo médio por operação: %20.2f ms │%n", 
            tempoRemocao / chavesTeste.size());
        System.out.printf("│ 🎯 Taxa de sucesso: %29.1f%% │%n", 
            (removidos * 100.0) / chavesTeste.size());
        System.out.println("└" + "─".repeat(58) + "┘");
    }
    
    private static List<Integer> gerarChavesAleatorias(int quantidade, int min, int max) {
        List<Integer> chaves = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < quantidade; i++) {
            int chave = random.nextInt(max - min + 1) + min;
            chaves.add(chave);
        }
        
        return chaves;
    }
}
