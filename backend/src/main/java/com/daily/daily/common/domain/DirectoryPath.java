package com.daily.daily.common.domain;

import com.daily.daily.common.exception.InvalidDirectoryException;

public class DirectoryPath {
    private static final String DELIMITER = "/";
    private String path; // ex : /dir1/dir2/dir3/ ... ./dir50

    private DirectoryPath(String[] directories) {
        validateDirectories(directories);

        StringBuilder builder = new StringBuilder(DELIMITER);
        for (String directory : directories) {
            builder.append(directory).append(DELIMITER);
        }
        this.path = builder.substring(0, builder.length() - DELIMITER.length()); // 마지막 디렉토리 DELIMITER 제거.
    }

    public static DirectoryPath of(String... directories) {
        return new DirectoryPath(directories);
    }

    private static void validateDirectories(String[] directories) {
        if (directories == null || directories.length == 0) {
            throw new InvalidDirectoryException();
        }

        for (String directory : directories) {
            if (directory == null || directory.isBlank()) {
                throw new InvalidDirectoryException();
            }
        }
    }

    public String getPath() {
        return path;
    }
}
