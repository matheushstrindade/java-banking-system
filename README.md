# Banking System

Sistema bancário **realista e extremamente bem escrito** em Java puro, seguindo as melhores práticas de 2025.

### Destaques (nível produção)
- `BigDecimal` para valores monetários (nunca `double`!)
- Validação **oficial de CPF** (algoritmo da Receita Federal)
- Relacionamento bidirecional **Cliente ↔ Conta** (1:N) seguro e sem loop
- Validação completa de entrada (nulo, negativo, futuro, duplicidade)
- Estrutura de pacotes profissional
- Mensagens claras em português brasileiro
- Código limpo, seguro e pronto para escala

## Funcionalidades atuais
- Cadastro de cliente com CPF válido (ex: 529.982.247-25)
- Criação de múltiplas contas por cliente (corrente, poupança, etc.)
- Depósito e saque com validação total
- Proteção contra titular inconsistente
- Resumo completo do cliente com todas as suas contas

## Estrutura do projeto
```bash
src/main/java/com/matheushstrindade/banking/
├─ model/
│   ├─ Cliente.java     ← com validação oficial de CPF
│   └─ Conta.java       ← exige titular, relacionamento bidirecional
└─ App.java              ← demonstração completa do fluxo real
```

## Como executar
```bash
# Compilar
javac src/main/java/com/matheushstrindade/banking/*.java \
      src/main/java/com/matheushstrindade/banking/model/*.java

# Executar
java -cp src/main/java com.matheushstrindade.banking.App
```

Saída esperada:
```
Cliente criado: Matheus H. S. Trindade
CPF: 529.982.247-25

Criando conta corrente com saldo inicial...
Conta criada → Conta{id=1, saldo=R$ 1500,00}
Titular da conta: Matheus H. S. Trindade
Total de contas do cliente: 1

Testando depósito com valor negativo...
Depósito recusado → Valor do depósito deve ser maior que zero. Tentativa: R$ -5000,00

Testando depósito válido...
Depósito aceito → Conta{id=1, saldo=R$ 2250,50}

Testando saque acima do saldo...
Saque bloqueado → Saldo insuficiente. Saldo atual: R$ 2250,50 | Tentativa de saque: R$ 10000,00

Testando saque válido...
Saque aceito → Conta{id=1, saldo=R$ 1849,75}

Criando conta poupança para o mesmo cliente...
Conta poupança criada → Conta{id=2, saldo=R$ 5000,00}
Total de contas do cliente Matheus H. S. Trindade: 2

=== RESUMO DO CLIENTE ===
Nome: Matheus H. S. Trindade
CPF: 529.982.247-25
Total de contas: 2
  • Conta ID 1 → Saldo: R$ 1849.75
  • Conta ID 2 → Saldo: R$ 5000.00
```

## Tecnologias
**Java**\
Projeto 100% pronto para evoluir para:
- Spring Boot 
- JPA / Hibernate 
- PostgreSQL / H2 
- Testes com JUnit 
- API REST

Feito com carinho por\ 
**Matheus H. S. Trindade**\
https://github.com/matheushstrindade \
Sinta-se à vontade para dar star, fork, usar como base nos seus estudos ou até no seu TCC/portfólio!
Qualquer dúvida, é só chamar
