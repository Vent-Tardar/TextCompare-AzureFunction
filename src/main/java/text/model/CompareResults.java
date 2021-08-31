package text.model;

import java.util.List;

public class CompareResults {
    private List<Diff> diffList;

    public CompareResults(List<Diff> diffList) {
        this.diffList = diffList;
    }

    public List<Diff> getDiffList() {
        return diffList;
    }

    public void setDiffList(List<Diff> diffList) {
        this.diffList = diffList;
    }
}
