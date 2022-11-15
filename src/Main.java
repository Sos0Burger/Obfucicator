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
    static int counter = 0;

    static ArrayList<String> usedChars = new ArrayList<>();

    static String newClassName = String.valueOf((char)rnd.nextInt(65,91)+(char)rnd.nextInt(65,91) + (char)rnd.nextInt(97,123));
    public static void main(String[] args) {
        System.out.println("Укажите абсолютный путь до файла");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        String code;
        int i;
        StringBuilder cd = new StringBuilder();

        try(FileReader fr = new FileReader(path)){
            while((i = fr.read())!=-1){
                cd.append((char) i);
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        code = cd.toString();

        code = deleteLineComments(code);

        code = renameClass(code, path);
        code = variableSwap(code);
        code = methodSwap(code);
        //code = variableCounter(code);

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
        code = code.replaceAll("\s//(.+)","");
        return code;
    }
    public static String deleteMultiLineComments(String code){
        code = code.replaceAll("/\\*(.*)\\*/","");
        return code;
    }
    public static String renameClass(String code,String path){
        Pattern p = Pattern.compile("\\\\(\\w*)\\.java");
        Matcher m = p.matcher(path);
        if(m.find()) {
            mainClassName = m.group().replaceAll("\\\\|\\.java", "");
            code = code.replaceAll(mainClassName, newClassName);
        }
        return code;
    }
    public static String deleteSpaces(String code){

        code = code.replaceAll("[\r\n]","");
        code = code.replaceAll(";(\s*)",";");
        code = code.replaceAll("(\\{)(\s+)","{");
        code = code.replaceAll("(})(\s+)","}");
        code = code.replaceAll("\s+=|\s+=\s+|=\s+", "=");
        code = code.replaceAll(", | ,",",");
        code = code.replaceAll("=\s", "=");
        return code;
    }
    public static String variableSwap(String code){
        Pattern variableInitializers = Pattern.compile("(\\w|>)\s\\b[a-z]([A-z\\d]*)(;| =|=)");
        Matcher matcherp = variableInitializers.matcher(code);
        Pattern variableFind = Pattern.compile("\s[a-z]([A-z\\d]*)\\b");
        Matcher variableName;
        boolean isDuplicate;
        ArrayList<String> initializers = new ArrayList<>();
        while(matcherp.find()){
            isDuplicate =false;

            counter++;
            variableName = variableFind.matcher(matcherp.group());
            if (variableName.find()) {
                for(int i = 0; i<initializers.size();i++){
                    if(variableName.group().replaceAll("\s", "").equals(initializers.get(i))){
                        isDuplicate = true;
                        break;
                    }
                }
                if(!isDuplicate) {
                    initializers.add(variableName.group().replaceAll("\s", ""));
                }
            }
        }

        String randomCharacter = String.valueOf((char) rnd.nextInt(97,122));
        usedChars.add(randomCharacter);
        for (String item:initializers) {
            for(int i = 0;i<usedChars.size();i++){
                if(randomCharacter.equals(usedChars.get(i))){
                    if(counter>26){
                        randomCharacter = String.valueOf((char) rnd.nextInt(97,122)+(char)rnd.nextInt(97,123));
                    }
                    else{
                        randomCharacter = String.valueOf((char) rnd.nextInt(97,122));
                    }
                    i = 0;
                }
            }
            usedChars.add(randomCharacter);
            code = code.replaceAll("\\b"+item+"\\b",randomCharacter);
        }

        return code;
    }
    public static String methodSwap(String code){
        Pattern methodFind = Pattern.compile("\\w\s[a-z][A-z\\d]+\\((.+)\\)");
        Pattern methodNameFind = Pattern.compile("[a-z][A-z\\d]+");

        Matcher methodMatcher = methodFind.matcher(code);
        Matcher methodNameMatcher;
        ArrayList<String> methodNames = new ArrayList<>();
        while (methodMatcher.find()){
            methodNameMatcher = methodNameFind.matcher(methodMatcher.group());
            if(methodNameMatcher.find()&&!methodNameMatcher.group().equals("main")){
                methodNames.add(methodNameMatcher.group());
            }
        }
        String randomCharacter = String.valueOf((char) rnd.nextInt(97,122));
        usedChars.add(randomCharacter);
        for (String item:methodNames) {
            for(int i = 0;i<usedChars.size();i++){
                if(randomCharacter.equals(usedChars.get(i))){
                    if(counter>26){
                        randomCharacter = String.valueOf((char) rnd.nextInt(97,122)+(char)rnd.nextInt(97,123));
                    }
                    else{
                        randomCharacter = String.valueOf((char) rnd.nextInt(97,122));
                    }
                    i = 0;
                }
            }
            usedChars.add(randomCharacter);
            code = code.replaceAll(item,randomCharacter);
        }

        return code;
    }
}