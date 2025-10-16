USE bbanco_adc;

-- Reverter para snake_case
IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'nomeCompleto')
    EXEC sp_rename 'usuarios.nomeCompleto', 'nome_completo', 'COLUMN';

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'tipoUsuario')
    EXEC sp_rename 'usuarios.tipoUsuario', 'tipo_usuario', 'COLUMN';

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'termosAceitos')
    EXEC sp_rename 'usuarios.termosAceitos', 'termos_aceitos', 'COLUMN';

IF EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'dataNascimento')
    EXEC sp_rename 'usuarios.dataNascimento', 'data_nascimento', 'COLUMN';

PRINT 'Banco corrigido!';