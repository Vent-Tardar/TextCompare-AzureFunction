package text.controller;

import text.diffs.compare.FileCompare;
import text.model.CompareResults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class CompareController {

    final FileCompare compareTexts;

    public CompareController(FileCompare compareTexts) {
        this.compareTexts = compareTexts;
    }

    @ResponseBody
    public CompareResults compare(ArrayList<String> origin, ArrayList<String> modified) {
        System.out.println("Origin => "+origin);
        System.out.println("Modified => "+modified);
        return compareTexts.compare(origin, modified);
    }
}