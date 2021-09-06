package text.controller;

import java.util.ArrayList;

public class MainController {
    private String s;

    public ArrayList<String> cleanedOriginal(String body){
        String fileOne = body.substring(body.indexOf("Content-Type: text/plain"));
        String fileTwo = fileOne.substring(fileOne.lastIndexOf("Content-Type: text/plain"));
        int size = fileTwo.indexOf("Content-Type: text/plain");
        int sizeTwo = fileTwo.length();
        fileOne = fileOne.substring(size, sizeTwo);
        size = fileOne.length();
        fileOne = fileOne.substring(25, size-1);
        size = fileOne.lastIndexOf("-");
        fileOne = fileOne.substring(0, size-27);
        if (fileOne.contains("\n\r\n")){
            fileOne = fileOne.replace("\n\r\n", "");
        }
        fileOne = fileOne.replaceAll("\r", "");

        ArrayList<String> origin = new ArrayList<>();
        for(int i = 0; i < fileOne.length(); i++){
            s = fileOne.substring(0, fileOne.indexOf("\n")+1);
            fileOne = fileOne.substring(s.length());
            origin.add(s);
        }
        origin.add(fileOne);
        return origin;
    }

    public ArrayList<String> cleanedModified(String body){
        String fileOne = body.substring(body.indexOf("Content-Type: text/plain"));
        String fileTwo = fileOne.substring(fileOne.lastIndexOf("Content-Type: text/plain"));
        fileTwo = fileTwo.substring(25);

        if (fileTwo.contains("\n\r\n")){
            fileTwo = fileTwo.replace("\n\r\n", "");
        }
        ArrayList<String> modified = new ArrayList<>();
        fileTwo = fileTwo.replaceAll("\r", "");
        for(int i = 0; i < fileTwo.length(); i++){
            s = fileTwo.substring(0, fileTwo.indexOf("\n")+1);
            fileTwo = fileTwo.substring(s.length());
            modified.add(s);
        }
        modified.remove(modified.get(modified.size()-1));
        return modified;
    }
}
