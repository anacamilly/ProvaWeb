package ufrn.br.web.model;

public class Frutas {
    private int id;
    private String nome;
    private String descricao;
    private String cor;
    private int quantidade;
    private Float preco;

    	
	public Frutas(){

	}

    public Frutas(int id){
		this.id = id;
	}

    public Frutas(int id, String nome, String descricao, String cor, int quantidade, float preco){
		this.id = id;
		this.nome = nome;
        this.descricao = descricao;
		this.cor = cor;
        this.quantidade = quantidade;
		this.preco = preco;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

    public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}

    public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
}



