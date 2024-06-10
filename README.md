# Consulta de vendas
Dado um projeto iniciado, foi solicitado o relatório de vendas e Sumário de vendas.

## Objetivo

- Criação de endpoints de API Rest com parâmetros de consulta opcionais.
- Implementação de consultas em banco de dados relacional com Spring Data JPA.

## endpoints

- Sumário de vendas por vendedor passando argumentos minDate e maxDate retorna os dados.
- Sumário de vendas por vendedor sem passar argumentos retorna os dados dos últimos 12 meses(maxDate = data sistema, minDate = 12 meses antes.)
- Relatório de vendas passando argumentos minDate e maxDate retorna os dados.
- Relatório de vendas sem passar argumentos retorna as vendas dos últimos 12 meses(maxDate = data sistema, minDate = 12 meses antes.)

**Collection Postman:** https://www.getpostman.com/collections/dea7904f994cb87c3d12