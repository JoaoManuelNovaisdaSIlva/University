USE [EasyFeira]
GO

/****** Object:  Table [dbo].[Utilizador]    Script Date: 15/01/2023 23:18:48 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Utilizador](
	[idUtilizador] [int] NOT NULL,
	[nomeUtilizador] [varchar](255) NOT NULL,
	[dataNascrimentoUtilizador] [date] NOT NULL,
	[moradaUtilizador] [varchar](255) NOT NULL,
	[telefoneUtilizador] [varchar](9) NOT NULL,
	[emailUtilizador] [varchar](255) NOT NULL,
	[nifUtilizador] [int] NOT NULL,
	[saldoUtilizador] [float] NOT NULL,
 CONSTRAINT [PK_Utilizador] PRIMARY KEY CLUSTERED 
(
	[idUtilizador] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

