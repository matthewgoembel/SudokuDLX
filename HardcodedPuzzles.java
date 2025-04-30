import java.util.ArrayList;
import java.util.List;

public class HardcodedPuzzles {
    private static final String[] PUZZLE_NAMES = {
        "Easy (40 clues)",
        "Medium (32 clues)",
        "Hard (17 clues)"
    };

    private static final String[] PUZZLE_STRINGS = {
        // Easy (40 clues)
        "500678900672095008000300067859060023020003001703900800900000200080010605345280079",
        // Medium (32 clues)
        "003020600900305001001806400008102900700000008006708200002609500800203009005010300",
        // Hard (17 clues)
        "000000002000006000001000090000100000000800000000030000040000700000200000300000000"
    };

    /** Returns the three puzzle names. */
    public static List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (String n : PUZZLE_NAMES) {
            names.add(n);
        }
        return names;
    }

    /** Returns a list of SudokuBoard instances for each hard-coded puzzle. */
    public static List<SudokuBoard> getPuzzles() {
        List<SudokuBoard> list = new ArrayList<>();
        for (String s : PUZZLE_STRINGS) {
            list.add(SudokuBoard.fromString(s));
        }
        return list;
    }
}
