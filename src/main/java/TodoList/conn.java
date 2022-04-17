/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class conn {
    Connection c;
    Statement s;
    public conn(){
        try{
            Class.forName("org.sqlite.JDBC");
            String path = System.getProperty("user.dir");
            String url = "jdbc:sqlite:"+path+"\\bettertodolist.db";
            c=DriverManager.getConnection(url);
            s=c.createStatement();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error trying to connect to todo list database! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);
        } 
    }
    public void closeConnection(){
        try {
            c.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error trying to disconnect to todo list database! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);
        }
        
    }
}
