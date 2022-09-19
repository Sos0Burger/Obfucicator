import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        String nameclass = "rwfs";
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

        Pattern p = Pattern.compile("([A-z]*) = ([A-z]*);");
        Matcher m = p.matcher(code);


        code = code.replaceAll("\n"," ");
        code = code.replaceAll("\r", "");
        code = code.replaceAll(" ", "");


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
}