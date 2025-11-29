package com.matheushstrindade.banking;

import com.matheushstrindade.banking.model.Conta;
import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        Conta c1 = new Conta(new BigDecimal("1000.00"));

        System.out.println("Conta criada: " + c1);
        System.out.println();

        try {
            c1.depositar(new BigDecimal("-5000"));
            c1.depositar(BigDecimal.ZERO);
            c1.depositar(new BigDecimal("500.75"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\nApós tentativas inválidas: " + c1);

        // Operação válida
        c1.depositar(new BigDecimal("250.30"));
        System.out.println("Depósito válido → " + c1);

        // Teste de saque
        try {
            c1.sacar(new BigDecimal("5000"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
        }

        c1.sacar(new BigDecimal("300"));
        System.out.println("Saque válido → " + c1);
    }
}
