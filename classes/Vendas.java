package classes;

import java.time.LocalDate;

public class Vendas {

    private LocalDate dataVenda;
    private Produtos produtoVendido;
    private int quantidadeVendida;

    public Vendas(LocalDate dataVenda, Produtos produtoVendido, int quantidadeVendida) {
        this.dataVenda = dataVenda;
        this.produtoVendido = produtoVendido;
        this.quantidadeVendida = quantidadeVendida;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Produtos getProdutoVendido() {
        return produtoVendido;
    }

    public void setProdutoVendido(Produtos produtoVendido) {
        this.produtoVendido = produtoVendido;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public double getValorTotal() {
        return produtoVendido.getValor() * quantidadeVendida;
    }
}
