
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
            
            
            JOptionPane.showMessageDialog(null,"Se han realizado las operaciones correctamente", "Primera Inicializacion", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){}
        
        // Inicializamos/Actualizamos la lista de personas del formulario
        // para que muestre las personas que ya están cargadas en el sistema
        //updateListaResultados();
 }
}

