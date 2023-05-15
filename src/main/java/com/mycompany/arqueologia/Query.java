
package com.mycompany.arqueologia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Query{
    // Valores para la conexión a la base de datos (su nombre, URL, Usuario y Contraseña)
    private static final String DB_NAME = "arqueologiaDB";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    private static final String DB_USER = "postgres";
    private static final String DB_PWD = "123456";

    // Objetos utilizados para interactuar con la base de datos
    // (conexión, realizar consultas con y sin parámetros, y recibir los resultados)
    private static Connection conn = null;
    private static Statement query = null;
    private static PreparedStatement p_query = null;
    private static ResultSet result = null;

    
 public Query() throws SQLException {
        // Una vez creado el formulario e inicializado sus componentes ↑↑↑
        // nos enlazamos con el DBMS para conectarnos a la base de datos solicitada
        // utilizando las credenciales correspondientes
        try{
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Hubo un error en la conexion con la base de datos", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        // una vez conectados, nuestro programa creará las tabas que sean necesarias
        // para funcionar, en el caso de que ya no estén creadas
        // (de ahí el "IF NOT EXISTS" luego del "CREATE TABLE").
        // En este ejemplo básico vamos a crear la tabla "ejemplo_personas"
        query = conn.createStatement();
        
        query.execute("CREATE TABLE IF NOT EXISTS Sitios("
                + "S_Cod VARCHAR(50) PRIMARY KEY,"
                + "S_Localidad VARCHAR(50) NOT NULL)"
        );
        
        query.execute("CREATE TABLE IF NOT EXISTS Cuadriculas("
                + "Cu_Cod VARCHAR(50) PRIMARY KEY,"
                + "S_Cod_Dividido VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (S_Cod_Dividido) REFERENCES Sitios(S_Cod))"
        );
        
        query.execute("CREATE TABLE IF NOT EXISTS Cajas("
                + "Ca_Cod VARCHAR(50) PRIMARY KEY,"
                + "Ca_Fecha DATE NOT NULL,"
                + "Ca_Lugar VARCHAR(50) NOT NULL)"
        );
        
        query.execute("CREATE TABLE IF NOT EXISTS Personas("
                + "P_Dni INT PRIMARY KEY,"
                + "P_Nombre VARCHAR(50) NOT NULL,"
                + "P_Apellido VARCHAR(50) NOT NULL,"
                + "P_Email VARCHAR(50) NOT NULL,"
                + "P_Telefono VARCHAR(50) NOT NULL)"
        );
        
        query.execute("CREATE TABLE IF NOT EXISTS Objetos("
                + "O_Cod VARCHAR(50) PRIMARY KEY,"
                + "O_Nombre VARCHAR(50) NOT NULL,"
                + "O_Tipoextraccion VARCHAR(50) NOT NULL,"
                + "O_Alto INT NOT NULL,"
                + "O_Largo INT NOT NULL,"
                + "O_Espesor INT NOT NULL,"
                + "O_Peso INT NOT NULL,"
                + "O_Cantidad INT NOT NULL,"
                + "O_Fecharegistro VARCHAR(50) NOT NULL,"
                + "O_Descripcion VARCHAR(50) NOT NULL,"
                + "O_Origen VARCHAR(50) NOT NULL,"
                + "CU_Cod_Asocia VARCHAR(50) NOT NULL,"
                + "Ca_Cod_Contiene VARCHAR(50) NOT NULL,"
                + "P_Dni_Ingresa INT NOT NULL,"
                + "O_Es CHAR(1) NOT NULL,"
                + "FOREIGN KEY (CU_Cod_Asocia) REFERENCES Cuadriculas(Cu_Cod),"
                + "FOREIGN KEY (Ca_Cod_Contiene) REFERENCES Cajas(Ca_Cod),"
                + "FOREIGN KEY (P_Dni_Ingresa) REFERENCES Personas(P_Dni))"
        );
        
        query.execute("CREATE TABLE IF NOT EXISTS Liticos("
                + "O_Cod VARCHAR(50) PRIMARY KEY,"
                + "L_Fechacreacion INT NOT NULL,"
                + "FOREIGN KEY (O_Cod) REFERENCES Objetos(O_Cod))"
        );
        
        query.execute("CREATE TABLE IF NOT EXISTS Ceramicos("
                + "O_Cod VARCHAR(50) PRIMARY KEY,"
                + "L_Color VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (O_Cod) REFERENCES Objetos(O_Cod))"
        );
        
        try{
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST1', 'Orange');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST2', 'White Plains');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST3', 'Eugene');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST4', 'Las Vegas');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST5', 'Honolulu');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST6', 'New Orleans');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST7', 'Wichita');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST8', 'New York City');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST9', 'Oklahoma City');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST10', 'Sacramento');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST11', 'Dallas');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST12', 'Kansas City');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST13', 'Lake Worth');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST14', 'Syracuse');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST15', 'Arlington');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST16', 'Trenton');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST17', 'San Jose');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST18', 'Washington');");
            query.execute("INSERT INTO Sitios (s_cod, s_localidad) VALUES ('ST19', 'Prescott');");
            
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU1', 'ST4');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU2', 'ST1');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU3', 'ST11');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU4', 'ST13');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU5', 'ST11');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU6', 'ST11');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU7', 'ST2');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU8', 'ST9');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU9', 'ST3');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU10', 'ST19');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU11', 'ST8');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU12', 'ST12');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU13', 'ST14');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU14', 'ST18');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU15', 'ST8');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU16', 'ST5');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU17', 'ST7');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU18', 'ST16');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU19', 'ST16');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU20', 'ST7');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU21', 'ST3');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU22', 'ST14');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU23', 'ST1');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU24', 'ST11');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU25', 'ST5');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU26', 'ST10');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU27', 'ST12');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU28', 'ST11');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU29', 'ST7');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU30', 'ST9');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU31', 'ST13');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU32', 'ST16');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU33', 'ST12');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU34', 'ST5');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU35', 'ST11');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU36', 'ST14');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU37', 'ST7');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU38', 'ST8');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU39', 'ST10');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU40', 'ST8');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU41', 'ST15');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU42', 'ST12');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU43', 'ST2');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU44', 'ST7');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU45', 'ST18');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU46', 'ST8');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU47', 'ST7');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU48', 'ST1');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU49', 'ST17');");
            query.execute("INSERT INTO Cuadriculas (Cu_Cod, S_Cod_Dividido) VALUES ('CU50', 'ST18');");
            

            
            
            

            JOptionPane.showMessageDialog(null,"Se han realizado las operaciones correctamente", "Primera Inicializacion", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){}
        
        // Inicializamos/Actualizamos la lista de personas del formulario
        // para que muestre las personas que ya están cargadas en el sistema
        //updateListaResultados();
 }
}

