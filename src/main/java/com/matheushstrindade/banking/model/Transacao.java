package com.matheushstrindade.banking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public final class Transacao {  // classe final = ninguém herda

    // === CAMPOS IMUTÁVEIS (final) ===
    private final Tipo tipo;
    private final BigDecimal valor;
    private final LocalDateTime dataHora;

    // === ENUM TIPO (tipo seguro, sem risco de string errada) ===
    public enum Tipo {
        DEPOSITO("depósito"),
        SAQUE("saque");

        private final String descricao;

        Tipo(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    // === CONSTRUTOR PRIVADO ===
    private Transacao(Tipo tipo, BigDecimal valor) {
        this.tipo = Objects.requireNonNull(tipo, "Tipo da transação não pode ser nulo");
        this.valor = Objects.requireNonNull(valor, "Valor da transação não pode ser nulo")
                .setScale(2, RoundingMode.HALF_EVEN);
        this.dataHora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }

    // === MÉTODOS FÁBRICA ===
    public static Transacao deposito(BigDecimal valor) {
        Objects.requireNonNull(valor, "Valor não pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Valor do depósito deve ser maior que zero. Tentativa: R$ " + formatarMoeda(valor)
            );
        }
        return new Transacao(Tipo.DEPOSITO, valor);
    }

    public static Transacao saque(BigDecimal valor) {
        Objects.requireNonNull(valor, "Valor não pode ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Valor do saque deve ser maior que zero. Tentativa: R$ " + formatarMoeda(valor)
            );
        }
        return new Transacao(Tipo.SAQUE, valor.negate());
    }

    // === GETTERS ===
    public Tipo getTipo() { return tipo; }

    public BigDecimal getValor() { return valor; }

    public LocalDateTime getDataHora() { return dataHora; }

    public boolean isDeposito() { return tipo == Tipo.DEPOSITO; }

    public boolean isSaque() { return tipo == Tipo.SAQUE; }

    // === VALIDAÇÃO ===
    private static void validarValorPositivo(BigDecimal valor, Tipo tipo) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Valor do " + tipo.getDescricao() + " deve ser maior que zero. Tentativa: R$ " + formatarMoeda(valor.abs())
            );
        }
    }

    // === FORMATAÇÃO ===
    public String getValorFormatado() {
        String sinal = valor.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "-";
        return sinal + "R$ " + formatarMoeda(valor.abs());
    }

    private static String formatarMoeda(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_EVEN)
                .toPlainString()
                .replace('.', ',');
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s",
                dataHora.toLocalDate(),
                tipo.getDescricao().toUpperCase(),
                getValorFormatado()
        );
    }
}