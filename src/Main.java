import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    static Random rnd = new Random();
    public static void main(String[] args) {
        Random rnd = new Random();
        System.out.println("Укажите абсолютный путь до файла");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        String code;
        int i;
        StringBuilder cd = new StringBuilder();

        try(FileReader fr = new FileReader(path)){
            while((i = fr.read())!=-1){
                cd.append((char) i);
            }
        }
        catch (IOException ex){
            ex.getMessage();
        }
        code = cd.toString();

        //code = deleteComments(code);
        //code = renameClass(code, path, nameclass);

       code = variableCounter(code);

        //code = deleteSpaces(code);


        try(FileWriter fw = new FileWriter("output.java")){
            fw.write(code);
            fw.flush();
        }
        catch (IOException ex){
            ex.getMessage();
        }
    }

    public static String deleteComments(String code){
        code = code.replaceAll("//(.*)\r","");
        code = code.replaceAll("/\\*((.*)\r\n)*(\\*)/","");
        return code;
    }
    public static String renameClass(String code,String path, String nameclass){
        code = code.replaceAll(path.replaceAll("\\.java",""),nameclass);
        return code;
    }
    public static String deleteSpaces(String code){
        code = code.replaceAll("//(.*)\r","");
        code = code.replaceAll("/\\*((.*)\r\n)*(\\*)/","");
        return code;
    }
    public static String variableCounter(String code){
        Pattern p = Pattern.compile("(([A-z]|\s)*)((= ((.*)&[^;]))|);");
        Matcher m = p.matcher(code);
        int counter = 0;
        StringBuilder sb = new StringBuilder(code);
        while (m.find()){
            System.out.println(m.group());
        }
        return code;
    }
}