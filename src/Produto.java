public class Produto {
    private int id;
    private String nome;
    private String categoria;
    
    public Produto(int id, String nome, String categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    @Override
    public String toString() {
        return String.format("Produto{id=%d, nome='%s', categoria='%s'}", id, nome, categoria);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return id == produto.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
