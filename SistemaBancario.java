import java.util.Scanner;
public class SistemaBancario {
    static Scanner scanner = new Scanner(System.in);
    static String nomeCliente = "";
    static double saldoInicial = 0;
    static double saldoAtual = 0;
    static boolean contaAberta = false;
    static int qtdDepositos = 0;
    static double valorDepositos = 0;
    static int qtdSaques = 0;
    static double valorSaques = 0;
    static double totalJuros = 0;
    static double saldoMinimo = Double.MAX_VALUE;
    static double saldoMaximo = Double.MIN_VALUE;
    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = scanner.nextInt();
            if (!contaAberta) {
                switch (opcao) {
                    case 1:
                        abrirConta();
                        break;
                    case 2:
                        System.out.println("Encerrando o sistema...");
                        opcao = 8;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } else {
                switch (opcao) {
                    case 1:
                        realizarDeposito();
                        break;
                    case 2:
                        realizarSaque();
                        break;
                    case 3:
                        aplicarJuros();
                        break;
                    case 4:
                        simularEmprestimo();
                        break;
                    case 5:
                        mostrarExtrato();
                        break;
                    case 6:
                        mostrarIntegrantes();
                        break;
                    case 7:
                        System.out.println("Encerrando o sistema...");
                        opcao = 8;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } while (opcao != 8);
    }
    // Aqui é o menu
    public static void mostrarMenu() {
        System.out.println("\n SISTEMA BANCÁRIO ");
        if (!contaAberta) {
            System.out.println("1 - Abrir Conta");
            System.out.println("2 - Sair");
        } else {
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Aplicar Juros");
            System.out.println("4 - Simular Empréstimo");
            System.out.println("5 - Extrato");
            System.out.println("6 - Integrantes");
            System.out.println("7 - Sair");
        }
        System.out.print("Escolha uma opção: ");
    }
    // 1 - Logica de abrir conta
    public static void abrirConta() {
        if (contaAberta) {
            System.out.println("Conta já foi criada!");
            return;
        }
        scanner.nextLine();
        System.out.print("Digite o nome do cliente: ");
        nomeCliente = scanner.nextLine();
        System.out.print("Digite o saldo inicial: ");
        saldoInicial = scanner.nextDouble();
        if (saldoInicial < 0) {
            System.out.println("Saldo inválido!");
            return;
        }
        saldoAtual = saldoInicial;
        contaAberta = true;
        saldoMinimo = saldoAtual;
        saldoMaximo = saldoAtual;
        System.out.println("Conta criada com sucesso!");
    }
    // 2 - Logica do Depósito
    public static void realizarDeposito() {
        if (!contaAberta) {
            System.out.println("Abra uma conta primeiro!");
            return;
        }
        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();
        if (valor <= 0) {
            System.out.println("Valor inválido!");
            return;
        }
        saldoAtual += valor;
        qtdDepositos++;
        valorDepositos += valor;
        atualizarMinMax();
        System.out.println("Depósito realizado! Novo saldo: " + saldoAtual);
    }
    // 3 - parte do Saque
    public static void realizarSaque() {
        if (!contaAberta) {
            System.out.println("Abra uma conta primeiro!");
            return;
        }
        System.out.print("Digite o valor do saque: ");
double valorDigitado = scanner.nextDouble();
 
if (valorDigitado <= 0) {
    System.out.println("Valor inválido!");
    return;
}
 
if (valorDigitado != (int) valorDigitado) {
    System.out.println("Não é possível sacar esse valor com as notas disponíveis.");
    return;
}
 
int valor = (int) valorDigitado;
        if (valor <= 0) {
            System.out.println("Valor inválido!");
            return;
        }
        if (valor > saldoAtual) {
            System.out.println("Saldo insuficiente!");
            return;
        }
        int[] notas = {100, 50, 20, 10, 5, 2};
        int restante = valor;
        int[] qtdNotas = new int[notas.length];
        for (int i = 0; i < notas.length; i++) {
            qtdNotas[i] = restante / notas[i];
            restante = restante % notas[i];
        }
        if (restante != 0) {
            System.out.println("Não é possível sacar esse valor com as notas disponíveis.");
            return;
        }
        System.out.println("Notas entregues:");
        for (int i = 0; i < notas.length; i++) {
            if (qtdNotas[i] > 0) {
                System.out.println(qtdNotas[i] + " nota(s) de R$ " + notas[i]);
            }
        }
        saldoAtual -= valor;
        qtdSaques++;
        valorSaques += valor;
        atualizarMinMax();
        System.out.println("Saque realizado! Novo saldo: " + saldoAtual);
    }
    // 4 - aplicar juros
    public static void aplicarJuros() {
        if (!contaAberta) {
            System.out.println("Abra uma conta primeiro!");
            return;
        }
        System.out.print("Digite a taxa de juros (%): ");
        double taxa = scanner.nextDouble();
        if (taxa <= 0) {
            System.out.println("Taxa inválida!");
            return;
        }
        double juros = saldoAtual * (taxa / 100);
        saldoAtual += juros;
        totalJuros += juros;
        atualizarMinMax();
        System.out.println("Juros aplicado: " + juros);
        System.out.println("Novo saldo: " + saldoAtual);
    }
    // 5 - Empréstimo
    public static void simularEmprestimo() {
        System.out.print("Valor do empréstimo: ");
        double valor = scanner.nextDouble();
        System.out.print("Taxa de juros (% ao mês): ");
        double taxa = scanner.nextDouble();
        System.out.print("Número de parcelas: ");
        int parcelas = scanner.nextInt();
        if (valor <= 0 || taxa <= 0 || parcelas <= 0) {
            System.out.println("Valores inválidos!");
            return;
        }
        double jurosTotal = valor * (taxa / 100) * parcelas;
        double total = valor + jurosTotal;
        double valorParcela = total / parcelas;
        System.out.println("Valor da parcela: " + valorParcela);
        System.out.println("Total de juros: " + jurosTotal);
        System.out.println("Total a pagar: " + total);
    }
    // 6 - mostrar o extrato
    public static void mostrarExtrato() {
        if (!contaAberta) {
            System.out.println("Abra uma conta primeiro!");
            return;
        }
        System.out.println("\n EXTRATO ");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Saldo inicial: " + saldoInicial);
        System.out.println("Saldo atual: " + saldoAtual);
        System.out.println("Depósitos: " + qtdDepositos + " | Total: " + valorDepositos);
        System.out.println("Saques: " + qtdSaques + " | Total: " + valorSaques);
        System.out.println("Total de juros recebidos: " + totalJuros);
        System.out.println("Saldo mínimo: " + saldoMinimo);
        System.out.println("Saldo máximo: " + saldoMaximo);
    }
    // 7 - Integrantes do grupo
    public static void mostrarIntegrantes() {
        System.out.println("Integrantes do grupo:");
        System.out.println("- (Guilherme Alejandro Barros Goycoechea)");
        System.out.println("- (Lorenzo Penna de Moraes)");
        System.out.println("- (Lucca werner)");
        System.out.println("- (Arthur Zagonell)");
    }
    // Atualiza mínimo e máximo
    public static void atualizarMinMax() {
        if (saldoAtual < saldoMinimo) {
            saldoMinimo = saldoAtual;
        }
        if (saldoAtual > saldoMaximo) {
            saldoMaximo = saldoAtual;
        }
    }
}