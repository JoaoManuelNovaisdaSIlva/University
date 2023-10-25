USE [EasyFeira]
GO

/****** Object:  Table [dbo].[StandsDeCertame]    Script Date: 15/01/2023 23:18:40 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[StandsDeCertame](
	[idStand] [int] NOT NULL,
	[idCertame] [int] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[StandsDeCertame]  WITH CHECK ADD  CONSTRAINT [FK_StandsDeCertame_Certame] FOREIGN KEY([idCertame])
REFERENCES [dbo].[Certame] ([idCertame])
GO

ALTER TABLE [dbo].[StandsDeCertame] CHECK CONSTRAINT [FK_StandsDeCertame_Certame]
GO

ALTER TABLE [dbo].[StandsDeCertame]  WITH CHECK ADD  CONSTRAINT [FK_StandsDeCertame_Stand] FOREIGN KEY([idStand])
REFERENCES [dbo].[Stand] ([idStand])
GO

ALTER TABLE [dbo].[StandsDeCertame] CHECK CONSTRAINT [FK_StandsDeCertame_Stand]
GO

