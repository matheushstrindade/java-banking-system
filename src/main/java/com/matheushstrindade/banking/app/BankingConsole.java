package com.matheushstrindade.banking.app;

import com.matheushstrindade.banking.model.Cliente;
import com.matheushstrindade.banking.model.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class BankingConsole {
    private static final Scanner sc = new Scanner(System.in);
    private static Cliente clienteAtual = null;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   BANKING SYSTEM - CONSOLE v1.0   ");
        System.out.println("   Bem-vindo!   ");
        System.out.println("========================================");
        System.out.println();

        while (true) {
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opção");

            switch (opcao) {
                case 1 -> criarCliente();
                case 2 -> criarConta();
                case 3 -> selecionarContaEOperar();
                case 4 -> listarContasDoCliente();
                case 5 -> exibirExtratoCompleto();
                case 0 -> {
                    System.out.println("Obrigado por usar o Banking System!");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
            pausar();
        }
    }

    private static void exibirMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("               MENU PRINCIPAL");
        System.out.println("=".repeat(40));
        System.out.println("1 → Criar novo cliente");
        System.out.println("2 → Criar nova conta para o cliente");
        System.out.println("3 → Selecionar conta e fazer operações");
        System.out.println("4 → Listar todas as contas do cliente");
        System.out.println("5 → Exibir extrato completo de uma conta");
        System.out.println("0 → Sair");
        System.out.println("—".repeat(40));
    }

    private static void criarCliente() {
        System.out.println("\n--- CRIAÇÃO DE CLIENTE ---");
        String nome = lerString("Nome completo");
        String cpf = lerString("CPF (ex: 529.982.247-25)");
        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        LocalDate dataNasc = LocalDate.parse(sc.nextLine());

        try {
            clienteAtual = new Cliente(nome, cpf, dataNasc);
            System.out.println("Cliente criado com sucesso!");
            System.out.println(clienteAtual.getNome() + " | CPF: " + clienteAtual.getCpf());
        } catch (Exception e) {
            System.out.println("Erro ao criar cliente: " + e.getMessage());
        }
    }

    private static void criarConta() {
        if (clienteAtual == null) {
            System.out.println("Crie um cliente primeiro!");
            return;
        }
        BigDecimal saldoInicial = lerBigDecimal("Saldo inicial da conta");
        try {
            new Conta(clienteAtual, saldoInicial);
            System.out.println("Conta criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void selecionarContaEOperar() {
        if (clienteAtual == null || clienteAtual.getContas().isEmpty()) {
            System.out.println("Nenhuma conta disponível.");
            return;
        }

        System.out.println("\n--- CONTAS DO CLIENTE ---");
        var contas = clienteAtual.getContas();
        for (int i = 0; i < contas.size(); i++) {
            Conta c = contas.get(i);
            System.out.println((i + 1) + " → ID " + c.getId() + " | Saldo: R$ " + c.getSaldo());
        }

        int idx = lerInteiro("Selecione a conta (número)") - 1;
        if (idx < 0 || idx >= contas.size()) {
            System.out.println("Conta inválida!");
            return;
        }

        Conta conta = contas.get(idx);
        menuOperacoesConta(conta);
    }

    private static void menuOperacoesConta(Conta conta) {
        while (true) {
            System.out.println("\n--- CONTA ID " + conta.getId() + " | Saldo: R$ " + conta.getSaldo() + " ---");
            System.out.println("1 → Depositar");
            System.out.println("2 → Sacar");
            System.out.println("3 → Ver extrato");
            System.out.println("0 → Voltar");
            int op = lerInteiro("Opção");

            if (op == 1) {
                BigDecimal valor = lerBigDecimal("Valor do depósito");
                try {
                    conta.depositar(valor);
                    System.out.println("Depósito realizado com sucesso!");
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else if (op == 2) {
                BigDecimal valor = lerBigDecimal("Valor do saque");
                try {
                    conta.sacar(valor);
                    System.out.println("Saque realizado com sucesso!");
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else if (op == 3) {
                System.out.println("\nEXTRATO DA CONTA ID " + conta.getId());
                System.out.println("-".repeat(50));
                conta.getExtrato().forEach(System.out::println);
                System.out.println("-".repeat(50));
                System.out.println("SALDO ATUAL: R$ " + conta.getSaldo());
            } else if (op == 0) {
                break;
            }
        }
    }

    private static void listarContasDoCliente() {
        if (clienteAtual == null) {
            System.out.println("Nenhum cliente criado.");
            return;
        }
        System.out.println("\nCONTAS DE " + clienteAtual.getNome());
        if (clienteAtual.getContas().isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            clienteAtual.getContas().forEach(c ->
                    System.out.println("ID " + c.getId() + " → R$ " + c.getSaldo() + " | " + c.getExtrato().size() + " transações")
            );
        }
    }

    private static void exibirExtratoCompleto() {
        selecionarContaEOperar();
    }

    // === MÉTODOS AUXILIARES ===
    private static String lerString(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine().trim();
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Digite um número válido!");
            }
        }
    }

    private static BigDecimal lerBigDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": R$ ");
                return new BigDecimal(sc.nextLine().replace(",", "."));
            } catch (Exception e) {
                System.out.println("Valor inválido! Use formato 1234,56");
            }
        }
    }

    private static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }
}