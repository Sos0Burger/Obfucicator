import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    static Random rnd = new Random();
    static String mainClassName;

    static String newClassName = String.valueOf((char)rnd.nextInt(65,91))+String.valueOf((char)rnd.nextInt(65,91)) + String.valueOf((char)rnd.nextInt(97,123));
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

        code = deleteLineComments(code);

        code = renameClass(code, path);

        code = variableCounter(code);

        code = deleteSpaces(code);
        code = deleteMultiLineComments(code);

        try(FileWriter fw = new FileWriter("X:/PRJjava/ProgExample/src/"+newClassName+".java")){
            fw.write(code);
            fw.flush();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static String deleteLineComments(String code){
        code = code.replaceAll("//(.*)\n","");
        return code;
    }
    public static String deleteMultiLineComments(String code){
        code = code.replaceAll("/\\*(.*)\\*/","");
        return code;
    }
    public static String renameClass(String code,String path){
        Pattern p = Pattern.compile("\\\\(\\w*)\\.java");
        Matcher m = p.matcher(path);
        m.find();
        mainClassName = m.group().replaceAll("\\\\|\\.java","");
        code = code.replaceAll(mainClassName, newClassName);
        return code;
    }
    public static String deleteSpaces(String code){

        code = code.replaceAll("\r|\n","");
        code = code.replaceAll(";(\s*)",";");
        code = code.replaceAll("\\{( *)","{");

        return code;
    }
    public static String variableCounter(String code){
        Pattern p = Pattern.compile("(.*)\s[a-z]([A-Za-z\\d]*)(;|((\s=\s)|=|\s=)(.*);)");
        Matcher m = p.matcher(code);
        int counter = 0;

        while(m.find()){
            counter++;
        }

        m = p.matcher(code);
        String randomCharacter;
        String variable;
        ArrayList<String> usedChars = new ArrayList<>();
        usedChars.add(String.valueOf((char) rnd.nextInt(97,122)));
        while (m.find()){
            Pattern pattern = Pattern.compile("\s[a-z]([A-Za-z\\d]*)((\s=)|=|;)");
            Matcher matcher = pattern.matcher(m.group());
            String replace;
            randomCharacter = String.valueOf((char)rnd.nextInt(97,122));

            if(matcher.find()){
                for(int i = 0; i<usedChars.size();i++){
                    if(!usedChars.get(i).equals(randomCharacter)){
                        continue;
                    }
                    if(counter >26 ){
                        randomCharacter = String.valueOf((char)rnd.nextInt(97,123) + rnd.nextInt(0,2)==0?(char)rnd.nextInt(97,123):(char)rnd.nextInt(65,91));

                    }
                    else {
                        randomCharacter = String.valueOf((char) rnd.nextInt(97, 123));
                    }
                    i = 0;
                }
                variable = matcher.group().replaceAll("=|\s|;","");

                Pattern pat = Pattern.compile("\\W"+variable+"\\W");
                Matcher mat = pat.matcher(code);

                while(mat.find()){
                    replace = mat.group().replace(variable,randomCharacter);
                    code = code.replace(mat.group(),replace);
                }
            }
        }
        return code;
    }
}