package com.matheushstrindade.banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cliente {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<Conta> contas = new ArrayList<>();

    public Cliente(String nome, String cpf, LocalDate dataNascimento) {
        Objects.requireNonNull(nome, "Nome não pode ser nulo");
        Objects.requireNonNull(cpf, "CPF não pode ser nulo");
        Objects.requireNonNull(dataNascimento, "Data de nascimento não pode ser nula");

        this.nome = nome.trim();

        if (this.nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        String cpfLimpo = cpf.replaceAll("\\D", "");
        if (!validarCpf(cpfLimpo)) {
            throw new IllegalArgumentException("CPF inválido: " + cpf);
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro");
        }

        this.cpf = cpf; // mantém formatado para exibição (ex: 529.982.247-25)
        this.dataNascimento = dataNascimento;
    }

    // === GETTERS ===
    public String getNome() { return nome; }

    public String getCpf() { return cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }

    public List<Conta> getContas() { return Collections.unmodifiableList(contas); }

    // === OPERAÇÕES ===
    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        if (this.contas.contains(conta)) {
            throw new IllegalArgumentException("Esta conta já pertence ao cliente.");
        }
        if (conta.getTitular() != null && conta.getTitular() != this) {
            throw new IllegalArgumentException(
                    "Esta conta já tem outro titular: " + conta.getTitular().getNome()
            );
        }

        this.contas.add(conta);
        conta.setTitular(this);
    }

    // === MÉTODOS AUXILIARES ===

    /**
     * Valida um CPF brasileiro seguindo o algoritmo oficial da Receita Federal.
     * Aceita CPF com ou sem formatação (pontos, traços, espaços).
     *
     * @param cpf CPF a ser validado (ex: "123.456.789-00" ou "12345678900")
     * @return true se o CPF for válido, false caso contrário
     */
    public static boolean validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        // Remove tudo que não for dígito
        String digitos = cpf.replaceAll("\\D", "");

        // Verifica se tem 11 dígitos
        if (digitos.length() != 11) {
            return false;
        }

        // Bloqueia CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (digitos.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Cálculo do 1º dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (digitos.charAt(i) - '0') * (10 - i);
            }
            int primeiroDigito = (soma * 10) % 11;
            if (primeiroDigito == 10 || primeiroDigito == 11) primeiroDigito = 0;

            if (primeiroDigito != (digitos.charAt(9) - '0')) {
                return false;
            }

            // Cálculo do 2º dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (digitos.charAt(i) - '0') * (11 - i);
            }
            int segundoDigito = (soma * 10) % 11;
            if (segundoDigito == 10 || segundoDigito == 11) segundoDigito = 0;

            return segundoDigito == (digitos.charAt(10) - '0');

        } catch (Exception e) {
            return false; // Qualquer erro inesperado → CPF inválido
        }
    }

    @Override
    public String toString() {
        return "Cliente{nome='" + nome + "', cpf='" + cpf + "'}";
    }
}
