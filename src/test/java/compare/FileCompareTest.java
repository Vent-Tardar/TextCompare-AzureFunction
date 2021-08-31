package compare;

import text.model.Diff;
import text.model.DiffType;
import org.junit.jupiter.api.Test;
import text.diffs.compare.FileCompare;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileCompareTest {
    private final FileCompare fc = new FileCompare();

    /**
     * This test checks base capabilities of DMP comparing
     */
    @Test
    public void compareMain() {
        List<String> original = Arrays.asList(
                "There is an example",
                "of ordinary text",
                "file. Py code down",
                "below:",
                "#Simple Python",
                "print(\"Hello, world!\")"
        );
        List<String> modified = Arrays.asList(
                "There is an example",
                "of ordinary text",
                "file. Java code down",
                "below:",
                "//Awesome Java",
                "public class Main {",
                "public static void main(String[] args) {",
                "System.out.println(\"Hello, world!\")",
                "}",
                "}"
        );
        List<Diff> source = Arrays.asList(
                new Diff(DiffType.MODIFIED, "file. Py code down","file. Java code down", 3, 3),
                new Diff(DiffType.MODIFIED, "#Simple Python","//Awesome Java", 5, 5),
                new Diff(DiffType.MODIFIED, "print(\"Hello, world!\")","public class Main {", 6, 6),
                new Diff(DiffType.ADDED, "","public static void main(String[] args) {", 0, 7),
                new Diff(DiffType.ADDED, "","System.out.println(\"Hello, world!\")", 0, 8),
                new Diff(DiffType.ADDED, "","}", 0, 9),
                new Diff(DiffType.ADDED, "","}", 0, 10)
        );
        List<Diff> expected = fc.compare(original, modified).getDiffList();
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(source.get(i).getDiffType(), expected.get(i).getDiffType());
            assertEquals(source.get(i).getOriginalText(), expected.get(i).getOriginalText());
            assertEquals(source.get(i).getModifiedText(), expected.get(i).getModifiedText());
            assertEquals(source.get(i).getOriginLine(), expected.get(i).getOriginLine());
            assertEquals(source.get(i).getModdedLine(), expected.get(i).getModdedLine());
        }
    }
}