USE [EasyFeira]
GO

/****** Object:  Table [dbo].[EmpresasDeCertame]    Script Date: 15/01/2023 23:18:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EmpresasDeCertame](
	[idCertame] [int] NOT NULL,
	[idEmpresa] [int] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpresasDeCertame]  WITH CHECK ADD  CONSTRAINT [FK_EmpresasDeCertame_Certame] FOREIGN KEY([idCertame])
REFERENCES [dbo].[Certame] ([idCertame])
GO

ALTER TABLE [dbo].[EmpresasDeCertame] CHECK CONSTRAINT [FK_EmpresasDeCertame_Certame]
GO

ALTER TABLE [dbo].[EmpresasDeCertame]  WITH CHECK ADD  CONSTRAINT [FK_EmpresasDeCertame_Empresa] FOREIGN KEY([idEmpresa])
REFERENCES [dbo].[Empresa] ([idEmpresa])
GO

ALTER TABLE [dbo].[EmpresasDeCertame] CHECK CONSTRAINT [FK_EmpresasDeCertame_Empresa]
GO

