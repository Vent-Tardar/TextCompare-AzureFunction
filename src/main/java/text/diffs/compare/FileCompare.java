package text.diffs.compare;

import text.model.CompareResults;
import text.model.Diff;
import text.model.DiffType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileCompare {

    private int lineOrigin = 1;
    private int lineModified = 1;

    /**
     * Compare two list of strings from text files
     *
     * @param origin   List of strings from original file
     * @param modified List of strings from modified file
     * @return Object containing compare results
     */
    public CompareResults compare(List<String> origin, List<String> modified) {
        CompareResults results = new CompareResults(new ArrayList<>());

        lineOrigin = 1;
        lineModified = 1;

        List<Diff> diffs = compareToEnd(origin, modified);
        results.setDiffList(diffs);
        return results;
    }

    /**
     * Compare to end of the largest list of strings
     *
     * @param origin   List of strings from original file
     * @param modified List of strings from modified file
     * @return Comfortable to reading list of differences
     */
    private List<Diff> compareToEnd(List<String> origin, List<String> modified) {
        List<Diff> diffs = new ArrayList<>();
        while ((lineOrigin-1 < origin.size()) && (lineModified-1 < modified.size())) {
            String original = origin.get(lineOrigin-1), modify = modified.get(lineModified-1);
            lineOrigin++; lineModified++;
            if (original.equals(modify))
                continue;
            diffs.add(new Diff(DiffType.MODIFIED, original, modify, lineOrigin-1, lineModified-1));
        }

        while (lineOrigin-1 < origin.size()) {
            diffs.add(new Diff(DiffType.DELETED, origin.get(lineOrigin-1), "", lineOrigin, 0));
            lineOrigin++;
        }

        while (lineModified-1 < modified.size()) {
            diffs.add(new Diff(DiffType.ADDED, "", modified.get(lineModified-1), 0, lineModified));
            lineModified++;
        }

        return diffs;
    }
}
