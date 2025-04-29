import java.util.ArrayList;
import java.util.List;

public class HardcodedPuzzles {
    private static final String[] PUZZLE_NAMES = {
        "Easy (39 clues)",
        "Medium (32 clues)",
        "Hard (17 clues)"
    };

    private static final String[] PUZZLE_STRINGS = {
        // Easy (39 clues)
        "530070000600195000098342560800060003400803001700020046061530280000419005090080079",
        // Medium (32 clues, Project Euler #96)
        "003020600900305001001806400008102900700000008006708200002609500800203009005010300",
        // Hard (17 clues, minimal)
        "000000002000006000001000090000100000000800000000030000040000700000200000300000000"
    };

    public static List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (String n : PUZZLE_NAMES) names.add(n);
        return names;
    }

    public static List<SudokuBoard> getPuzzles() {
        List<SudokuBoard> list = new ArrayList<>();
        for (String s : PUZZLE_STRINGS) {
            list.add(SudokuBoard.fromString(s));
        }
        return list;
    }
}
