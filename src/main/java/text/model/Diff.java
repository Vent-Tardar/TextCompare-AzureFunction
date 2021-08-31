package text.model;

import java.util.Objects;

public class Diff {
    private final DiffType diffType;
    private final String originalText;
    private final String modifiedText;
    private final int originLine;
    private final int moddedLine;

    public Diff(DiffType diffType, String originalText, String modifiedText, int originLine, int moddedLine) {
        this.diffType = diffType;
        this.originalText = originalText;
        this.modifiedText = modifiedText;
        this.originLine = originLine;
        this.moddedLine = moddedLine;
    }

    public DiffType getDiffType() {
        return diffType;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getModifiedText() {
        return modifiedText;
    }

    public int getOriginLine() {
        return originLine;
    }

    public int getModdedLine() {
        return moddedLine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(diffType, originalText, modifiedText, originLine, moddedLine);
    }

    @Override
    public String toString() {
        return "Diff{" +
                "diffType=" + diffType +
                ", originalText='" + originalText + '\'' +
                ", modifiedText='" + modifiedText + '\'' +
                ", originLineNo=" + originLine +
                ", moddedLineNo=" + moddedLine +
                '}';
    }
}
