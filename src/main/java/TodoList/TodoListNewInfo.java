/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package TodoList;

import com.toedter.calendar.JTextFieldDateEditor;
import vous.bettertodolist.TodoListMainFrame;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Logarta
 */
public class TodoListNewInfo extends javax.swing.JPanel {

    /**
     * Creates new form TodoListNewInfo
     */
    public TodoListNewInfo() {
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

        createButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        TaskMessage = new javax.swing.JTextField();
        Task = new javax.swing.JLabel();
        Due = new javax.swing.JLabel();
        dueDate = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(244, 252, 217));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        setMaximumSize(new java.awt.Dimension(460, 50));
        setMinimumSize(new java.awt.Dimension(460, 50));
        setPreferredSize(new java.awt.Dimension(460, 50));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        createButton.setBackground(new java.awt.Color(229, 239, 193));
        createButton.setText("Add");
        add(createButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 8, 70, 16));

        cancelButton.setBackground(new java.awt.Color(229, 239, 193));
        cancelButton.setText("Cancel");
        add(cancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 27, 80, 16));

        TaskMessage.setPreferredSize(new java.awt.Dimension(70, 16));
        add(TaskMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 8, 330, -1));

        Task.setText("Task:");
        add(Task, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, -1, -1));

        Due.setText("Duedate:");
        add(Due, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 27, -1, -1));

        dueDate.setDateFormatString("MMM d, yyyy");
        dueDate.setPreferredSize(new java.awt.Dimension(105, 16));
        add(dueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 27, 300, -1));
        JTextFieldDateEditor editor = (JTextFieldDateEditor)dueDate.getDateEditor();
        editor.setEditable(false);
    }// </editor-fold>//GEN-END:initComponents

    public JButton getCancel(){
        return cancelButton;
    }
    
    public void removePanel(){
        TodoListMainFrame refresh = new TodoListMainFrame();
        refresh.setVisible(true);
    }
    
    public JButton getDone(){
        return createButton;
    }
    public void changeState(){
        String message = this.TaskMessage.getText();
        String duedate  = ((JTextField)dueDate.getDateEditor().getUiComponent()).getText();
        if(duedate.trim().isEmpty()){
            duedate = "No duedate";
        }
        try {
            conn con = new conn();
            String sql = "insert into todolist(todo_message, todo_deadline) values(?, ?)";
            PreparedStatement st = con.c.prepareStatement(sql);

            st.setString(1, message);
            st.setString(2, duedate);
                
            st.executeUpdate();
            
            con.closeConnection();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occured while adding a new task! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);        
        }
        TodoListMainFrame refresh = new TodoListMainFrame();
        refresh.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Due;
    private javax.swing.JLabel Task;
    private javax.swing.JTextField TaskMessage;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createButton;
    private com.toedter.calendar.JDateChooser dueDate;
    // End of variables declaration//GEN-END:variables
}
