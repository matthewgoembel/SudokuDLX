import java.util.ArrayList;
import java.util.List;

public class HardcodedPuzzles {
    private static final String[] PUZZLE_NAMES = { "Easy", "Medium", "Hard" };
    private static final String[] PUZZLE_STRINGS = {
      // Easy (~41 clues, high)
      "530070000"
      + "600195000"
      + "098342560"
      + "800060003"
      + "400803001"
      + "700020046"
      + "061530280"
      + "000419005"
      + "090080079",
        
        // medium (~32 clues, moderate)
        "003020600"
      + "900305001"
      + "001806400"
      + "008102900"
      + "700000008"
      + "006708200"
      + "002609500"
      + "800203009"
      + "005010300",

        // hard (~17 clues, low) 
        "000000002"
      + "000006000"
      + "001000090"
      + "000100000"
      + "000080000"
      + "000003000"
      + "040000700"
      + "000200000"
      + "300000000"
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
