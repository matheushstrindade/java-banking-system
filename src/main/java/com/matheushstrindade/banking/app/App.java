package com.matheushstrindade.banking.app;

import com.matheushstrindade.banking.model.Cliente;
import com.matheushstrindade.banking.model.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.math.RoundingMode;

public class App {
    public static void main(String[] args) {

        // === Criação do Cliente ===
        Cliente matheus = new Cliente(
                "Matheus H. S. Trindade",
                "529.982.247-25",
                LocalDate.of(1995, 10, 5)
        );

        System.out.println("Cliente criado: " + matheus.getNome());
        System.out.println("CPF: " + matheus.getCpf() + "\n");

        // === Conta Corrente ===
        System.out.println("Criando conta corrente...");
        Conta corrente = new Conta(matheus, new BigDecimal("1500.00"));
        System.out.println("Conta criada → " + corrente + "\n");

        // === Operações na conta corrente ===
        System.out.println("=== OPERANDO NA CONTA CORRENTE ===");
        corrente.depositar(new BigDecimal("750.50"));
        System.out.println("Depósito de R$ 750,50 → Saldo: R$ " + corrente.getSaldo());

        corrente.sacar(new BigDecimal("400.75"));
        System.out.println("Saque de R$ 400,75 → Saldo: R$ " + corrente.getSaldo() + "\n");

        // === Conta Poupança ===
        System.out.println("Criando conta poupança...");
        Conta poupanca = new Conta(matheus, new BigDecimal("5000.00"));
        System.out.println("Conta poupança criada → " + poupanca + "\n");

        // === EXTRATO COMPLETO DA CONTA CORRENTE ===
        System.out.println("=== EXTRATO DETALHADO - CONTA CORRENTE (ID " + corrente.getId() + ") ===");
        corrente.getExtrato().forEach(System.out::println);
        System.out.println("─".repeat(50));
        System.out.println("SALDO FINAL: R$ " + corrente.getSaldo().setScale(2, RoundingMode.HALF_EVEN));
        System.out.println();

        // === RESUMO GERAL DO CLIENTE ===
        System.out.println("=== RESUMO COMPLETO DO CLIENTE ===");
        System.out.println("Nome: " + matheus.getNome());
        System.out.println("CPF: " + matheus.getCpf());
        System.out.println("Total de contas: " + matheus.getContas().size());
        System.out.println();

        matheus.getContas().forEach(conta -> {
            System.out.println("Conta ID " + conta.getId() +
                    " | Saldo: R$ " + conta.getSaldo().setScale(2, RoundingMode.HALF_EVEN) +
                    " | Transações: " + conta.getExtrato().size());
        });

        System.out.println("\nSistema bancário 100% funcional com extrato auditável.");
        System.out.println("Próximos passos: Spring Boot + API REST + PostgreSQL + Docker");
    }
}
