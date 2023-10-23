USE [EasyFeira]
GO

/****** Object:  Table [dbo].[Certame]    Script Date: 15/01/2023 23:16:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Certame](
	[idCertame] [int] NOT NULL,
	[maxStandsCertame] [int] NOT NULL,
	[categoriaCertame] [varchar](255) NOT NULL,
	[tipoCertame] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Certame] PRIMARY KEY CLUSTERED 
(
	[idCertame] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

