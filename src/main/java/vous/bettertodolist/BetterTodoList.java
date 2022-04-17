/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package vous.bettertodolist;

import GoogleClassroomList.GoogleClassroomProcess;
import java.io.File;

/**
 *
 * @author Logarta
 */
public class BetterTodoList {

    public static void main(String[] args) {
        firstStart();
    }
    
    public static void firstStart(){
        String path = System.getProperty("user.dir");
        File credentialsPath = new File(path+"\\tokens\\StoredCredential");
        if (credentialsPath.exists()){
            TodoListMainFrame start = new TodoListMainFrame();
            start.setVisible(true);
            start.firstStart();
        } else {
            TodoListMainFrame start = new TodoListMainFrame();
            start.setVisible(true);
        }
    }
}
