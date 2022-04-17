package GoogleClassroomList;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.*;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.model.Date;
import com.google.api.client.util.DateTime;

import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import vous.bettertodolist.TodoListMainFrame;

public class GoogleClassroomProcess {
    private final String APPLICATION_NAME = "Google Classroom API Java Quickstart";
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final String TOKENS_DIRECTORY_PATH = "tokens";
    
    private final List<String> CourseState = Collections.singletonList("ACTIVE");
    private final List<String> SCOPES = new ArrayList<String>();
    private final String CREDENTIALS_FILE_PATH = "/clientSecrets/credentials.json";
    
    private List<String>CourseID = new ArrayList<String>();
    private List<String>CourseName = new ArrayList<String>();
    private ArrayList<ArrayList<String>>AllAssignments = new ArrayList();
    private ArrayList<ArrayList<String>>FinalAssignments = new ArrayList();
    
    public void googleLogin(){
        try {
            getCourses();
        } catch (Exception e) {
            TodoListMainFrame refresh = new TodoListMainFrame();
            refresh.setVisible(true);
        }
    }
    
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        SCOPES.add(ClassroomScopes.CLASSROOM_COURSES_READONLY);
        SCOPES.add(ClassroomScopes.CLASSROOM_COURSEWORK_ME_READONLY);
        // Load client secrets.
        InputStream in = GoogleClassroomProcess.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    private void getCourses() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Classroom service = new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // List the first 10 courses that the user has access to.
        ListCoursesResponse response = service.courses().list().setCourseStates(CourseState)
                .setPageSize(100)
                .execute();
        List<Course> courses = response.getCourses();
        if (courses == null || courses.size() == 0) {
            TodoListMainFrame refresh = new TodoListMainFrame();
            refresh.setVisible(true);
        } else {
            for (Course course : courses) {
                CourseID.add(course.getId());
                CourseName.add(course.getName());
            }
        }
        getAssignments();
    }
    
    private void getAssignments()throws IOException, GeneralSecurityException{
        String[] arrayInfo = null;
        
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Classroom service = new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        for (String courseID : CourseID){
            ListStudentSubmissionsResponse response = service.courses().courseWork().studentSubmissions().list(courseID, "-").execute();
            List<StudentSubmission> studentSubmissions = response.getStudentSubmissions();
            if (studentSubmissions == null || studentSubmissions.size() == 0) {
                
            } else {
                for (StudentSubmission studentSubmission : studentSubmissions) {
                    String isDone = studentSubmission.getState();
                    if (isDone.equals("NEW") || isDone.equals("CREATED") || isDone.equals("RECLAIMED_BY_STUDENT")){
                        int courseMain = CourseID.indexOf(courseID);
                        ArrayList<String> Tasks = new ArrayList();
                        
                        Tasks.add(studentSubmission.getCourseId());
                        Tasks.add(CourseName.get(courseMain));
                        Tasks.add(studentSubmission.getCourseWorkId());
                        AllAssignments.add(Tasks);
                    }
                }
            }
        }
        getInfo();
    }
    
    private void getInfo()throws IOException, GeneralSecurityException{
        int taskDueMonth = 0;
        int taskDueDay = 0;
        int taskDueYear = 0;
        String taskDueDate = "";
        
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Classroom service = new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        for (ArrayList<String> list : AllAssignments){
            String content = String.join("|", list);
            String[] arrayInfo = content.split("\\|", 0);
            String courseId = arrayInfo[0];
            String courseName = arrayInfo[1];
            String id = arrayInfo[2];
            
            CourseWork response = service.courses().courseWork().get(courseId, id).execute();
            String taskTitle = response.getTitle();
            String taskLink = response.getAlternateLink();
            
            Date DueDate = response.getDueDate();
            String originalDueDate = String.valueOf(DueDate);
            if (originalDueDate != "null"){
                taskDueMonth = DueDate.getMonth();
                taskDueDay = DueDate.getDay();
                taskDueYear = DueDate.getYear();
                String dueMonth = String.valueOf(taskDueMonth);
                String dueDay = String.valueOf(taskDueDay);
                String dueYear = String.valueOf(taskDueYear);
                taskDueDate = dueMonth+"-"+dueDay+"-"+dueYear;
            } else {
                taskDueDate = "No date";
            }
            
            ArrayList<String> Tasks = new ArrayList();
            Tasks.add(taskTitle);
            Tasks.add(courseName);
            Tasks.add(taskDueDate);
            Tasks.add(taskLink);
            FinalAssignments.add(Tasks);
        }
        refreshDatabase();
    }
    
    private void refreshDatabase(){
        try {
            conn con = new conn();
            String sql = "DELETE FROM classroom_todolist";
            PreparedStatement st = con.c.prepareStatement(sql);
            st.executeUpdate();
            
            con.closeConnection();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occured while trying to refresh google classroom tasks! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);        
        }
        addToDatabase();
    }
    
    private void addToDatabase(){
        String date="";
        SimpleDateFormat fromUser = new SimpleDateFormat("M-d-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMM d, y");
        
        for (ArrayList<String> list : FinalAssignments){
            String content = String.join("|", list);
            if (content.length() != 0){
                String[] arrayInfo = content.split("\\|", 0);
                String message = arrayInfo[0]+" - "+arrayInfo[1];
                String duedate = arrayInfo[2];
                String link = arrayInfo[3];
                
                if(!duedate.equals("No date")){
                    try {
                        date = myFormat.format(fromUser.parse(duedate));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    date = "No duedate";
                }
                try {
                    conn con = new conn();
                    String sql = "insert into classroom_todolist(todo_message, todo_deadline, todo_link) values(?, ?, ?)";
                    PreparedStatement st = con.c.prepareStatement(sql);

                    st.setString(1, message);
                    st.setString(2, date);
                    st.setString(3, link);

                    st.executeUpdate();

                    con.closeConnection();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "An error occured while adding a new task from google classroom! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);        
                }
            }
        }
        TodoListMainFrame refresh = new TodoListMainFrame();
        refresh.setVisible(true);
    }
    
    public void logoutGoogle(){
        try {
            conn con = new conn();
            String sql = "DELETE FROM classroom_todolist";
            PreparedStatement st = con.c.prepareStatement(sql);
            st.executeUpdate();
            
            con.closeConnection();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occured while removing traces of logged out google account! \n "+e, "Error!", JOptionPane.PLAIN_MESSAGE);        
        }
        TodoListMainFrame refresh = new TodoListMainFrame();
        refresh.setVisible(true);
    }
}
