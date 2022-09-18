import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Random rnd = new Random();
        String nameclass = "rwfs";
        System.out.println("Укажите абсолютный путь до файла");
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        String code = new String();
        int i;
        StringBuffer cd = new StringBuffer();

        try(FileReader fr = new FileReader(path)){
            while((i = fr.read())!=-1){
                cd.append((char) i);
            }
        }
        catch (IOException ex){
            ex.getMessage();
        }
        code = cd.toString();
        code = code.replaceAll("//(.*)\r","");
        code = code.replaceAll("/\\*(((.*))\r\n)*(\\*)/","");
        Pattern p = Pattern.compile("\\s(.*)class(.*)\\{");

        code = code.replaceAll("\\s(.*)class(.*)\\{"," class "+ nameclass + "{");

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
}