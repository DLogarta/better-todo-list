/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GoogleClassroomList;

import java.awt.Desktop;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import vous.bettertodolist.TodoListMainFrame;

/**
 *
 * @author Logarta
 */
public class GoogleClassroomTodoListInfo extends javax.swing.JPanel {
    private String receivedMessage;
    private String receivedDate;
    private String receivedStatus;
    private String link;
    public GoogleClassroomTodoListInfo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        message = new javax.swing.JLabel();
        due = new javax.swing.JLabel();
        VisitButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 252, 217));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        setMaximumSize(new java.awt.Dimension(460, 50));
        setMinimumSize(new java.awt.Dimension(460, 50));
        setPreferredSize(new java.awt.Dimension(460, 50));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        message.setText("Task Here");
        add(message, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 360, -1));

        due.setText("Duedate: ");
        add(due, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 27, 360, -1));

        VisitButton.setBackground(new java.awt.Color(229, 239, 193));
        VisitButton.setText("Visit");
        VisitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisitButtonActionPerformed(evt);
            }
        });
        add(VisitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 10, 70, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void VisitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VisitButtonActionPerformed
        
    }//GEN-LAST:event_VisitButtonActionPerformed

    public void getInfo(String sentMessage, String sentDate, String sentStatus){
        receivedMessage = sentMessage;
        receivedDate = sentDate;
        receivedStatus = sentStatus;
        
        setInfo();
    }
    
    public void setInfo(){
        message.setText(receivedMessage);
        due.setText("Due: "+receivedDate);
        if (receivedStatus.equals("Overdue")){
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255,51,51), 2, true));
        } else if (receivedStatus.equals("Due today")){
            setBorder(new javax.swing.border.LineBorder(new java.awt.Color(50,205,50), 2, true));
        }
    }
    
    public JButton getDone(){
        return VisitButton;
    }
    
    public void changeState(){
        String message = this.message.getText();
        System.out.println(message);
        try {
            conn con = new conn();
            String sql = "SELECT * from classroom_todolist where todo_message = ?";
            PreparedStatement st = con.c.prepareStatement(sql);
            st.setString(1, message);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                link = rs.getString("todo_link");
                openToBrowser(link);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occured while trying to visit the site! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);        
        }
    }
    
    private void openToBrowser(String url){
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured while trying to open website! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton VisitButton;
    private javax.swing.JLabel due;
    private javax.swing.JLabel message;
    // End of variables declaration//GEN-END:variables
}