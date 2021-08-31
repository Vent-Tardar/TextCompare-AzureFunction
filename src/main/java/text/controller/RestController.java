package text.controller;

import text.model.CompareResults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import static text.extractors.TextExtractor.extractText;

@Controller
public class RestController {

    final CompareController compareController;

    public RestController(CompareController compareController) {
        this.compareController = compareController;
    }

    @GetMapping("/")
    public String index() {
        return "main.html";
    }

    /**
     *
     * @param file1 First text file
     * @param file2 Second text file
     * @param response Extending HTTP functionality
     * @return compare results in JSON format
     * @throws Exception If an exception occurs when creating a file
     */
    @ResponseBody
    @PostMapping(value = "api/v1/compare")
    public CompareResults uploadFile(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2,
                                     HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        return compareController.compare(extractText(file1.getInputStream()), extractText(file2.getInputStream()));
    }
}