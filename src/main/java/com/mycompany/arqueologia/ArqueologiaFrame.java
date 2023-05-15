
package com.mycompany.arqueologia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ArqueologiaFrame extends javax.swing.JFrame {

    // Valores para la conexión a la base de datos (su nombre, URL, Usuario y Contraseña)
    private static final String DB_NAME = "arqueologiaDB";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    private static final String DB_USER = "postgres";
    private static final String DB_PWD = "123456";
    
    // Mensajes de error
    private static final String ERROR_MSG_INSERT = "Error al intentar dar de alta a esta persona.";
    private static final String ERROR_MSG_INSERT_INPUT = "No se admiten campos vacíos.";
    
    // Objetos utilizados para interactuar con la base de datos
    // (conexión, realizar consultas con y sin parámetros, y recibir los resultados)
    private static Connection conn = null;
    private static Statement query = null;
    private static PreparedStatement p_query = null;
    private static ResultSet result = null;
    
    public ArqueologiaFrame() throws SQLException {
        initComponents();
        //label_error.setVisible(false);

        // Una vez creado el formulario e inicializado sus componentes ↑↑↑
        // nos enlazamos con el DBMS para conectarnos a la base de datos solicitada
        // utilizando las credenciales correspondientes
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);

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
        
        // Inicializamos/Actualizamos la lista de personas del formulario
        // para que muestre las personas que ya están cargadas en el sistema
        //updateListaResultados();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelHeader = new javax.swing.JPanel();
        jPanelGeneral = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(635, 355));
        setResizable(false);
        setSize(new java.awt.Dimension(635, 355));

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 76, Short.MAX_VALUE)
        );

        jPanelGeneral.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanelGeneralLayout = new javax.swing.GroupLayout(jPanelGeneral);
        jPanelGeneral.setLayout(jPanelGeneralLayout);
        jPanelGeneralLayout.setHorizontalGroup(
            jPanelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeneralLayout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(503, Short.MAX_VALUE))
        );
        jPanelGeneralLayout.setVerticalGroup(
            jPanelGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeneralLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jButton1)
                .addContainerGap(271, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ArqueologiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArqueologiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArqueologiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArqueologiaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ArqueologiaFrame().setVisible(true);
                } catch (SQLException ex){
                    Logger.getLogger(ArqueologiaFrame.class.getName()).log(Level.SEVERE, null, ex);
               
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanelGeneral;
    private javax.swing.JPanel jPanelHeader;
    // End of variables declaration//GEN-END:variables
}
