# Banking System

Sistema bancário **brasileiro completo** em Java puro — feito com padrão de produção 2025.

Validação oficial de CPF • BigDecimal • Cliente com múltiplas contas • Extrato auditável • Clean Code total

![Java 17+](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Clean Code](https://img.shields.io/badge/Clean_Code-✓-success)
![BigDecimal](https://img.shields.io/badge/BigDecimal-✓-blue)

### Destaques (nível fintech real)
- Validação **oficial de CPF** (algoritmo da Receita Federal)
- `BigDecimal` para valores monetários (nunca `double`)
- Relacionamento bidirecional **Cliente ↔ Conta** (1:N) seguro e sem loop
- Transações **imutáveis** com enum + `LocalDateTime` (fuso SP)
- Extrato auditável com valor sinalizado (+ depósito / - saque)
- Zero gambiarra, zero dívida técnica
- Código 100% limpo, seguro e pronto para produção

## Funcionalidades
- Cadastro de cliente com CPF válido (ex: 529.982.247-25)
- Múltiplas contas por cliente (corrente, poupança, etc.)
- Depósito e saque com validação completa
- Histórico de transações imutável e protegido
- Extrato detalhado por conta
- Resumo completo do cliente

## Estrutura do projeto
```bash
src/main/java/com/matheushstrindade/banking/
├─ model/
│   ├─ Cliente.java     → validação oficial de CPF + relacionamento
│   ├─ Conta.java       → extrato imutável + operações seguras
│   └─ Transacao.java   → imutável, enum, valor sinalizado
└─ app/
   ├─ App.java              → demonstração automática (ideal para GitHub)
   └─ BankingConsole.java   → console interativo completo (use o banco de verdade!)
```

## Como executar
### 1. Demonstração automática (recomendado para ver rápido)
```bash
# Compilar
javac src/main/java/com/matheushstrindade/banking/*.java \
      src/main/java/com/matheushstrindade/banking/model/*.java

# Executar
java -cp src/main/java com.matheushstrindade.banking.app.App
```

### 2. Console interativo (use como cliente real!)
```bash
java -cp src/main/java com.matheushstrindade.banking.app.BankingConsole
```

### Saída da demonstração automática:
```
Cliente criado: Matheus H. S. Trindade
CPF: 529.982.247-25

Criando conta corrente...
Conta criada → Conta{id=1, saldo=R$ 1500,00, transações=0}

=== OPERANDO NA CONTA CORRENTE ===
Depósito de R$ 750,50 → Saldo: R$ 2250.50
Saque de R$ 400,75 → Saldo: R$ 1849.75

Criando conta poupança...
Conta poupança criada → Conta{id=2, saldo=R$ 5000,00, transações=0}

=== EXTRATO DETALHADO - CONTA CORRENTE (ID 1) ===
[2025-12-02] DEPÓSITO +R$ 750,50
[2025-12-02] SAQUE -R$ 400,75
──────────────────────────────────────────────────
SALDO FINAL: R$ 1849.75

=== RESUMO COMPLETO DO CLIENTE ===
Nome: Matheus H. S. Trindade
CPF: 529.982.247-25
Total de contas: 2

Conta ID 1 | Saldo: R$ 1849.75 | Transações: 2
Conta ID 2 | Saldo: R$ 5000.00 | Transações: 0

Sistema bancário 100% funcional com extrato auditável.
Próximos passos: Spring Boot + API REST + PostgreSQL + Docker
```

## Tecnologias
- Java
- BigDecimal
- Clean%20Code

## Próximos passos
- Transformar em API REST com Spring Boot
- Persistência com JPA/Hibernate + PostgreSQL 
- Testes unitários com JUnit 5 + Mockito 
- Docker + GitHub Actions 
- Autenticação JWT, transações, extrato, etc.

Feito com carinho por  
**Matheus H. S. Trindade**\
https://github.com/matheushstrindade

Sinta-se à vontade para dar star, fork, usar como base nos seus estudos ou até no seu TCC/portfólio!
Qualquer dúvida, é só chamar
