package rikkei.academy.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config <T>{
    public static String PATH_USER_PRINCIPAL = "C:\\Users\\Admin\\IdeaProjects\\TinhKetQuaFizzBuzz\\untitled2\\untitled18\\src\\rikkei\\academy\\datebase\\user_principal.txt";
    public static Scanner scanner(){
        Scanner scanner = new Scanner(System.in);
        return scanner;
    }
   public List<T> readFile(String pathFile){
        List<T> tList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            tList = (List<T>) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }catch (Exception e){
            e.getMessage();
        }
        return tList;
   }
   public void writeFile(String pathFile, List<T> tList){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pathFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tList);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
   }
}
