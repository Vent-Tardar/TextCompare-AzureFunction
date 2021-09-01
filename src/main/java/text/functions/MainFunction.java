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
        fileTwo = fileTwo.substring(25, size-56);
//        System.out.println("====================================\n" + fileTwo);
        ArrayList<String> originO = new ArrayList<>();
        originO.add(fileOne);
        ArrayList<String> origin = new ArrayList<>();
        for (String s : originO) {
            s = s.replace("\n", ", ");
            origin.add(s);
        }
        ArrayList<String> modifiedO = new ArrayList<>();
        modifiedO.add(fileTwo);
        ArrayList<String> modified = new ArrayList<>();
        for (String s : modifiedO) {
            s = s.replace("\n", ", ");
            modified.add(s);
        }
        CompareController compareController = new CompareController(new FileCompare());
        CompareResults comResults = compareController.compare(origin, modified);
        List<Diff> results = comResults.getDiffList();
        System.out.println(results);
        return request.createResponseBuilder(HttpStatus.OK).body(results).build();
    }
}
