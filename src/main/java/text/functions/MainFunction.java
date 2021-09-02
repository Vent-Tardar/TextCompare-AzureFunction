package text.functions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import text.controller.CompareController;
import text.diffs.compare.FileCompare;
import text.model.CompareResults;
import text.model.Diff;

/**
 * Azure Functions with HTTP Trigger.
 */
public class MainFunction {
    /**
     * This function listens at endpoint "/api/MainFunction". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/MainFunction
     * 2. curl {your host}/api/MainFunction?name=HTTP%20Query
     */
    @FunctionName("MainFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws IOException {
        String body = request.getBody().get();
        String fileOne = body.substring(body.indexOf("Content-Type: text/plain"));
        String fileTwo = fileOne.substring(fileOne.lastIndexOf("Content-Type: text/plain"));
        int size = fileTwo.indexOf("Content-Type: text/plain");
        int sizeTwo = fileTwo.length();
        fileOne = fileOne.substring(size, sizeTwo);
        size = fileOne.length();
        fileOne = fileOne.substring(25, size-1);
        size = fileOne.lastIndexOf("-");
        fileOne = fileOne.substring(0, size-27);
        size = fileTwo.length();
        fileTwo = fileTwo.substring(25);
        ArrayList<String> originO = new ArrayList<>();
        if (fileOne.contains("\n\r\n")){
            fileOne = fileOne.replace("\n\r\n", "");
        } else if (fileOne.contains("\r\n")){
            fileOne = fileOne.replace("\r\n", "");
        }
        originO.add(fileOne);
        ArrayList<String> origin = new ArrayList<>();
//        for (String s : originO) {
//            if(s.contains("\r\n")){
//                origin.add(s);
//                System.out.println(origin);
//            }
//        }
        ArrayList<String> modifiedO = new ArrayList<>();
        if (fileTwo.contains("\n\r\n")){
            fileTwo = fileTwo.replace("\n\r\n", "");
        }  else if (fileTwo.contains("\r")){
            fileTwo = fileTwo.replace("\r", "");
        }
        modifiedO.add(fileTwo);
        ArrayList<String> modified = new ArrayList<>();
//        for (String s : modifiedO) {
//            if(s.contains("\r\n")){
//                System.out.println("Find: "+s);
//                modified.add(s);
//                System.out.println("Add: "+modified);
//            }
//        }
        String s = fileTwo.substring(0, fileTwo.indexOf("\r\n"));
//        System.out.println(s);
        modified.add(s);
        s = fileTwo.substring(s.length());
        fileTwo = fileTwo.substring(s.length());
//        System.out.println(s);
        while (s.length() != fileTwo.length()){
            if(!s.contains("---------")){
                modified.add(s);
                s = fileTwo.substring(s.length());
                fileTwo = fileTwo.substring(s.length());
            }
        }
//        for (int i = 0; i < modified.size(); i++){
//            System.out.println(modified);
//        }
        CompareController compareController = new CompareController(new FileCompare());
        CompareResults comResults = compareController.compare(originO, modifiedO);
        List<Diff> results = comResults.getDiffList();
        return request.createResponseBuilder(HttpStatus.OK).body(results).build();
    }
}
