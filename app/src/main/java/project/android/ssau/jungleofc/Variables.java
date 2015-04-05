package project.android.ssau.jungleofc;

import java.util.ArrayList;

/**
 * Created by 123 on 05.04.2015.
 */
public class Variables {
    private Variables(){}

    public static String id;
    public static String task;
    public static ArrayList<String> programm;
    public static boolean addData=true;

    public static void addProgramm(String s){
        programm= new ArrayList<>();
        String[] ss=s.split("\\n");
        for(String s1:ss){
            programm.add(s+"\n");
        }
    }
}
