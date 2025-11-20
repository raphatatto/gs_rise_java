CREATE TABLE tb_rise_usuario
(
    id_usuario    INT IDENTITY(1,1) NOT NULL,
    nome__usuario VARCHAR(500)      NOT NULL,
    email_usuario VARCHAR(50),
    senha_usuario VARCHAR(100)      NOT NULL,
    tipo_usuario  VARCHAR(50),
    telefone      VARCHAR(20),
    [desc]        VARCHAR(500),
    habilidades   VARCHAR(500),
    CONSTRAINT PK_rise_usuario PRIMARY KEY (id_usuario)
);

CREATE TABLE tb_rise_trilha_progresso
(
    id_trilha             INT IDENTITY(1,1) NOT NULL,
    percentual_concluido  INT,
    dt_inicio             DATETIME2,
    dt_ultima_atualizacao DATETIME2,
    titulo_trilha         VARCHAR(500),
    categoria_trilha      VARCHAR(500),
    data_planejada        DATETIME2,
    dt_criacao            DATETIME2,
    id_usuario            INT              NOT NULL,
    CONSTRAINT PK_trilha_progresso PRIMARY KEY (id_trilha)
);

CREATE TABLE tb_rise_bem_estar
(
    id_bem_estar   INT IDENTITY(1,1) NOT NULL,
    dt_registro    DATETIME2,
    nivel_humor    INT,
    horas_estudo   DATETIME2,
    desc_atividade VARCHAR(50),
    id_usuario     INT               NOT NULL,
    CONSTRAINT PK_rise_bem_estar PRIMARY KEY (id_bem_estar)
);

CREATE TABLE tb_rise_curriculo
(
    id_curriculo             INT IDENTITY(1,1) NOT NULL,
    titulo_curriculo         VARCHAR(100),
    experiencia_profissional VARCHAR(1000),
    habilidades              VARCHAR(1000),
    formacao                 VARCHAR(1000),
    ultima_atualizacao       DATETIME2,
    projetos                 VARCHAR(100),
    links                    VARCHAR(100),
    id_usuario               INT               NOT NULL,
    CONSTRAINT PK_rise_curriculo PRIMARY KEY (id_curriculo)
);

CREATE UNIQUE INDEX tb_rise_curriculo__IDX
    ON tb_rise_curriculo (id_usuario ASC);

CREATE TABLE tb_rise_curso
(
    id_curso   INT IDENTITY(1,1) NOT NULL,
    nome_curso VARCHAR(500),
    desc_curso VARCHAR(500),
    link_curso VARCHAR(500),
    area_curso VARCHAR(500),
    id_usuario INT               NOT NULL,
    CONSTRAINT PK_rise_curso PRIMARY KEY (id_curso)
);

CREATE TABLE tb_rise_trilha_objetivo
(
    id_objetivo        INT IDENTITY(1,1) NOT NULL,
    titulo_objetivo    VARCHAR(500),
    categoria_objetivo VARCHAR(500),
    data_planejada     DATETIME2,
    concluido          CHAR(1),
    data_conclusao     DATETIME2,
    dt_criacao         DATETIME2,
    id_trilha          INT               NOT NULL,
    CONSTRAINT PK_trilha_objetivo PRIMARY KEY (id_objetivo)
);

ALTER TABLE tb_rise_bem_estar
    ADD CONSTRAINT FK_bem_estar_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES tb_rise_usuario (id_usuario);

ALTER TABLE tb_rise_curriculo
    ADD CONSTRAINT FK_curriculo_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES tb_rise_usuario (id_usuario);

ALTER TABLE tb_rise_curso
    ADD CONSTRAINT FK_curso_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES tb_rise_usuario (id_usuario);

ALTER TABLE tb_rise_trilha_objetivo
    ADD CONSTRAINT FK_objetivo_trilha
    FOREIGN KEY (id_trilha)
    REFERENCES tb_rise_trilha_progresso (id_trilha);

ALTER TABLE tb_rise_trilha_progresso
    ADD CONSTRAINT FK_trilha_usuario
    FOREIGN KEY (id_usuario)
    REFERENCES tb_rise_usuario (id_usuario);
