package text.functions;

import java.io.File;
import java.net.http.HttpRequest;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            HttpRequest req, @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String queryOne = request.getQueryParameters().get("First file");
        File fileOne = new File(request.getBody().orElse(queryOne));
        String queryTwo = request.getQueryParameters().get("Second file");
        File fileTwo = new File(request.getBody().orElse(queryTwo));



        if (!(fileOne.exists() && fileTwo.exists())) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + fileOne.getName()).build();
        }
    }
}
