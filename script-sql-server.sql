-- Script para criar banco SQL Server
-- Execute no SQL Server Management Studio

-- 1. Criar o banco de dados
CREATE DATABASE bbanco_adc;
GO

-- 2. Usar o banco
USE bbanco_adc;
GO

-- 3. Criar tabela usuarios
CREATE TABLE usuarios (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome_completo NVARCHAR(100) NOT NULL,
    cpf NVARCHAR(11) NOT NULL UNIQUE,
    telefone NVARCHAR(20),
    email NVARCHAR(100) NOT NULL UNIQUE,
    senha NVARCHAR(255) NOT NULL,
    sexo NVARCHAR(10),
    tipo_usuario NVARCHAR(20) NOT NULL,
    termos_aceitos BIT NOT NULL,
    data_nascimento DATE NOT NULL
);

-- 4. Criar tabela profissionais
CREATE TABLE profissionais (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome_completo NVARCHAR(100) NOT NULL,
    cpf NVARCHAR(11) NOT NULL UNIQUE,
    telefone NVARCHAR(20),
    email NVARCHAR(100) NOT NULL UNIQUE,
    senha NVARCHAR(255) NOT NULL,
    sexo NVARCHAR(10),
    especialidade NVARCHAR(20) NOT NULL,
    termos_aceitos BIT NOT NULL,
    data_nascimento DATE NOT NULL
);

-- 5. Inserir dados de teste
INSERT INTO usuarios (nome_completo, cpf, telefone, email, senha, sexo, tipo_usuario, termos_aceitos, data_nascimento)
VALUES ('João Silva', '12345678901', '11999999999', 'joao@email.com', '123456', 'masculino', 'paciente', 1, '1990-01-01');

INSERT INTO profissionais (nome_completo, cpf, telefone, email, senha, sexo, especialidade, termos_aceitos, data_nascimento)
VALUES ('Dr. Maria Santos', '98765432100', '11888888888', 'maria@email.com', '123456', 'feminino', 'psicologia', 1, '1985-05-15');

-- 6. Consultar dados
SELECT * FROM usuarios;
SELECT * FROM profissionais;