CREATE TABLE [dbo].[Admin] (
    [Id]       INT            IDENTITY (1, 1) NOT NULL,
    [email]    NVARCHAR (MAX) NOT NULL,
    [password] NVARCHAR (MAX) NOT NULL,
    [nome]     NVARCHAR (MAX) NOT NULL,
    CONSTRAINT [PK_Admin] PRIMARY KEY CLUSTERED ([Id] ASC)
);


CREATE TABLE [dbo].[User] (
    [Id]        INT            IDENTITY (1, 1) NOT NULL,
    [email]     NVARCHAR (MAX) NOT NULL,
    [password]  NVARCHAR (MAX) NOT NULL,
    [firstName] NVARCHAR (MAX) NOT NULL,
    [lastName]  NVARCHAR (MAX) NOT NULL,
    [phone]     NVARCHAR (MAX) NOT NULL,
    [nif]       INT            NOT NULL,
    [iban]      NVARCHAR (MAX) NOT NULL,
    [morada]    NVARCHAR (MAX) NOT NULL,
    CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED ([Id] ASC)
);


CREATE TABLE [dbo].[Empresa] (
    [IdEmpresa]     INT            IDENTITY (1, 1) NOT NULL,
    [nome]          NVARCHAR (MAX) NOT NULL,
    [nif]           INT            NOT NULL,
    [morada]        NVARCHAR (MAX) NOT NULL,
    [contactos]     NVARCHAR (MAX) NOT NULL,
    [loginPassword] NVARCHAR (MAX) DEFAULT (N'') NOT NULL,
    [loginUsername] NVARCHAR (MAX) DEFAULT (N'') NOT NULL,
    CONSTRAINT [PK_Empresa] PRIMARY KEY CLUSTERED ([IdEmpresa] ASC)
);



CREATE TABLE [dbo].[Vendedor] (
    [Id]        INT            IDENTITY (1, 1) NOT NULL,
    [idEmpresa] INT            NOT NULL,
    [nome]      NVARCHAR (MAX) NOT NULL,
    [contactos] NVARCHAR (MAX) NOT NULL,
    [morada]    NVARCHAR (MAX) NOT NULL,
    [password]  NVARCHAR (MAX) NOT NULL,
    CONSTRAINT [PK_Vendedor] PRIMARY KEY CLUSTERED ([Id] ASC)
);


CREATE TABLE [dbo].[Stand] (
    [IdStand]    INT IDENTITY (1, 1) NOT NULL,
    [idVendedor] INT NOT NULL,
    [idCertame]  INT NOT NULL,
    CONSTRAINT [PK_Stand] PRIMARY KEY CLUSTERED ([IdStand] ASC)
);

CREATE TABLE [dbo].[Produto] (
    [IdProduto]  INT            IDENTITY (1, 1) NOT NULL,
    [idVendedor] INT            NOT NULL,
    [idStand]    INT            NOT NULL,
    [preco]      FLOAT (53)     NULL,
    [imagens]    NVARCHAR (MAX) NOT NULL,
    [nome]       NVARCHAR (MAX) DEFAULT (N'') NOT NULL,
    [stock]      INT            NULL,
    CONSTRAINT [PK_Produto] PRIMARY KEY CLUSTERED ([IdProduto] ASC)
);



