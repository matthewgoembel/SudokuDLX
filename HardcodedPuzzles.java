import java.util.ArrayList;
import java.util.List;

public class HardcodedPuzzles {
    private static final String[] PUZZLE_NAMES = { "Easy", "Medium", "Hard" };
    private static final String[] PUZZLE_STRINGS = {
        // Easy
        "530070000600195000098000060800060003400803001700020006060000280000419005000080079",
        // Medium
        "030020500008307010050000000000006085040001709800204000000000040010503200002010060",
        // Hard
        "000000907000420180000705026100904000050000040000507009920108000034059000507000000"
    };

    /** Returns a list of puzzle names in the same order as the puzzles. */
    public static List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (String n : PUZZLE_NAMES) names.add(n);
        return names;
    }

    /** Returns a list of SudokuBoard instances for Easy, Medium, and Hard. */
    public static List<SudokuBoard> getPuzzles() {
        List<SudokuBoard> list = new ArrayList<>();
        for (String s : PUZZLE_STRINGS) {
            list.add(SudokuBoard.fromString(s));
        }
        return list;
    }
}
