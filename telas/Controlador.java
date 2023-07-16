package telas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classes.Produtos;
import classes.Vendas;

public class Controlador {

    private List<Produtos> produtos;
    private List<Vendas> vendas;
    private Scanner scan;

    public Controlador() {
        this.produtos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.scan = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;
        
        while (opcao != 0) {
            limparTela();
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    limparTela();
                    incluirProduto();
                    break;
                case 2:
                    consultarProduto();
                    break;
                case 3:
                    listarProdutos();
                    break;
                case 4:
                    vendasPorPeriodo();
                    break;
                case 5:
                    realizarVenda();
                    break;
                case 0:
                    System.out.println("\nSaindo do programa...");
                    break;
                default:
                    limparTela();
                    System.out.println("Opção inválida! Tente novamente.");
                    voltarMenu();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("**************************************");
        System.out.println("******** MENU PRINCIPAL ********");
        System.out.println("**************************************");
        System.out.println("1 - Incluir produto");
        System.out.println("2 - Consultar produto");
        System.out.println("3 - Listagem de produtos");
        System.out.println("4 - Vendas por período - detalhado");
        System.out.println("5 - Realizar venda");
        System.out.println("0 - Sair");
    }

    private int lerOpcao() {
        System.out.print("\nDigite a opção desejada: ");
        int input = scan.nextInt();
        return input;
    }
    

    private void incluirProduto() {
        System.out.println("**************************************");
        System.out.println("******** INCLUIR PRODUTO ********");
        System.out.println("**************************************");
        System.out.print("Digite o código do produto: ");
        int codigo = scan.nextInt();
        scan.nextLine();
        System.out.print("Digite o nome do produto: ");
        String nome = scan.nextLine();
        System.out.print("Digite o valor do produto: ");
        double valor = scan.nextDouble();
        System.out.print("Digite a quantidade em estoque: ");
        int quantidadeEmEstoque = scan.nextInt();

        Produtos produto = new Produtos(codigo, nome, valor, quantidadeEmEstoque);
        produtos.add(produto);

        limparTela();
        System.out.println("Produto incluído com sucesso!");
        scan.nextLine();
        voltarMenu();
    }

    private void consultarProduto() {

        if (produtos.isEmpty()) {
            limparTela();
            System.out.println("Não há produtos cadastrados.");
            System.out.print("\nPressione ENTER para continuar...");
            scan.nextLine();
            scan.nextLine();
            exibirMenu();

        } else {

            limparTela();
            System.out.println("**************************************");
            System.out.println("******** CONSULTA DE PRODUTOS ********");
            System.out.println("**************************************");
            System.out.print("Digite o código do produto: ");
            int codigo = scan.nextInt();

            Produtos produtoEncontrado = null;

            for (Produtos produto : produtos) {
                if (produto.getCodigo() == codigo) {
                    produtoEncontrado = produto;
                    System.out.println("");
                    break;
                }
            }

            if (produtoEncontrado != null) {
                limparTela();
                System.out.println("Código: " + produtoEncontrado.getCodigo());
                System.out.println("Nome: " + produtoEncontrado.getNome());
                System.out.println("Valor: " + produtoEncontrado.getValor());
                System.out.println("Quantidade em estoque: " + produtoEncontrado.getQuantidadeEmEstoque());
                scan.nextLine();            
                voltarMenu();
            } else {
                limparTela();
                System.out.println("Produto não encontrado! Retorne ao menu e cadastre um produto ou verifique o código informado.");
                scan.nextLine();     
                voltarMenu();
            }
        }
    }

    private void listarProdutos() {
        limparTela();
        System.out.println("**************************************");
        System.out.println("******** LISTAGEM DE PRODUTOS ********");
        System.out.println("**************************************");

        if (produtos.isEmpty()) {
            limparTela();
            System.out.println("Não há produtos cadastrados. Volte ao menu e digite a opção 1.");
            scan.nextLine();
            voltarMenu();
        } else {
            limparTela();
            System.out.println("Código\tNome\tValor\tQuantidade");

            for (Produtos produto : produtos) {
                System.out.println(
                    produto.getCodigo() + "\t" +
                    produto.getNome() + "\t" +
                    produto.getValor() + "\t" +
                    produto.getQuantidadeEmEstoque()
                );
            }

            double valorTotal = 0;
            double valorMinimo = produtos.get(0).getValor();
            double valorMaximo = produtos.get(0).getValor();

            for (Produtos produto : produtos) {
                valorTotal += produto.getValor();
                if (produto.getValor() < valorMinimo) {
                    valorMinimo = produto.getValor();
                }
                if (produto.getValor() > valorMaximo) {
                    valorMaximo = produto.getValor();
                }
            }

            double valorMedio = valorTotal / produtos.size();

            System.out.println("---");
            System.out.println("Valor médio: " + valorMedio);
            System.out.println("Valor máximo: " + valorMaximo);
            System.out.println("Valor mínimo: " + valorMinimo);
            scan.nextLine();
            voltarMenu();
        }
    }

    private void vendasPorPeriodo() {
        limparTela();
        System.out.println("**************************************");
        System.out.println("******** VENDAS - POR PERÍODO ********");
        System.out.println("**************************************");

        if (vendas.isEmpty()) {
            limparTela();
            System.out.println("Não foram realizadas vendas.\nVolte ao menu e utilize a opção 5.");
            scan.nextLine();
            voltarMenu();
        } else {

            scan.nextLine();
            System.out.print("Digite a data inicial (no formato dd/MM/yyyy): ");
            String dataInicialStr = scan.nextLine();

            System.out.print("Digite a data final (no formato dd/MM/yyyy): ");
            String dataFinalStr = scan.nextLine();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicial, dataFinal;

            try {
                dataInicial = LocalDate.parse(dataInicialStr, dtf);
                dataFinal = LocalDate.parse(dataFinalStr, dtf);
            } catch (DateTimeParseException e) {
                limparTela();
                System.out.println("Formato de data inválido. Certifique-se de usar o formato dd/MM/yyyy.");
                voltarMenu();
                return;
            }

            System.out.println("Período de emissão: " + dataInicial + " a " + dataFinal);

            boolean encontrouVendas = false;

            limparTela();

            for (Vendas venda : vendas) {
                LocalDate dataVenda = venda.getDataVenda();
                if (dataVenda.isAfter(dataInicial.minusDays(1)) && dataVenda.isBefore(dataFinal.plusDays(1))) {
                    encontrouVendas = true;
                    Produtos produto = venda.getProdutoVendido();
                    System.out.println("Data da venda: " + dataVenda.format(dtf));
                    System.out.println("Nome do produto: " + produto.getNome());
                    System.out.println("Quantidade: " + venda.getQuantidadeVendida());
                    System.out.println("Valor unitário: " + produto.getValor());
                    System.out.println("Valor total: " + venda.getValorTotal());
                    System.out.println("===============================");
                }
            }
            voltarMenu();

            if (!encontrouVendas) {
                limparTela();
                System.out.println("Não foram encontradas vendas no período especificado.");
                voltarMenu();
            }
        }
    }

    private void realizarVenda() {
        limparTela();
        System.out.println("**************************************");
        System.out.println("******** REALIZAR VENDA ********");
        System.out.println("**************************************");
        if (produtos.isEmpty()) {
            limparTela();
            System.out.println("Não há produtos cadastrados.");
            scan.nextLine();
            voltarMenu();
        } else {

            System.out.print("Digite o código do produto: ");
            int codigo = scan.nextInt();
            scan.nextLine();

            Produtos produtoEncontrado = null;
            for (Produtos produto : produtos) {
                if (produto.getCodigo() == codigo) {
                    produtoEncontrado = produto;
                    break;
                }
            }

            if (produtoEncontrado == null) {
                limparTela();
                System.out.println("Produto não encontrado.");
                voltarMenu();
            } else {

                System.out.print("Digite a quantidade a ser vendida: ");
                int quantidadeVendida = scan.nextInt();
                scan.nextLine();

                if (quantidadeVendida > produtoEncontrado.getQuantidadeEmEstoque()) {
                    limparTela();
                    System.out.println("Quantidade insuficiente em estoque.");
                    voltarMenu();
                }

                produtoEncontrado.setQuantidadeEmEstoque(produtoEncontrado.getQuantidadeEmEstoque() - quantidadeVendida);

                System.out.print("Data da venda (deixe em branco para a data de hoje): ");
                String dataT = scan.nextLine();

                LocalDate data;
                if (dataT.trim().isEmpty()) {
                    data = LocalDate.now();
                } else {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    data = LocalDate.parse(dataT, dtf);
                }

                Vendas venda = new Vendas(data, produtoEncontrado, quantidadeVendida);
                vendas.add(venda); 

                limparTela();
                System.out.println("Venda realizada com sucesso!\nCódigo do produto: " + codigo + "\nQuantidade vendida: " + quantidadeVendida + "\nData da venda: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                voltarMenu();
            }
        }
    }

    public void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void voltarMenu() {
        System.out.print("\nPressione ENTER para continuar...");
        scan.nextLine();
        limparTela();
    }
}
