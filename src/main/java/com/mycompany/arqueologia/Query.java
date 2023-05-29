
package com.mycompany.arqueologia;

import java.awt.List;
import java.sql.Array;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public abstract class Query{
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

    
    public static void initQuery() throws SQLException {
        // Una vez creado el formulario e inicializado sus componentes ↑↑↑
        // nos enlazamos con el DBMS para conectarnos a la base de datos solicitada
        // utilizando las credenciales correspondientes
        
        try{
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
        }catch (SQLException e){
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
                + "O_Alto DOUBLE PRECISION NOT NULL,"
                + "O_Largo DOUBLE PRECISION NOT NULL,"
                + "O_Espesor DOUBLE PRECISION NOT NULL,"
                + "O_Peso DOUBLE PRECISION NOT NULL,"
                + "O_Cantidad INT NOT NULL,"
                + "O_Fecharegistro DATE NOT NULL,"
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
                + "C_Color VARCHAR(50) NOT NULL,"
                + "FOREIGN KEY (O_Cod) REFERENCES Objetos(O_Cod))"
        );
        
        try{
            
            // Inserts en tabla Sitios
            
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
            
            // Inserts en tabla Cuadriculas
            
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
            
            // Inserts en tabla Cajas
            
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA1', '24-07-2022', 'Zona6');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA2', '03-11-2022', 'Zona1');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA3', '11-08-2022', 'Zona7');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA4', '12-05-2022', 'Zona4');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA5', '30-06-2022', 'Zona3');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA6', '25-10-2022', 'Zona5');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA7', '15-06-2022', 'Zona3');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA8', '07-12-2022', 'Zona1');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA9', '03-02-2023', 'Zona8');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA10', '29-03-2023', 'Zona1');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA11', '07-04-2023', 'Zona2');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA12', '22-03-2023', 'Zona7');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA13', '09-01-2023', 'Zona3');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA14', '17-06-2022', 'Zona8');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA15', '16-08-2022', 'Zona10');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA16', '22-03-2023', 'Zona8');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA17', '22-10-2022', 'Zona1');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA18', '12-12-2022', 'Zona4');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA19', '30-04-2023', 'Zona4');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA20', '28-03-2023', 'Zona1');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA21', '24-03-2023', 'Zona4');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA22', '10-06-2022', 'Zona5');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA23', '25-05-2022', 'Zona7');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA24', '19-04-2023', 'Zona5');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA26', '17-04-2023', 'Zona7');");
            query.execute("INSERT INTO Cajas (Ca_Cod, Ca_Fecha, Ca_Lugar) VALUES ('CA25', '01-04-2023', 'Zona7');");
            
            // Inserts en tabla Personas
            
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (34975409, 'Englebert', 'Giggs', 'egiggs0@unblog.fr', '1836085564');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (77844209, 'Jordan', 'Callear', 'jcallear1@ed.gov', '6503126502');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (40417189, 'Benji', 'Colchett', 'bcolchett2@myspace.com', '2136409452');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (15269037, 'Jessa', 'Pennycook', 'jpennycook3@ebay.com', '1241706587');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (11790016, 'Mary', 'Burkart', 'mburkart4@hhs.gov', '6303516894');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (7422764, 'Jacky', 'Allwood', 'jallwood5@godaddy.com', '6198786633');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (22718194, 'Parry', 'Lanchberry', 'planchberry6@cmu.edu', '8032544421');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (84021424, 'Si', 'Tackell', 'stackell7@mit.edu', '3256870843');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (16823037, 'Ado', 'Goldstraw', 'agoldstraw8@a8.net', '1798329827');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (84005629, 'Evyn', 'De Blase', 'edeblase9@dedecms.com', '9943545249');");
            
            // Inserts en tabla Objetos
            
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ1', 'Hacha de piedra pulida', 'Sondeo', 9.34, 9.72, 4.77, 6.02, 56, '22-09-2022', 'Conjunc/lid adhesiolysis', 'Cultura Natufiense', 'CU27', 'CA6', 15269037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ2', 'Cuchillo de obsidiana', 'Sondeo', 1.52, 5.04, 1.37, 6.55, 27, '17-10-2022', 'C-vasc scan/isotop funct', 'Cultura Acheulense', 'CU23', 'CA11', 84021424, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ3', 'Punta de flecha de sílex', 'Análisis de superficie', 5.77, 7.08, 3.68, 4.5, 76, '24-08-2022', 'Inc soft tissue hand NEC', 'Cultura Clovis', 'CU35', 'CA11', 11790016, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ4', 'Martillo de piedra', 'Prospección geofísica', 6.29, 5.68, 8.94, 4.3, 72, '27-01-2023', 'Root canal w irrigation', 'Cultura Maya', 'CU36', 'CA16', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ5', 'Mortero de piedra para moler', 'Levantamiento arqueológico', 1.15, 2.41, 1.63, 3.0, 6, '09-04-2023', 'Tendon excision for grft', 'Cultura Moche', 'CU42', 'CA23', 11790016, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ6', 'Azuela de piedra', 'Sondeo', 7.48, 3.97, 8.06, 9.26, 82, '06-01-2023', 'Closed renal biopsy', 'Cultura Natufiense', 'CU46', 'CA25', 11790016, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ7', 'Cincel de piedra', 'Prospección geofísica', 6.22, 8.33, 9.88, 5.18, 48, '04-09-2022', 'Micro exam-uppr urin NEC', 'Cultura Moche', 'CU11', 'CA14', 16823037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ8', 'Muela de mano de piedra', 'Análisis de superficie', 6.61, 1.55, 6.16, 3.57, 64, '20-06-2022', 'Diphtheria toxoid admin', 'Cultura Moche', 'CU15', 'CA9', 84021424, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ9', 'Piedra de afilar', 'Sondeo', 5.96, 8.64, 7.03, 9.52, 74, '12-01-2023', 'Vag reconst w grft/pros', 'Cultura Neolítica', 'CU33', 'CA13', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ10', 'Hacha de mano de piedra', 'Análisis de superficie', 8.57, 5.87, 2.73, 5.45, 80, '11-03-2023', 'Ventricl shunt-abdomen', 'Cultura Jomon', 'CU8', 'CA18', 40417189, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ11', 'Olla de barro', 'Levantamiento arqueológico', 8.39, 6.75, 3.44, 2.07, 74, '23-12-2022', 'Repair eyeball rupture', 'Cultura Neolítica', 'CU26', 'CA20', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ12', 'Plato de cerámica', 'Sondeo', 4.6, 9.84, 6.04, 4.87, 67, '14-08-2022', 'Open robotic assist proc', 'Cultura Natufiense', 'CU2', 'CA24', 40417189, 'C');");  
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ13', 'Cuenco de cerámica', 'Sondeo', 3.23, 8.16, 9.14, 7.6, 85, '30-06-2022', 'Simple suture of dura', 'Cultura Jomon', 'CU46', 'CA17', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ14', 'Vasija de cerámica', 'Sondeo', 2.2, 3.86, 6.15, 3.41, 88, '16-10-2022', 'Glaucoma testing', 'Cultura Moche', 'CU18', 'CA12', 40417189, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ15', 'Jarro de cerámica', 'Prospección geofísica', 8.14, 9.06, 4.63, 2.14, 88, '30-12-2022', 'Fetal pulse oximetry', 'Cultura Jomon', 'CU2', 'CA3', 77844209, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ16', 'Figurilla de cerámica', 'Sondeo', 4.48, 9.02, 7.6, 7.22, 34, '16-02-2023', 'Magnet removal cornea FB', 'Cultura Moche', 'CU7', 'CA24', 34975409, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ17', 'Estatuilla de cerámica', 'Sondeo', 9.91, 5.46, 6.18, 2.95, 16, '09-03-2023', 'Parathyroid scan', 'Cultura Acheulense', 'CU21', 'CA15', 16823037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ18', 'Jarron de cerámica', 'Excavación', 5.16, 9.87, 2.43, 6.03, 57, '18-01-2023', 'Bowel diagnost proc NEC', 'Cultura Natufiense', 'CU26', 'CA22', 34975409, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ19', 'Tazon de cerámica', 'Sondeo', 5.43, 9.71, 9.86, 4.99, 75, '10-02-2023', 'Peritoneal lavage', 'Cultura Clovis', 'CU20', 'CA3', 11790016, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ20', 'Cántaro de cerámica', 'Levantamiento arqueológico', 2.55, 3.4, 2.37, 2.91, 53, '16-07-2022', 'Hemorrhage control NOS', 'Cultura Acheulense', 'CU19', 'CA18', 84005629, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ21', 'Hacha de piedra pulida', 'Prospección geofísica', 3.93, 8.4, 4.76, 3.09, 41, '16-07-2022', 'Implt cochlear prost NOS', 'Cultura Neolítica', 'CU26', 'CA2', 34975409, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ22', 'Cuchillo de obsidiana', 'Levantamiento arqueológico', 7.71, 5.46, 1.25, 4.84, 47, '18-10-2022', 'Lid recons entropion rep', 'Cultura Neolítica', 'CU11', 'CA8', 15269037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ23', 'Punta de flecha de sílex', 'Análisis de superficie', 9.35, 3.93, 5.75, 5.89, 7, '01-03-2023', 'Cruciate lig repair NEC', 'Cultura Natufiense', 'CU22', 'CA9', 84021424, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ24', 'Martillo de piedra', 'Levantamiento arqueológico', 1.28, 7.89, 4.5, 1.48, 13, '02-05-2023', 'Anesth inject-spin canal', 'Cultura Clovis', 'CU22', 'CA13', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ25', 'Mortero de piedra para moler', 'Excavación', 6.12, 1.39, 9.65, 1.04, 7, '28-05-2022', 'Vagin/cul-de-sac dx NEC', 'Cultura Clovis', 'CU11', 'CA9', 84021424, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ26', 'Azuela de piedra', 'Sondeo', 9.0, 4.85, 9.0, 7.91, 45, '14-04-2023', 'Bronch/trach lavage NEC', 'Cultura Natufiense', 'CU50', 'CA18', 34975409, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ27', 'Cincel de piedra', 'Prospección geofísica', 1.5, 6.56, 5.16, 3.89, 34, '21-05-2022', 'Remove ocular implant', 'Cultura Acheulense', 'CU42', 'CA23', 84021424, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ28', 'Muela de mano de piedra', 'Análisis de superficie', 5.4, 4.98, 3.73, 1.66, 49, '25-12-2022', 'Attach pedicle to hand', 'Cultura Acheulense', 'CU10', 'CA18', 34975409, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ29', 'Piedra de afilar', 'Excavación', 7.73, 9.29, 4.18, 3.18, 68, '03-01-2023', 'Tooth restorat by inlay', 'Cultura Jomon', 'CU38', 'CA20', 77844209, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ30', 'Hacha de mano de piedra', 'Prospección geofísica', 4.16, 7.05, 6.69, 9.01, 53, '31-08-2022', 'Ureteral meatus dilation', 'Cultura Moche', 'CU48', 'CA11', 84021424, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ31', 'Olla de barro', 'Análisis de superficie', 1.41, 1.35, 8.17, 2.1, 60, '16-08-2022', 'C & s-lower GI', 'Cultura Neolítica', 'CU34', 'CA24', 15269037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ32', 'Plato de cerámica', 'Sondeo', 3.04, 1.46, 9.79, 4.44, 70, '28-04-2023', 'Lid marg recon-part thic', 'Cultura Neolítica', 'CU4', 'CA12', 11790016, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ33', 'Cuenco de cerámica', 'Prospección geofísica', 9.31, 8.55, 1.65, 7.79, 48, '09-09-2022', 'Lap gastroenterostomy', 'Cultura Neolítica', 'CU50', 'CA16', 22718194, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ34', 'Vasija de cerámica', 'Sondeo', 5.61, 7.86, 9.41, 7.9, 11, '04-09-2022', 'Remov intrauterine pack', 'Cultura Maya', 'CU22', 'CA8', 11790016, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ35', 'Jarro de cerámica', 'Prospección geofísica', 2.58, 4.91, 6.68, 1.09, 61, '26-10-2022', 'Salivary duct probing', 'Cultura Jomon', 'CU44', 'CA22', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ36', 'Figurilla de cerámica', 'Análisis de superficie', 8.5, 8.55, 4.48, 6.56, 32, '31-01-2023', 'Debrid opn fx-finger', 'Cultura Neolítica', 'CU8', 'CA1', 34975409, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ37', 'Estatuilla de cerámica', 'Prospección geofísica', 5.91, 3.68, 4.24, 1.37, 19, '12-11-2022', 'Lmb/lmbsac fus post/post', 'Cultura Natufiense', 'CU9', 'CA14', 40417189, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ38', 'Jarron de cerámica', 'Prospección geofísica', 3.93, 8.53, 9.77, 7.63, 32, '11-10-2022', 'Oth arthrotomy-shoulder', 'Cultura Acheulense', 'CU49', 'CA9', 77844209, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ39', 'Tazon de cerámica', 'Excavación', 1.38, 6.63, 8.98, 8.76, 77, '19-10-2022', 'Exc ectopic breast tissu', 'Cultura Clovis', 'CU29', 'CA20', 40417189, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ40', 'Cántaro de cerámica', 'Análisis de superficie', 8.34, 7.02, 1.17, 9.76, 52, '21-12-2022', 'Routine psychiat visit', 'Cultura Jomon', 'CU2', 'CA7', 40417189, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ41', 'Hacha de piedra pulida', 'Levantamiento arqueológico', 7.39, 2.95, 5.73, 6.24, 31, '08-06-2022', 'Atria septa def rep NEC', 'Cultura Natufiense', 'CU48', 'CA12', 77844209, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ42', 'Cuchillo de obsidiana', 'Levantamiento arqueológico', 3.91, 2.64, 2.14, 7.03, 23, '12-11-2022', 'Orchiopexy', 'Cultura Clovis', 'CU49', 'CA12', 7422764, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ43', 'Punta de flecha de sílex', 'Análisis de superficie', 4.32, 8.5, 8.81, 9.8, 59, '19-05-2022', 'Hand synovectomy', 'Cultura Natufiense', 'CU18', 'CA9', 77844209, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ44', 'Martillo de piedra', 'Prospección geofísica', 9.12, 2.01, 8.15, 7.12, 82, '18-07-2022', 'Vessel resect/replac NOS', 'Cultura Clovis', 'CU47', 'CA9', 22718194, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ45', 'Mortero de piedra para moler', 'Levantamiento arqueológico', 5.69, 8.6, 6.19, 8.64, 26, '05-03-2023', 'Evac ob hemat vulva/vag', 'Cultura Moche', 'CU14', 'CA24', 7422764, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ46', 'Azuela de piedra', 'Análisis de superficie', 2.01, 8.04, 5.58, 3.26, 23, '08-08-2022', 'Incision of uvula', 'Cultura Maya', 'CU31', 'CA6', 11790016, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ47', 'Cincel de piedra', 'Levantamiento arqueológico', 2.18, 7.59, 6.02, 6.9, 91, '26-05-2022', 'Ext ear diagnos proc NEC', 'Cultura Jomon', 'CU43', 'CA2', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ48', 'Muela de mano de piedra', 'Prospección geofísica', 9.1, 9.55, 4.02, 9.85, 23, '26-03-2023', 'Saliv glnd dx proc NEC', 'Cultura Clovis', 'CU8', 'CA23', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ49', 'Piedra de afilar', 'Análisis de superficie', 9.29, 3.28, 6.79, 9.42, 77, '02-04-2023', 'Cystotomy & adhesiolysis', 'Cultura Natufiense', 'CU15', 'CA20', 77844209, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ50', 'Hacha de mano de piedra', 'Levantamiento arqueológico', 8.54, 5.24, 9.1, 9.89, 18, '21-01-2023', 'Vaginal obliteration', 'Cultura Natufiense', 'CU27', 'CA18', 84021424, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ51', 'Olla de barro', 'Excavación', 8.92, 4.79, 5.95, 4.26, 33, '02-10-2022', 'Ureteroscopy', 'Cultura Jomon', 'CU1', 'CA12', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ52', 'Plato de cerámica', 'Prospección geofísica', 3.03, 1.67, 8.57, 3.08, 27, '19-04-2023', 'Rad excis ext ear les', 'Cultura Maya', 'CU23', 'CA20', 84005629, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ53', 'Cuenco de cerámica', 'Análisis de superficie', 6.43, 8.29, 9.72, 6.87, 33, '18-12-2022', 'Arth/pros rem wo rep-hip', 'Cultura Moche', 'CU12', 'CA5', 84021424, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ54', 'Vasija de cerámica', 'Excavación', 5.85, 1.94, 6.93, 5.33, 67, '17-08-2022', 'Orbitotomy w bone flap', 'Cultura Clovis', 'CU25', 'CA14', 84021424, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ55', 'Jarro de cerámica', 'Análisis de superficie', 9.35, 1.59, 10.0, 8.68, 11, '02-07-2022', 'Remov urin drainage NEC', 'Cultura Maya', 'CU19', 'CA12', 84005629, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ56', 'Figurilla de cerámica', 'Análisis de superficie', 4.88, 8.07, 4.48, 1.15, 13, '12-09-2022', 'Cord bld stem cell trans', 'Cultura Neolítica', 'CU37', 'CA19', 34975409, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ57', 'Estatuilla de cerámica', 'Excavación', 1.81, 4.46, 9.55, 9.02, 53, '24-03-2023', 'Total knee replacement', 'Cultura Neolítica', 'CU30', 'CA11', 84005629, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ58', 'Jarron de cerámica', 'Levantamiento arqueológico', 3.0, 2.0, 6.49, 6.83, 68, '04-05-2022', 'Contrast x-ray of orbit', 'Cultura Natufiense', 'CU5', 'CA11', 84021424, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ59', 'Tazon de cerámica', 'Sondeo', 8.91, 2.37, 7.7, 1.81, 90, '30-12-2022', 'Insert tissue expander', 'Cultura Maya', 'CU24', 'CA1', 15269037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ60', 'Cántaro de cerámica', 'Análisis de superficie', 6.02, 1.56, 4.51, 4.18, 55, '08-10-2022', 'Remov intralum pharyn FB', 'Cultura Jomon', 'CU27', 'CA21', 84005629, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ61', 'Hacha de piedra pulida', 'Análisis de superficie', 1.86, 4.93, 5.84, 9.52, 88, '25-04-2023', 'Perc abltn lung les/tiss', 'Cultura Natufiense', 'CU1', 'CA19', 77844209, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ62', 'Cuchillo de obsidiana', 'Sondeo', 5.05, 3.94, 1.3, 3.89, 21, '10-03-2023', 'Skel xray-elbow/forearm', 'Cultura Moche', 'CU33', 'CA9', 16823037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ63', 'Punta de flecha de sílex', 'Análisis de superficie', 1.34, 2.95, 6.79, 8.04, 15, '08-10-2022', 'Percutan hrt assist syst', 'Cultura Clovis', 'CU47', 'CA24', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ64', 'Martillo de piedra', 'Excavación', 4.34, 3.3, 6.84, 2.18, 41, '05-02-2023', 'Rev hip repl-fem comp', 'Cultura Jomon', 'CU36', 'CA11', 11790016, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ65', 'Mortero de piedra para moler', 'Excavación', 5.75, 2.67, 1.42, 9.31, 38, '21-10-2022', 'Clos uterine ligament bx', 'Cultura Natufiense', 'CU48', 'CA4', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ66', 'Azuela de piedra', 'Excavación', 1.73, 4.0, 1.78, 1.48, 76, '19-12-2022', 'Simple mastoidectomy', 'Cultura Clovis', 'CU3', 'CA9', 84005629, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ67', 'Cincel de piedra', 'Análisis de superficie', 1.07, 5.38, 7.65, 2.03, 46, '27-02-2023', 'Inf liquid brachy isotop', 'Cultura Moche', 'CU46', 'CA22', 34975409, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ68', 'Muela de mano de piedra', 'Sondeo', 6.0, 4.51, 9.24, 8.42, 59, '09-09-2022', 'Intravs msmt ves NEC/NOS', 'Cultura Jomon', 'CU19', 'CA9', 15269037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ69', 'Piedra de afilar', 'Excavación', 3.13, 4.94, 1.05, 8.04, 3, '24-01-2023', 'Imp/rep mchan cochl pros', 'Cultura Acheulense', 'CU8', 'CA13', 34975409, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ70', 'Hacha de mano de piedra', 'Prospección geofísica', 4.63, 2.89, 4.68, 7.95, 1, '24-09-2022', 'Breast operation NEC', 'Cultura Natufiense', 'CU29', 'CA23', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ71', 'Olla de barro', 'Sondeo', 10.0, 8.95, 3.49, 7.73, 58, '05-09-2022', 'Orthodon applianc applic', 'Cultura Natufiense', 'CU28', 'CA22', 7422764, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ72', 'Plato de cerámica', 'Sondeo', 5.17, 2.66, 1.9, 4.64, 76, '01-08-2022', 'Lap adhesiolys ova/tube', 'Cultura Clovis', 'CU49', 'CA4', 15269037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ73', 'Cuenco de cerámica', 'Excavación', 8.57, 5.58, 2.66, 5.0, 67, '26-03-2023', 'Eyelid biopsy', 'Cultura Acheulense', 'CU41', 'CA19', 40417189, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ74', 'Vasija de cerámica', 'Sondeo', 4.61, 4.35, 6.68, 1.87, 35, '24-12-2022', 'Eyelid epilation NEC', 'Cultura Moche', 'CU31', 'CA13', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ75', 'Jarro de cerámica', 'Prospección geofísica', 1.81, 4.26, 1.17, 1.69, 83, '25-04-2023', 'Vesicostomy', 'Cultura Natufiense', 'CU41', 'CA3', 15269037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ76', 'Figurilla de cerámica', 'Análisis de superficie', 4.71, 7.68, 7.91, 4.55, 5, '28-12-2022', 'Lung repair NEC', 'Cultura Neolítica', 'CU45', 'CA15', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ77', 'Estatuilla de cerámica', 'Prospección geofísica', 9.38, 9.0, 3.73, 6.89, 24, '22-08-2022', 'Partial esophagectomy', 'Cultura Acheulense', 'CU41', 'CA4', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ78', 'Jarron de cerámica', 'Prospección geofísica', 7.18, 3.71, 6.84, 7.89, 55, '19-07-2022', 'Peritoneal suture', 'Cultura Natufiense', 'CU27', 'CA19', 84005629, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ79', 'Tazon de cerámica', 'Análisis de superficie', 8.74, 1.19, 4.62, 6.83, 58, '13-12-2022', 'Intravas msmt periph art', 'Cultura Moche', 'CU25', 'CA16', 40417189, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ80', 'Cántaro de cerámica', 'Prospección geofísica', 4.62, 3.78, 7.36, 9.01, 62, '06-08-2022', 'Catarac phacoemuls/aspir', 'Cultura Neolítica', 'CU30', 'CA1', 7422764, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ81', 'Hacha de piedra pulida', 'Excavación', 1.25, 4.81, 8.76, 6.29, 80, '23-08-2022', 'Uterus/adnexa repair NEC', 'Cultura Jomon', 'CU48', 'CA18', 15269037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ82', 'Cuchillo de obsidiana', 'Sondeo', 1.41, 7.81, 6.64, 6.49, 64, '30-08-2022', 'Periton pneumogram NEC', 'Cultura Moche', 'CU29', 'CA13', 77844209, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ83', 'Punta de flecha de sílex', 'Prospección geofísica', 2.67, 1.32, 4.55, 5.87, 4, '11-10-2022', 'Remove cervical cerclage', 'Cultura Acheulense', 'CU5', 'CA3', 40417189, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ84', 'Martillo de piedra', 'Análisis de superficie', 5.64, 6.2, 3.47, 9.31, 78, '11-06-2022', 'Closed thyroid gland bx', 'Cultura Moche', 'CU32', 'CA5', 34975409, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ85', 'Mortero de piedra para moler', 'Levantamiento arqueológico', 5.7, 6.8, 2.26, 6.88, 38, '29-03-2023', 'Remov therapeut dev NEC', 'Cultura Natufiense', 'CU28', 'CA2', 84021424, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ86', 'Azuela de piedra', 'Prospección geofísica', 8.28, 4.99, 2.96, 1.76, 26, '27-03-2023', 'Intravasc msmnt cor art', 'Cultura Moche', 'CU14', 'CA5', 7422764, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ87', 'Cincel de piedra', 'Prospección geofísica', 9.58, 8.76, 4.78, 6.04, 95, '27-05-2022', 'Toxicology-upper GI', 'Cultura Neolítica', 'CU32', 'CA11', 22718194, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ88', 'Muela de mano de piedra', 'Levantamiento arqueológico', 9.37, 3.54, 3.12, 4.87, 14, '07-02-2023', 'CAS w CT/CTA', 'Cultura Clovis', 'CU25', 'CA11', 11790016, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ89', 'Piedra de afilar', 'Análisis de superficie', 6.72, 1.74, 1.88, 7.19, 90, '19-10-2022', 'Thoracic spine x-ray NEC', 'Cultura Maya', 'CU31', 'CA8', 15269037, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ90', 'Hacha de mano de piedra', 'Sondeo', 6.07, 7.99, 2.07, 6.91, 32, '16-08-2022', 'Repair ob laceration NEC', 'Cultura Neolítica', 'CU44', 'CA6', 7422764, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ91', 'Olla de barro', 'Levantamiento arqueológico', 4.09, 7.39, 1.03, 4.98, 4, '02-08-2022', 'Perc mtrl vlv repr w imp', 'Cultura Maya', 'CU44', 'CA7', 40417189, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ92', 'Plato de cerámica', 'Prospección geofísica', 6.64, 2.08, 8.63, 9.47, 99, '30-06-2022', 'Opn pulmon valvuloplasty', 'Cultura Neolítica', 'CU12', 'CA6', 22718194, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ93', 'Cuenco de cerámica', 'Prospección geofísica', 7.58, 7.61, 9.33, 3.87, 64, '31-12-2022', 'Tonsillectomy/adenoidec', 'Cultura Acheulense', 'CU27', 'CA20', 16823037, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ94', 'Vasija de cerámica', 'Levantamiento arqueológico', 6.45, 8.77, 6.48, 4.36, 84, '03-02-2023', 'Esophagoscopy by incis', 'Cultura Neolítica', 'CU44', 'CA8', 40417189, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ95', 'Jarro de cerámica', 'Análisis de superficie', 8.37, 9.05, 9.53, 5.88, 63, '25-04-2023', 'Periurethral biopsy', 'Cultura Natufiense', 'CU2', 'CA23', 22718194, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ96', 'Figurilla de cerámica', 'Sondeo', 7.14, 7.54, 4.34, 9.48, 76, '13-08-2022', 'Excision of elbow NEC', 'Cultura Moche', 'CU26', 'CA9', 7422764, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ97', 'Estatuilla de cerámica', 'Excavación', 8.67, 9.09, 3.19, 8.07, 13, '12-04-2023', 'Subtotal mastectomy', 'Cultura Moche', 'CU21', 'CA8', 7422764, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ98', 'Jarron de cerámica', 'Levantamiento arqueológico', 1.79, 6.51, 4.25, 7.66, 93, '10-08-2022', 'Resection of nose', 'Cultura Clovis', 'CU37', 'CA4', 7422764, 'C');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ99', 'Tazon de cerámica', 'Análisis de superficie', 4.61, 4.52, 7.54, 7.08, 88, '22-06-2022', 'Superficial radiation', 'Cultura Acheulense', 'CU31', 'CA12', 40417189, 'L');");
            query.execute("INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES ('OBJ100', 'Cántaro de cerámica', 'Análisis de superficie', 2.84, 9.31, 3.36, 7.26, 82, '15-09-2022', 'Venous cath NEC', 'Cultura Maya', 'CU33', 'CA20', 40417189, 'L');");
            
            // Inserts en tabla Liticos
            
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ1', 861);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ2', 214);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ3', 442);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ5', 184);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ8', 751);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ15', 391);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ18', 909);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ20', 832);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ21', 131);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ23', 466);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ24', 949);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ26', 554);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ28', 505);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ29', 228);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ30', 138);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ32', 136);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ33', 693);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ37', 354);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ41', 294);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ43', 369);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ44', 495);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ45', 165);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ47', 845);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ48', 905);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ52', 134);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ54', 576);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ55', 228);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ59', 217);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ63', 606);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ64', 770);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ65', 675);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ67', 908);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ69', 809);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ71', 608);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ74', 334);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ75', 785);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ76', 213);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ77', 442);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ78', 633);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ79', 734);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ81', 998);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ83', 265);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ85', 971);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ86', 187);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ91', 748);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ92', 496);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ93', 431);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ94', 259);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ95', 576);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ96', 153);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ97', 556);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ99', 163);");
            query.execute("INSERT INTO Liticos (O_cod, L_fechacreacion) VALUES ('OBJ100', 414);");
            
            // Inserts en tabla Ceramicos
            
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ4', 'blanco');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ6', 'azul');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ7', 'marrón');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ9', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ10', 'blanco');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ11', 'púrpura');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ12', 'verde');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ13', 'rosa');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ14', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ16', 'rojo');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ17', 'púrpura');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ19', 'púrpura');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ22', 'amarillo');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ25', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ27', 'marrón');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ31', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ34', 'blanco');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ35', 'amarillo');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ36', 'azul');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ38', 'negro');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ39', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ40', 'blanco');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ42', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ46', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ49', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ50', 'verde');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ51', 'púrpura');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ53', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ56', 'púrpura');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ57', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ58', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ60', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ61', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ62', 'rojo');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ66', 'rosa');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ68', 'negro');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ70', 'negro');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ72', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ73', 'rosa');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ80', 'negro');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ82', 'gris');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ84', 'verde');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ87', 'blanco');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ88', 'púrpura');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ89', 'naranja');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ90', 'rojo');");
            query.execute("INSERT INTO Ceramicos (O_cod, C_color) VALUES ('OBJ98', 'púrpura');");
            
            // Insert del investigador Rodolphe Rominov y Delete del investigador Benji Colchett
            //query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (11790016, 'Mary', 'Burkart', 'mburkart4@hhs.gov', '6303516894');");
            query.execute("INSERT INTO Personas (P_Dni, P_Nombre, P_Apellido, P_Email, P_Telefono) VALUES (25544555, 'Rodolphe', 'Rominov', 'rrominovm@sciencedaily.com', '7135986253');");
            query.execute("DELETE FROM Personas WHERE P_Nombre = 'Benji' AND P_Apellido = 'Colchett';");
            
            JOptionPane.showMessageDialog(null,"Se han realizado las operaciones correctamente", "Primera Inicializacion", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(SQLException e){
            System.out.println("Error"+e.getMessage());
        }
        
        // Inicializamos/Actualizamos la lista de personas del formulario
        // para que muestre las personas que ya están cargadas en el sistema
        for (int i=0;i<5;i++){
            updateListaResultados(i);
        }
    }

    public static String[] resultToArray(String consulta,String tabla) throws SQLException{
        query = conn.createStatement();
        //"SELECT cu_cod FROM Cuadriculas ORDER BY cu_cod;"
        result = query.executeQuery(consulta);

        ArrayList<String> valores = new ArrayList<>();

        while (result.next()) {
            //"cu_cod"
            String valor = result.getString(tabla);
            valores.add(valor);
        }

        String[] arreglo = valores.toArray(new String[0]);

        return arreglo;

    }

    public static ResultSet updateListaResultados(int tabla) throws SQLException {
        // realiza la consulta "SELECT * FROM ejemplo_personas" a la base de datos
        // utilizando la conexión ya establecida (almacenada en la variable conn).
        // Finalmente muestra el resultado de la consulta en la tabla principal
        // del programa (jTablaPersonas).
        query = conn.createStatement();
        switch(tabla){
            case 0:{//Objetos
                result = query.executeQuery("SELECT * FROM objetos;");
                break;
            }
            case 1:{//personas
               result = query.executeQuery("SELECT p_apellido,p_nombre,p_dni,p_email,p_telefono FROM personas  ORDER BY p_apellido; "); 
               break;
            }
            case 2:{//sitios
                result = query.executeQuery("SELECT * FROM sitios ORDER BY s_localidad;");
                break;
            }
            case 3:{//Cuadriculas
                result = query.executeQuery("SELECT cu_cod,s_cod,s_localidad FROM cuadriculas,sitios WHERE s_cod_dividido=s_cod;");
                break;
            }
            case 4:{ //cajas
               result = query.executeQuery("SELECT * FROM cajas ORDER BY ca_fecha; ");
               break;
            }
            
        }
        return result;
    }
   
    /*public static String[] resultToArrayString(ResultSet rs) throws SQLException{
        List<String> lista = new ArrayList<>();
        
        lista.add(rs.g))
    }*/
    
    public static DefaultTableModel resultToTable(ResultSet rs) throws SQLException {
        // Esta es una función auxiliar que les permite convertir los resultados de las
        // consultas (ResultSet) a un modelo interpretable para la tabla mostrada en pantalla
        // (es decir, para un objeto de tipo JTable, ver línea 81)
        ResultSetMetaData metaData = rs.getMetaData();

        // creando las culmnas de la tabla
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // creando las filas de la tabla con los resultados de la consulta
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
    
    // Consulta numero 5: 
    // Mostrar el código y nombre de los objetos que se hallaron entre dos fechas ingresadas por el usuario.

    public static ResultSet codigoYNombreDeObjetoEntreFechas (Date fecha1, Date fecha2) throws SQLException {
        
        String consulta = "SELECT o_cod, o_nombre FROM objetos WHERE o_fecharegistro BETWEEN ? AND ?;";
        
        p_query = conn.prepareStatement(consulta);
        p_query.setDate(1, (java.sql.Date) fecha1);
        p_query.setDate(2, (java.sql.Date) fecha2);
        
        return p_query.executeQuery();
        
    }
    
    public static boolean objetoExiste (String cod) throws SQLException {
        String consulta = "SELECT COUNT(o_cod) FROM objetos WHERE o_cod = ?;";
        
        p_query = conn.prepareStatement(consulta);
        p_query.setString(1, cod);
        ResultSet rs = p_query.executeQuery();
        
        return rs.getInt(1) == 0;
    }
    
    public static void insertarObjeto (String cod, String nombre, String extraccion, float alto, float largo, float espesor, float peso, int cant, Date registro, String desc, String origen, String codAsociado, String codContiene, int dni, String especialidad) throws SQLException {
        
        //String a = ""+especialidad;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(registro);
        
        java.sql.Date fechaSql = new java.sql.Date(registro.getTime());
        
        String consulta = "INSERT INTO Objetos (O_Cod, O_Nombre, O_Tipoextraccion, O_Alto, O_Largo, O_Espesor, O_Peso, O_Cantidad, O_Fecharegistro, O_Descripcion, O_Origen, CU_Cod_Asocia, CA_Cod_Contiene, P_Dni_Ingresa, O_Es) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        p_query = conn.prepareStatement(consulta);
        p_query.setString(1, cod);
        p_query.setString(2, nombre);
        p_query.setString(3, extraccion);
        p_query.setFloat(4, alto);
        p_query.setFloat(5, largo);
        p_query.setFloat(6, espesor);
        p_query.setFloat(7, peso);
        p_query.setInt(8, cant);
        p_query.setDate(9, fechaSql);
        p_query.setString(10, desc);
        p_query.setString(11, origen);
        p_query.setString(12, codAsociado);
        p_query.setString(13, codContiene);
        p_query.setInt(14, dni);
        p_query.setString(15,especialidad);
        
        p_query.executeUpdate();
    }
    
    // Consulta numero 3:
    
    public static void eliminarCaja(String codigo) {
        
        // Como este es un ejemplo, no hemos realizado controles, pero en la versión final
        // sería bueno que el programa controle por la existencia del elemento, antes de ser
        // eliminado, y que le pregunte al usuario y realmente desea eliminarlo o, en el caso de
        // no existir el valor, que le indique que ese valor no existe.
        try {
            
            // creamos una consulta DELETE parametrizada por el valor que identificador del valor queremos eliminar
            // en este caso, el DNI,(indicado nuevamente con ?)
            p_query = conn.prepareStatement("DELETE FROM cajas WHERE dni = ?");
            // luego asignamos el valor dado por el usuario a ese parámetro (?), dando primero su posición y luego su valor
            // como se hizo anteriormente en el INSERT
            p_query.setString(1, codigo);
             // ejecutamos la consulta con el valor asignado
            p_query.executeUpdate();
            
            // finalmente actualizamos nuestra tabla mostrando la lista de personas en el formulario principal

        } catch (SQLException ex) {
            Logger.getLogger(ArqueologiaFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    // Consulta numero 4:
    // Obtener el detalle de los objetos que hay en una caja con código ingresado por el usuario.
    
    public static ResultSet objetosEnUnaCaja(String codigo) throws SQLException{
        
        String consulta = "SELECT o_cod, o_nombre, o_tipoextraccion, o_alto, o_largo, o_espesor, o_peso, o_cantidad, o_fecharegistro, o_descripcion FROM  objetos, cajas WHERE ca_cod = ca_cod_contiene AND ca_cod = ?;";
        
        p_query = conn.prepareStatement(consulta);
        //Fijarse si funciona en el caso de poner parameter index = 1 o 0
        p_query.setString(1, codigo);
        
        return p_query.executeQuery();
    }
    
    //
    
    public static int cantidadDeObjetosLiticos() throws SQLException{
        
        query = conn.createStatement();
        
        result = query.executeQuery("SELECT COUNT(*) FROM objetos WHERE o_es = 'L'");
        
        return result.getInt(0);
        
    }
    
    public static int cantidadDeObjetosCeramicos() throws SQLException{
        
        query = conn.createStatement();
        
        result = query.executeQuery("SELECT COUNT(*) FROM objetos WHERE o_es = 'C'");
        
        return result.getInt(0);

        
    }
    
    // Consulta 7:
    // Mostrar en una pestaña o ventan el resumen de la cantidad de personas, cantidad
    // de cuadriculas, cantidad de objetos y cantidad de cajas, actualmente en el sistema.
    
    public static int[] mostrarResumenDeCantidades() throws SQLException {
        
        int[] resultados = new int[4];
        
        int cantPer = 0 , cantCuad = 0, cantObj = 0, cantCajas = 0;
 
        query = conn.createStatement();
        
        result = query.executeQuery("SELECT COUNT(*) AS cantPersonas FROM personas");
        
        resultados [0] = result.getInt(cantPer);
        
        result = query.executeQuery("SELECT COUNT(*) AS cantCuad FROM cuadriculas");
        
        resultados [1] = result.getInt(cantCuad);
        
        result = query.executeQuery("SELECT COUNT(*) AS cantObj FROM objetos");
        
        resultados [2] = result.getInt(cantObj);
        
        result = query.executeQuery("SELECT COUNT(*) AS cantCajas FROM cajas");
        
        resultados [3] = result.getInt(cantCajas);
        
        return resultados;
    }
    
    
    // Consulta 8:
    // Para cada arqueologo, mostrar su nombre y apellido junto con la cantidad de objetos
    // hallados por el. El listado debe mostrarse ordenado alfabeticamente por apellido de arqueologo.
    
    public static ResultSet objetosEncontradosPorArqueologos() throws SQLException {
        
        String consulta = 
                  "SELECT p_nombre, p_apellido, COUNT(o_cod)"
                + "FROM objetos, personas"
                + "WHERE p_dni = p_dni_ingresa"
                + "GROUP BY p_apellido, p_nombre"
                + "ORDER BY p_apellido";
        
        query = conn.createStatement();
        
        result = query.executeQuery(consulta);
        
        return result;
    }
    
    
    // Consulta 9:
    // Listar código y lugar de las cajas que esté vacías.
    
    public static ResultSet cajasVacias() throws SQLException {
        
        String consulta = 
                  "SELECT ca_cod, ca_lugar "
                + "FROM cajas"
                + "EXCEPT"
                + "SELECT DISTINCT ca_cod, ca_lugar"
                + "FROM objetos, cajas"
                + "WHERE ca_cod = ca_cod_contiene;";
        
        query = conn.createStatement();
        
        result = query.executeQuery(consulta);
        
        return result;
    }
    
}