package text.function;

import java.io.IOException;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import text.controller.CompareController;
import text.controller.MainController;
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
        MainController mainController = new MainController();
        ArrayList<String> origin;
        ArrayList<String> modified;
        origin = mainController.cleanedOriginal(body);
        modified = mainController.cleanedModified(body);

        CompareController compareController = new CompareController(new FileCompare());
        CompareResults comResults = compareController.compare(origin, modified);
        List<Diff> results = comResults.getDiffList();
        return request.createResponseBuilder(HttpStatus.OK).body(results).build();
    }
}
