-- Script para excluir o banco bbanco_adc
-- Execute no SQL Server Management Studio

USE master;
GO

-- Forçar desconexão de usuários
ALTER DATABASE bbanco_adc SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO

-- Excluir o banco
DROP DATABASE bbanco_adc;
GO

-- Confirmar exclusão
SELECT name FROM sys.databases WHERE name = 'bbanco_adc';
-- Se não retornar nada, o banco foi excluído com sucesso