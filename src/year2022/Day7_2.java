package year2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7_2 {
    public static class Directory {
        int fileSizes;
        Directory parent;
        Map<String, Directory> directories = new HashMap<>();

        Directory(Directory parent) {
            this.parent = parent;
        }
    }

    public String part2(List<String> lines) {
        Directory current = new Directory(null);
        final Directory root = current;

        for (String line : lines) {
            if (line.startsWith("$ cd")) {
                String dir = line.substring(5);

                if (dir.equals("..")) {
                    current = current.parent;
                    continue;
                } else if (dir.equals("/")) {
                    continue;
                }

                if (current.directories.get(dir) == null) {
                    current.directories.put(dir, new Directory(current));
                }
                current = current.directories.get(dir);
            } else if (!line.startsWith("$") && !line.startsWith("dir")) {
                current.fileSizes += Integer.parseInt(line.split(" ")[0]);
            }
        }

        calculateFileSizes(root);

        int freeSpace = 70000000 - root.fileSizes;
        return String.valueOf(getFileSizeToDelete(root, 30000000 - freeSpace));
    }

    public void calculateFileSizes(Directory root) {
        for (Directory dir : root.directories.values()) {
            calculateFileSizes(dir);
            root.fileSizes += dir.fileSizes;
        }
    }

    public int getFileSizeToDelete(Directory root, int spaceToDelete) {
        int fileSize = Integer.MAX_VALUE;
        for (Directory dir : root.directories.values()) {
            int fileSizeConcurrent = getFileSizeToDelete(dir, spaceToDelete);
            fileSize = Math.min(fileSize, fileSizeConcurrent);
        }

        if (root.fileSizes >= spaceToDelete) {
            fileSize = Math.min(fileSize, root.fileSizes);
        }

        return fileSize;
    }
}
