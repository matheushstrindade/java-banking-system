package com.matheushstrindade.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Conta {
    private static long proximoId = 1L;
    private final Long id;
    private BigDecimal saldo;
    private Cliente titular;
    private final List<Transacao> extrato = new ArrayList<>();

    public Conta(Cliente titular, BigDecimal saldoInicial) {
        Objects.requireNonNull(titular, "Titular não pode ser nulo");
        Objects.requireNonNull(saldoInicial, "Saldo inicial não pode ser nulo");

        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Saldo inicial não pode ser negativo: R$ " + formatarMoeda(saldoInicial)
            );
        }

        this.id = proximoId++;
        this.saldo = saldoInicial.setScale(2, RoundingMode.HALF_EVEN);
        titular.adicionarConta(this);
    }

    // === GETTERS ===
    public Long getId() { return id; }
    public BigDecimal getSaldo() { return saldo; }
    public Cliente getTitular() { return titular; }

    public List<Transacao> getExtrato() {
        return Collections.unmodifiableList(extrato); // Imutável externamente
    }

    void setTitular(Cliente titular) {
        this.titular = titular;
    }

    // === OPERAÇÕES PÚBLICAS (ÚNICAS formas de mexer no saldo) ===
    public void depositar(BigDecimal valor) {
        validarValorPositivo(valor, "depósito");
        Transacao transacao = Transacao.deposito(valor);
        this.saldo = this.saldo.add(transacao.getValor());
        this.extrato.add(transacao);
    }

    public void sacar(BigDecimal valor) {
        validarValorPositivo(valor, "saque");
        if (valor.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente. Saldo atual: R$ " + formatarMoeda(this.saldo) +
                            " | Tentativa de saque: R$ " + formatarMoeda(valor)
            );
        }
        Transacao transacao = Transacao.saque(valor);
        this.saldo = this.saldo.add(transacao.getValor()); // valor já é negativo!
        this.extrato.add(transacao);
    }

    // === MÉTODOS AUXILIARES ===
    private void validarValorPositivo(BigDecimal valor, String operacao) {
        Objects.requireNonNull(valor, "Valor do " + operacao + " não pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Valor do " + operacao + " deve ser maior que zero: R$ " + formatarMoeda(valor)
            );
        }
    }

    private String formatarMoeda(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_EVEN)
                .toPlainString()
                .replace('.', ',');
    }

    @Override
    public String toString() {
        return String.format("Conta{id=%d, saldo=R$ %s, transações=%d}",
                id, formatarMoeda(saldo), extrato.size());
    }
}