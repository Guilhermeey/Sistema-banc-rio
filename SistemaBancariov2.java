import java.util.Scanner;
 
public class SistemaBancariov2 {
 
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
 
    static boolean sair = false;
 
    public static void main(String[] args) {
 
        int opcao;
 
        do {
            mostrarMenu();
 
            if (!scanner.hasNextInt()) {
                System.out.println("Opção inválida!");
                scanner.next();
                continue;
            }
 
            opcao = scanner.nextInt();
 
            if (!contaAberta && (opcao < 1 || opcao > 2)) {
                System.out.println("Opção inválida!");
                continue;
            }
 
            if (contaAberta && (opcao < 1 || opcao > 7)) {
                System.out.println("Opção inválida!");
                continue;
            }
 
            if (!contaAberta) {
                switch (opcao) {
                    case 1:
                        abrirConta();
                        break;
                    case 2:
                        System.out.println("Encerrando o sistema...");
                        sair = true;
                        break;
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
                        sair = true;
                        break;
                }
            }
 
        } while (!sair);
    }
 
    public static void mostrarMenu() {
        mostrarTitulo("SISTEMA BANCÁRIO");
 
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
 
    public static void mostrarTitulo(String titulo) {
        System.out.println("\n==============================");
        System.out.println("        " + titulo);
        System.out.println("==============================");
    }
 
    public static void abrirConta() {
        mostrarTitulo("ABRIR CONTA");
 
        scanner.nextLine();
 
        System.out.print("Nome do cliente: ");
        nomeCliente = scanner.nextLine();
 
        System.out.print("Saldo inicial: ");
        saldoInicial = scanner.nextDouble();
 
        if (saldoInicial < 0) {
            System.out.println("Valor inválido!");
            return;
        }
 
        saldoAtual = saldoInicial;
        contaAberta = true;
 
        saldoMinimo = saldoAtual;
        saldoMaximo = saldoAtual;
 
        System.out.println("Conta criada com sucesso!");
    }
 
    public static void realizarDeposito() {
        mostrarTitulo("DEPÓSITO");
 
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
 
        if (valor <= 0) {
            System.out.println("Valor inválido!");
            return;
        }
 
        saldoAtual += valor;
        qtdDepositos++;
        valorDepositos += valor;
 
        atualizarMinMax();
 
        System.out.printf("Novo saldo: %.2f\n", saldoAtual);
    }
 
    public static void realizarSaque() {
        mostrarTitulo("SAQUE");
 
        System.out.print("Valor: ");
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
 
        if (valor > saldoAtual) {
            System.out.println("Saldo insuficiente!");
            return;
        }
 
        int[] notas = {100, 50, 20, 10, 5, 2};
        int restante = valor;
        int[] qtdNotas = new int[notas.length];
 
        for (int i = 0; i < notas.length; i++) {
            qtdNotas[i] = restante / notas[i];
            restante %= notas[i];
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
 
        System.out.printf("Novo saldo: %.2f\n", saldoAtual);
    }
 
    public static void aplicarJuros() {
        mostrarTitulo("APLICAR JUROS");
 
        System.out.print("Taxa (%): ");
        double taxa = scanner.nextDouble();
 
        if (taxa <= 0) {
            System.out.println("Taxa inválida!");
            return;
        }
 
        double juros = saldoAtual * (taxa / 100);
        saldoAtual += juros;
        totalJuros += juros;
 
        atualizarMinMax();
 
        System.out.printf("Juros: %.2f\n", juros);
        System.out.printf("Novo saldo: %.2f\n", saldoAtual);
    }
 
    public static void simularEmprestimo() {
        mostrarTitulo("SIMULAÇÃO DE EMPRÉSTIMO");
 
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
 
        System.out.print("Taxa (% ao mês): ");
        double taxa = scanner.nextDouble();
 
        System.out.print("Parcelas: ");
        int parcelas = scanner.nextInt();
 
        if (valor <= 0 || taxa <= 0 || parcelas <= 0) {
            System.out.println("Valores inválidos!");
            return;
        }
 
        double jurosTotal = valor * (taxa / 100) * parcelas;
        double total = valor + jurosTotal;
        double parcela = total / parcelas;
 
        System.out.printf("Parcela: %.2f\n", parcela);
        System.out.printf("Juros total: %.2f\n", jurosTotal);
        System.out.printf("Total: %.2f\n", total);
    }
 
    public static void mostrarExtrato() {
        mostrarTitulo("EXTRATO");
 
        System.out.println("Cliente: " + nomeCliente);
        System.out.printf("Saldo inicial: %.2f\n", saldoInicial);
        System.out.printf("Saldo atual: %.2f\n", saldoAtual);
        System.out.println("Depósitos: " + qtdDepositos + " | " + valorDepositos);
        System.out.println("Saques: " + qtdSaques + " | " + valorSaques);
        System.out.printf("Juros recebidos: %.2f\n", totalJuros);
        System.out.printf("Saldo mínimo: %.2f\n", saldoMinimo);
        System.out.printf("Saldo máximo: %.2f\n", saldoMaximo);
    }
 
    public static void mostrarIntegrantes() {
        mostrarTitulo("INTEGRANTES");
 
        System.out.println("- João Gonçalves");
        System.out.println("- Adicione outros nomes");
    }
 
    // ================= AUXILIAR =================
    public static void atualizarMinMax() {
        if (saldoAtual < saldoMinimo) {
            saldoMinimo = saldoAtual;
        }
        if (saldoAtual > saldoMaximo) {
            saldoMaximo = saldoAtual;
        }
    }
}