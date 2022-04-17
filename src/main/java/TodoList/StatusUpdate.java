/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class StatusUpdate {
    private String taskName;
    private int compareDate;
    private DateTimeFormatter formatter;
    
    public void checkStatus(){
        checkClassroomStatus();
        checkLocalStatus();
    }
    
    private void checkClassroomStatus(){
        try {
            formatter = DateTimeFormatter.ofPattern("MMM d, y");
            conn con = new conn();
            String sql = "SELECT todo_message, todo_deadline FROM classroom_todolist";
            PreparedStatement st = con.c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()){
                taskName = rs.getString("todo_message");
                String taskDuedate = rs.getString("todo_deadline");

                LocalDate getDate = LocalDate.now();
                String dateNow = getDate.format(formatter);
                if(!taskDuedate.equals("No duedate")){
                    LocalDate now = LocalDate.parse(dateNow, formatter);
                    LocalDate due = LocalDate.parse(taskDuedate, formatter);
                    compareDate = now.compareTo(due);
                }
                    if(compareDate>0 && !taskDuedate.equals("No duedate")){
                        try {
                            String sql2 = "UPDATE classroom_todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "Overdue");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    } else if (compareDate==0 && !taskDuedate.equals("No duedate")){
                        try {
                            String sql2 = "UPDATE classroom_todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "Due today");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    } else if (compareDate<0 && !taskDuedate.equals("No duedate")){
                        try {
                            String sql2 = "UPDATE classroom_todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "Waiting");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    } else {
                        try {
                            String sql2 = "UPDATE classroom_todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "No deadline");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
            }
            rs.close();
            st.close();
            con.closeConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured while selecting a task from todo list! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private void checkLocalStatus(){
        try {
            formatter = DateTimeFormatter.ofPattern("MMM d, y");
            conn con = new conn();
            String sql = "SELECT todo_message, todo_deadline FROM todolist";
            PreparedStatement st = con.c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()){
                taskName = rs.getString("todo_message");
                String taskDuedate = rs.getString("todo_deadline");

                LocalDate getDate = LocalDate.now();
                String dateNow = getDate.format(formatter);
                if(!taskDuedate.equals("No duedate")){
                    LocalDate now = LocalDate.parse(dateNow, formatter);
                    LocalDate due = LocalDate.parse(taskDuedate, formatter);
                    compareDate = now.compareTo(due);
                }
                    if(compareDate>0 && !taskDuedate.equals("No duedate")){
                        try {
                            String sql2 = "UPDATE todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "Overdue");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    } else if (compareDate==0 && !taskDuedate.equals("No duedate")){
                        try {
                            String sql2 = "UPDATE todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "Due today");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    } else if (compareDate<0 && !taskDuedate.equals("No duedate")){
                        try {
                            String sql2 = "UPDATE todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "Waiting");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    } else {
                        try {
                            String sql2 = "UPDATE todolist SET status=? WHERE todo_message='"+taskName+"'";
                            PreparedStatement st2 = con.c.prepareStatement(sql2);
                            st2.setString(1, "No deadline");
                            st2.executeUpdate();
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
            }
            rs.close();
            st.close();
            con.closeConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occured while selecting a task from todo list! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
