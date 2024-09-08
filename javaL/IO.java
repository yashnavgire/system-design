package javaL;

import java.util.*;

public class IO {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        //read word
        String word = sc.next();
        
        //read full line
        String line = sc.nextLine();
        
        //tokenize the line
        StringTokenizer st = new StringTokenizer(line, " ");
        while(st.hasMoreTokens()) st.nextToken();

        //read and parse an Integer, double, long
        int intVal = sc.nextInt();
        long longVal = sc.nextLong();
    }
}
