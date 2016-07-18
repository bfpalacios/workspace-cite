USE [DBCITE]
GO

/****** Object:  StoredProcedure [dbo].[ActualizarEvento]    Script Date: 07/14/2016 07:31:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ActualizarNoticia]            
            @titulo VARCHAR(100),
            @descCorta VARCHAR(100),
            @descripcion TEXT ,
            @fecha Datetime,
            @archivo varbinary(max),
            @id int
        AS
            BEGIN
	
                UPDATE dbo.INFORMATIVO
                SET TITULO_INFORMATIVO = @titulo, 
                DESC_CORTA_INFORMATIVO = @descCorta,
                DESC_INFORMATIVO = @descripcion,
                FECHA = @fecha, 
                ARCHIVO_INFORMATIVO = @archivo
                WHERE ID_INFORMATIVO = @id
            END
GO


USE [DBCITE]
GO

/****** Object:  StoredProcedure [dbo].[ActualizarEvento]    Script Date: 07/14/2016 07:31:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ActualizarPublicacion]            
            @titulo VARCHAR(100),
            @descCorta VARCHAR(100),
            @descripcion TEXT ,
            @fecha Datetime,
            @archivo varbinary(max),
            @id int
        AS
            BEGIN
	
                UPDATE dbo.PUBLICACIONES
                SET TITULO_PUBLICACIONES = @titulo, 
                DESC_CORTA_PUBLICACIONES = @descCorta,
                DESC_PUBLICACIONES = @descripcion,
                FECHA = @fecha, 
                ARCHIVO_PUBLICACIONES = @archivo
                WHERE ID_PUBLICACIONES = @id
            END
GO

USE [DBCITE]
GO

/****** Object:  StoredProcedure [dbo].[ActualizarEvento]    Script Date: 07/14/2016 07:31:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[EliminarNoticia]
            @id int
        AS
            BEGIN
	
                DELETE FROM dbo.INFORMATIVO
                WHERE ID_INFORMATIVO = @id
            END
GO

USE [DBCITE]
GO

/****** Object:  StoredProcedure [dbo].[ActualizarEvento]    Script Date: 07/14/2016 07:31:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[EliminarPublicacion]
            @id int
        AS
            BEGIN
	
                DELETE FROM dbo.PUBLICACIONES
                WHERE ID_PUBLICACIONES = @id
            END
GO


USE [DBCITE]
GO

/****** Object:  StoredProcedure [dbo].[BuscarNoticias]    Script Date: 07/09/2016 14:31:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[BuscarNoticias]
    @titulo VARCHAR(100),
    @fecha DATE
AS
BEGIN
	IF(ISNULL(@titulo,'') = '' and ISNULL(@fecha,'1900-01-01') = '1900-01-01')
	 PRINT ''
	ELSE
		IF(ISNULL(@titulo,'') = '')
			SELECT	* FROM
				dbo.INFORMATIVO t WHERE CAST(t.FECHA AS DATE) = CAST(@fecha AS DATE)
		ELSE
			IF(ISNULL(@fecha,'1900-01-01') = '1900-01-01')
				SELECT	* FROM
					dbo.INFORMATIVO t WHERE t.TITULO_INFORMATIVO like '%' + @titulo + '%'
			ELSE
				SELECT	* FROM
				dbo.INFORMATIVO t
				WHERE t.TITULO_INFORMATIVO like '%' + @titulo + '%'
				AND CAST(t.FECHA AS DATE) = CAST(@fecha AS DATE)  			   
    END

GO


USE [DBCITE]
GO
/****** Object:  StoredProcedure [dbo].[BuscarPublicaciones]    Script Date: 07/09/2016 14:32:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[BuscarPublicaciones]
    @titulo VARCHAR(100),
    @fecha DATE
AS
BEGIN
	IF(ISNULL(@titulo,'') = '' and ISNULL(@fecha,'1900-01-01') = '1900-01-01')
	 PRINT ''
	ELSE
		IF(ISNULL(@titulo,'') = '')
			SELECT	* FROM
				dbo.PUBLICACIONES t WHERE CAST(t.FECHA AS DATE) = CAST(@fecha AS DATE)
		ELSE
			IF(ISNULL(@fecha,'1900-01-01') = '1900-01-01')
				SELECT	* FROM
					dbo.PUBLICACIONES t WHERE t.TITULO_PUBLICACIONES like '%' + @titulo + '%'
			ELSE
				SELECT	* FROM
				dbo.PUBLICACIONES t
				WHERE t.TITULO_PUBLICACIONES like '%' + @titulo + '%'
				AND CAST(t.FECHA AS DATE) = CAST(@fecha AS DATE)  			   
    END
	
	
	
	USE [DBCITE]
GO

/****** Object:  StoredProcedure [dbo].[ListarNoticias]    Script Date: 07/16/2016 22:23:08 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


ALTER PROCEDURE [dbo].[ListarNoticias]
    @cantidadRegistros int
AS
    BEGIN     
				SELECT	top (@cantidadRegistros) * 
				FROM
				dbo.INFORMATIVO	
				ORDER BY FECHA DESC	    
    END

GO


/****** Object:  StoredProcedure [dbo].[SP_Insertar_Detalle_Infomativo]    Script Date: 07/17/2016 19:45:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_Insertar_Detalle_Infomativo]
    @Titulo_Informativo VARCHAR(100) ,
    @Desc_Informativo TEXT ,
    @Desc_Corta_Informativo VARCHAR(100) ,
    @Fecha DATETIME ,
    @Archivo_Informativo VARBINARY(MAX)
AS
    BEGIN
        INSERT  dbo.INFORMATIVO
                ( TITULO_INFORMATIVO ,
                  DESC_INFORMATIVO ,
                  DESC_CORTA_INFORMATIVO ,
                  FECHA ,
                  ARCHIVO_INFORMATIVO
                )
        VALUES  ( @Titulo_Informativo ,
                  @Desc_Informativo ,
                  @Desc_Corta_Informativo ,
                  @Fecha ,
                  @Archivo_Informativo
                )
    END

/****** Object:  StoredProcedure [dbo].[SP_Insertar_Publicaciones]    Script Date: 07/17/2016 19:46:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[SP_Insertar_Publicaciones]
    @Titulo_Publicaciones VARCHAR(100) ,
    @Desc_Publicaciones TEXT ,
    @Desc_Corta_Publicaciones VARCHAR(100) ,
    @Fecha DATETIME ,
    @Archivo_Publicaciones VARBINARY(MAX)
AS
    BEGIN
        INSERT  dbo.PUBLICACIONES
                ( TITULO_PUBLICACIONES ,
                  DESC_PUBLICACIONES ,
                  DESC_CORTA_PUBLICACIONES ,
                  FECHA ,
                  ARCHIVO_PUBLICACIONES
                )
        VALUES  ( @Titulo_Publicaciones ,
                  @Desc_Publicaciones ,
                  @Desc_Corta_Publicaciones ,
                  @Fecha ,
                  @Archivo_Publicaciones
                )
    END



