USE [EasyFeira]
GO

/****** Object:  Table [dbo].[Empresa]    Script Date: 15/01/2023 23:18:08 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Empresa](
	[idEmpresa] [int] NOT NULL,
	[nomeEmpresa] [varchar](255) NOT NULL,
	[nifEmpresa] [int] NOT NULL,
	[moradaEmpresa] [varchar](255) NOT NULL,
	[telefoneEmpresa] [char](9) NOT NULL,
	[emailEmpresa] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Empresa] PRIMARY KEY CLUSTERED 
(
	[idEmpresa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

