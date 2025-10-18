package year2016;

import java.util.List;

public class Day4_2 {

    public String part2(List<String> input)  {
        for (String room : input) {
            String end = room.substring(room.lastIndexOf("-") + 1);

            int id = Integer.parseInt(end.substring(0, end.indexOf("[")));
            String letters = room.substring(0, room.lastIndexOf("-")).replaceAll("-", "");
            letters = shiftLetters(letters, id);

            if (letters.contains("north") && letters.contains("pole") && letters.contains("object")) {
                return String.valueOf(id);
            }
        }

        return "-1";
    }

    private String shiftLetters(String letters, int id) {
        StringBuilder incrementedLetters = new StringBuilder();
        for (int i = 0; i < letters.length(); i++) {
            int asciiCode = letters.charAt(i) + (id % 26);

            if (asciiCode > 'z') {
                asciiCode -= 26;
            }

            incrementedLetters.append((char) asciiCode);
        }

        return incrementedLetters.toString();
    }
}
