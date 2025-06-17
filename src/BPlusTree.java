import java.util.*;

public class BPlusTree {
    private static final int ORDER = 3; // Ordem da árvore
    private static final int MAX_KEYS = ORDER * 2 - 1;
    
    private Node root;
    
    public BPlusTree() {
        this.root = new LeafNode();
    }
    
    public void insert(int key, Produto produto) {
        if (root.isFull()) {
            InternalNode newRoot = new InternalNode();
            newRoot.children.add(root);
            root.split(newRoot, 0);
            root = newRoot;
        }
        root.insert(key, produto);
    }
    
    public Produto search(int key) {
        return root.search(key);
    }
    
    public boolean remove(int key) {
        boolean removed = root.remove(key);
        if (root instanceof InternalNode && root.keys.isEmpty()) {
            root = ((InternalNode) root).children.get(0);
        }
        return removed;
    }
    
    public void printTree() {
        root.print(0);
    }
    
    // Classe abstrata para nós
    abstract class Node {
        List<Integer> keys;
        
        public Node() {
            keys = new ArrayList<>();
        }
        
        abstract boolean isFull();
        abstract void insert(int key, Produto produto);
        abstract Produto search(int key);
        abstract boolean remove(int key);
        abstract void split(InternalNode parent, int index);
        abstract void print(int depth);
    }
    
    // Nó interno (não-folha)
    class InternalNode extends Node {
        List<Node> children;
        
        public InternalNode() {
            super();
            children = new ArrayList<>();
        }
        
        @Override
        boolean isFull() {
            return keys.size() >= MAX_KEYS;
        }
        
        @Override
        void insert(int key, Produto produto) {
            int index = Collections.binarySearch(keys, key);
            if (index < 0) {
                index = -index - 1;
            }
            
            Node child = children.get(index);
            if (child.isFull()) {
                child.split(this, index);
                if (key > keys.get(index)) {
                    index++;
                }
            }
            children.get(index).insert(key, produto);
        }
        
        @Override
        Produto search(int key) {
            int index = Collections.binarySearch(keys, key);
            if (index < 0) {
                index = -index - 1;
            }
            return children.get(index).search(key);
        }
        
        @Override
        boolean remove(int key) {
            int index = Collections.binarySearch(keys, key);
            if (index < 0) {
                index = -index - 1;
            }
            return children.get(index).remove(key);
        }
        
        @Override
        void split(InternalNode parent, int parentIndex) {
            InternalNode newNode = new InternalNode();
            int midIndex = keys.size() / 2;
            int midKey = keys.get(midIndex);
            
            // Move metade das chaves para o novo nó
            newNode.keys.addAll(keys.subList(midIndex + 1, keys.size()));
            newNode.children.addAll(children.subList(midIndex + 1, children.size()));
            
            // Remove as chaves e filhos movidos
            keys.subList(midIndex, keys.size()).clear();
            children.subList(midIndex + 1, children.size()).clear();
            
            // Adiciona a chave do meio ao pai
            parent.keys.add(parentIndex, midKey);
            parent.children.add(parentIndex + 1, newNode);
        }
        
        @Override
        void print(int depth) {
            String indent = "  ".repeat(depth);
            System.out.println(indent + "Internal: " + keys);
            for (Node child : children) {
                child.print(depth + 1);
            }
        }
    }
    
    // Nó folha
    class LeafNode extends Node {
        List<Produto> produtos;
        LeafNode next; // Ponteiro para o próximo nó folha
        
        public LeafNode() {
            super();
            produtos = new ArrayList<>();
        }
        
        @Override
        boolean isFull() {
            return keys.size() >= MAX_KEYS;
        }
        
        @Override
        void insert(int key, Produto produto) {
            int index = Collections.binarySearch(keys, key);
            if (index >= 0) {
                // Chave já existe, substitui o produto
                produtos.set(index, produto);
            } else {
                index = -index - 1;
                keys.add(index, key);
                produtos.add(index, produto);
            }
        }
        
        @Override
        Produto search(int key) {
            int index = Collections.binarySearch(keys, key);
            return index >= 0 ? produtos.get(index) : null;
        }
        
        @Override
        boolean remove(int key) {
            int index = Collections.binarySearch(keys, key);
            if (index >= 0) {
                keys.remove(index);
                produtos.remove(index);
                return true;
            }
            return false;
        }
        
        @Override
        void split(InternalNode parent, int parentIndex) {
            LeafNode newNode = new LeafNode();
            int midIndex = keys.size() / 2;
            
            // Move metade das chaves e produtos para o novo nó
            newNode.keys.addAll(keys.subList(midIndex, keys.size()));
            newNode.produtos.addAll(produtos.subList(midIndex, produtos.size()));
            
            // Remove as chaves e produtos movidos
            keys.subList(midIndex, keys.size()).clear();
            produtos.subList(midIndex, produtos.size()).clear();
            
            // Conecta os nós folha
            newNode.next = this.next;
            this.next = newNode;
            
            // Adiciona a primeira chave do novo nó ao pai
            parent.keys.add(parentIndex, newNode.keys.get(0));
            parent.children.add(parentIndex + 1, newNode);
        }
        
        @Override
        void print(int depth) {
            String indent = "  ".repeat(depth);
            System.out.println(indent + "Leaf: " + keys);
        }
    }
}
