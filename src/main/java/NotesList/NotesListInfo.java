/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package NotesList;

import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import vous.bettertodolist.TodoListMainFrame;

/**
 *
 * @author Logarta
 */
public class NotesListInfo extends javax.swing.JPanel {
    private String receivedTitle;
    public NotesListInfo() {
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

        title = new javax.swing.JLabel();
        view = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 252, 217));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        setMaximumSize(new java.awt.Dimension(160, 50));
        setMinimumSize(new java.awt.Dimension(160, 50));
        setPreferredSize(new java.awt.Dimension(160, 50));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setText("Title Here");
        add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 140, -1));

        view.setBackground(new java.awt.Color(229, 239, 193));
        view.setText("View");
        view.setPreferredSize(new java.awt.Dimension(75, 16));
        add(view, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 27, 80, -1));
    }// </editor-fold>//GEN-END:initComponents

    public void getInfo(String sentTitle){
        receivedTitle = sentTitle;
        setInfo();
    }
    
    public void setInfo(){
        title.setText(receivedTitle);
    }
    
    public JButton getView(){
        return view;
    }
    
    public void viewNote(){
        String title = this.title.getText();
        TodoListMainFrame refresh = new TodoListMainFrame();
        refresh.displayNotes(title);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel title;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables
}