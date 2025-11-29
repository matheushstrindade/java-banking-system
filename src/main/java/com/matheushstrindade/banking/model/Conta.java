package com.matheushstrindade.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Conta {
    private static long proximoId = 1L;
    private final Long id;
    private BigDecimal saldo;
    private Cliente titular;

    public Conta(Cliente titular, BigDecimal saldoInicial) {
        if (saldoInicial == null) {
            throw new IllegalArgumentException("Saldo inicial não pode ser nulo.");
        }
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Saldo inicial não pode ser negativo. Valor informado: R$ " + formatarMoeda(saldoInicial)
            );
        }

        this.id = proximoId++;
        this.saldo = saldoInicial.setScale(2, RoundingMode.HALF_EVEN);
        titular.adicionarConta(this);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getTitular() { return titular; }

    // Setters
    void setTitular(Cliente titular) {
        this.titular = titular;
    }

    // === OPERAÇÕES ===

    public void depositar(BigDecimal valor) {
        validarValorPositivo(valor, "depósito");
        this.saldo = this.saldo.add(valor.setScale(2, RoundingMode.HALF_EVEN));
    }

    public void sacar(BigDecimal valor) {
        validarValorPositivo(valor, "saque");
        if (valor.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente. Saldo atual: R$ " + formatarMoeda(this.saldo) +
                            " | Tentativa de saque: R$ " + formatarMoeda(valor)
            );
        }
        this.saldo = this.saldo.subtract(valor.setScale(2, RoundingMode.HALF_EVEN));
    }

    // === MÉTODOS AUXILIARES ===

    private void validarValorPositivo(BigDecimal valor, String operacao) {
        Objects.requireNonNull(valor, "Valor do " + operacao + " não pode ser nulo.");

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Valor do " + operacao + " deve ser maior que zero. Tentativa: R$ " + formatarMoeda(valor)
            );
        }
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) return "0,00";
        return valor.setScale(2, RoundingMode.HALF_EVEN)
                .toPlainString()
                .replace('.', ',');
    }

    @Override
    public String toString() {
        return String.format("Conta{id=%d, saldo=R$ %s}", id, formatarMoeda(saldo));
    }
}