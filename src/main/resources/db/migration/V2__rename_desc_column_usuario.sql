-- Renomeia a coluna [desc] para descricao em tb_rise_usuario
EXEC sp_rename 'tb_rise_usuario.[desc]', 'descricao', 'COLUMN';
