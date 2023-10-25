USE [EasyFeira]
GO

/****** Object:  Table [dbo].[Stand]    Script Date: 15/01/2023 23:18:30 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Stand](
	[idStand] [int] NOT NULL,
	[idCertame] [int] NOT NULL,
	[idEmpresa] [int] NOT NULL,
 CONSTRAINT [PK_Stand] PRIMARY KEY CLUSTERED 
(
	[idStand] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Stand]  WITH CHECK ADD  CONSTRAINT [FK_Stand_Certame] FOREIGN KEY([idCertame])
REFERENCES [dbo].[Certame] ([idCertame])
GO

ALTER TABLE [dbo].[Stand] CHECK CONSTRAINT [FK_Stand_Certame]
GO

ALTER TABLE [dbo].[Stand]  WITH CHECK ADD  CONSTRAINT [FK_Stand_Empresa] FOREIGN KEY([idEmpresa])
REFERENCES [dbo].[Empresa] ([idEmpresa])
GO

ALTER TABLE [dbo].[Stand] CHECK CONSTRAINT [FK_Stand_Empresa]
GO

