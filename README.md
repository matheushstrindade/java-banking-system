# Java Banking System

Sistema bancário simples e extremamente bem escrito em Java, usando as melhores práticas de 2025:

- `BigDecimal` para valores monetários (nunca double!)
- Validação completa de entrada
- IDs sequenciais com `long`
- Código limpo, organizado em pacotes
- Mensagens de erro claras em português

## Funcionalidades
- Criar conta com saldo inicial
- Depositar (valida valor > 0)
- Sacar (valida saldo suficiente)
- Exibição formatada em reais (R$ 1.234,56)

## Estrutura do projeto
```bash
src/main/java/com/matheushstrindade/banking/
├─ model/
│   └─ Conta.java
└─ App.java
```

## Como executar
```bash
# Compilar
javac src/main/java/com/matheushstrindade/banking/*.java src/main/java/com/matheushstrindade/banking/model/*.java

# Executar
java -cp src/main/java com.matheushstrindade.banking.App
```

Saída esperada:
```
Conta criada: Conta{id=1, saldo=R$ 1000,00}

Negativo bloqueado: Valor do depósito deve ser maior que zero. Tentativa: R$ -5000,00

Depósito válido → Conta{id=1, saldo=R$ 1500,75}
Saque bloqueado: Saldo insuficiente. Saldo atual: R$ 1500,75 | Tentativa de saque: R$ 5000,00
Saque válido → Conta{id=1, saldo=R$ 1200,75}
```

## Tecnologias
**Java**\
Projeto 100% pronto para evoluir para:
- Spring Boot 
- JPA / Hibernate 
- PostgreSQL / H2 
- Testes com JUnit 
- API REST

Feito com carinho por Matheus H. S. Trindade\
Sinta-se à vontade para dar star e usar como base nos seus estudos ou portfólio!
