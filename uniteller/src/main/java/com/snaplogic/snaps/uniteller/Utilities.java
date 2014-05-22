/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2013, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.uniteller;

import com.google.inject.Inject;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.utilities.URLEncoder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.snaplogic.snaps.uniteller.Messages.*;

/**
 * This class contains FileSystem and URI related utility methods
 * 
 * @author svatada
 */
public class Utilities {
    static final String URL_PROTOCOL_FILE = "file";
    private static final String ROOT_FILE_PATH = URL_PROTOCOL_FILE + ":/";
    private List<URI> selectedFileList = new ArrayList<>();
    private String lastScheme;
    private boolean firstUri = true;
    static final String OS_NAME = "os.name";
    static final String WIN = "win";
    @Inject
    private URLEncoder urlEncoder;

    /**
     * Produces a list of files matching the given wildcard or regex. Its scope is package-default
     * because this method is called from another class in the same package.
     * 
     * @param propertyValues - Snap property values
     * @param accountType - account type
     * @param userInfo - user credential, e.g. "yourUsername:yourPassword@"
     */
    void populate(final String fileOrFolder) {
        FileSystem fileSystem;
        Path path;
        String fullPath = urlEncoder.validateAndEncodeURI(fileOrFolder).toString();
        URI pathUri = getUriFor(fullPath);
        checkForSameScheme(pathUri);
        // get file system object
        fileSystem = getFileSystemFor(pathUri);
        if (fileSystem != null) {
            try {
                path = createPath(fileSystem, pathUri);
                // If it is a regular file, add it to the selected file list.
                // If it is a directory, go get the list of files in the directory.
                if (Files.isRegularFile(path)) {
                    selectedFileList.add(pathUri);
                } else if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
                    // if the file does not exist, throw ExecutionException with a
                    // corresponding error message (symbolic link is not supported)
                    throw new ExecutionException(ERR_FILE_NOT_FOUND).formatWith(fileOrFolder)
                            .withReason(ERR_FILE_NOT_FOUND_REASON)
                            .withResolution(ERR_FILE_NOT_FOUND_RESOLUTION);
                } else {
                    // a file which is not a regular file nor a directory is not supported
                    throw new ExecutionException(ERR_UNSUPPORTED_FILE_TYPE)
                            .formatWith(fileOrFolder).withReason(ERR_UNSUPPORTED_FILE_TYPE_REASON)
                            .withResolution(ERR_UNSUPPORTED_FILE_TYPE_RESOLUTION);
                }
            } finally {
                closeFileSystem(fileSystem, fileOrFolder);
            }
        } else {
            // If there is no FileSystemProvider, ignore wildcard and
            // includeSubfolders properties.
            selectedFileList.add(pathUri);
        }

    }

    @SuppressWarnings("unchecked")
    FileSystem getFileSystemFor(final URI pathUri) {
        FileSystem fileSystem;
        try {
            // TODO: psung: the support for local file system can be removed after removing all
            // local files for fileFolder property from integration tests.
            switch (pathUri.getScheme().toLowerCase()) {
                case URL_PROTOCOL_FILE:
                    // In case of Local Filesystem Provider like BsdFileSystemProvider,
                    // FileSystem object can be created only for the root directory.
                    fileSystem = FileSystems.getFileSystem(new URI(ROOT_FILE_PATH));
                    break;
                default:
                    fileSystem = FileSystems.getFileSystem(pathUri);
            }
        } catch (ProviderNotFoundException e) {
            // If there is no provider, this could be a file path on a Filesystem that
            // does not support folder navigation like Sldb, Http, etc.
            fileSystem = null;
        } catch (Exception ex) {
            // In case of all other exceptions, try to create a new filesystem instead of
            // trying to reuse the old one.
            try {
                fileSystem = FileSystems.newFileSystem(pathUri, Collections.EMPTY_MAP);
            } catch (IOException e) {
                // Failed to create a Filesystem object for a given URI.
                throw new ExecutionException(e, ERR_CREATE_FILESYSTEM)
                        .formatWith(pathUri.toString()).withReason(e.getMessage())
                        .withResolution(ERR_CREATE_FILESYSTEM_RESOLUTION);
            }
        }
        return fileSystem;
    }

    // The scope of this method is package-default because it is called from another class in the
    // same package.
    List<URI> getResult() {
        return selectedFileList;
    }

    // The scope of this method is package-default because it is called from another class in the
    // same package.
    void clear() {
        selectedFileList.clear();
    }

    /**
     * Finds all files which matches wildcard or regex in a given directory.
     * 
     * @param path Path object of the directory
     * @param pattern regular expression pattern
     * @param includeSubfolder a flag to search subdirectories recursively if true
     */
    private void findFiles(Path path) {
        // prepare a filter object
        DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
            public boolean accept(Path file) throws IOException {
                return true;
            }
        };
        // Search for regular files in the directory. If found, add them to the selected file
        // list.
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, filter)) {
            for (Path entry : stream) {
                Path fileName = entry.getFileName();
                if (fileName != null && Files.isRegularFile(entry)) {
                    selectedFileList.add(entry.toUri());
                }
            }
        } catch (IOException e) {
            // Directory could not be found for a given path object.
            String error = String.format(ERR_DIR_NOT_FOUND, path.toString(), e.getMessage());
            throw new ExecutionException(e, error).withReason(error).withResolution(
                    RESOLUTION_DIR_NOT_FOUND);
        }
    }

    Path createPath(FileSystem fileSystem, URI uri) {
        String fileFolder = uri.getPath();
        String osName = System.getProperty(OS_NAME).toLowerCase();
        if (osName.contains(WIN) && fileFolder.startsWith("/")) {
            // absolute files on windows do not start with "/" otw Java File Path api errors
            fileFolder = fileFolder.substring(1, fileFolder.length());
        }
        return fileSystem.getPath(fileFolder);
    }

    /**
     * Converts a file or folder path string to URI.
     * 
     * @param fileOrFolder - a file or folder path string
     * 
     * @return URI object
     */
    URI getUriFor(String fileOrFolder) {
        try {
            return new URI(fileOrFolder);
        } catch (URISyntaxException e) {
            // URI class constructor found a syntax error in the fileOtFolder argument and threw an
            // URISyntaxException because the argument is not qualified as an URI.
            String error = String.format(ERR_URI_SYNTAX, fileOrFolder, e.getMessage());
            throw new ExecutionException(e, error).withReason(error).withResolution(
                    ERR_MALFORMED_URL_RESOLUTION);
        }
    }

    private void checkForSameScheme(URI uri) {
        String scheme = uri.getScheme();
        if (firstUri) {
            lastScheme = scheme;
            firstUri = false;
        } else {
            if (!scheme.equals(lastScheme)) {
                // All files or folders in a Read Snap instance must have the same scheme. This
                // rule is found violated here.
                String error = String.format(ERR_DIFFERENT_SCHEMES, lastScheme, scheme);
                throw new ExecutionException(error).withReason(error).withResolution(
                        RESOLUTION_DIFFERENT_SCHEMES);
            }
        }
    }

    private void closeFileSystem(FileSystem fileSystem, String fileOrFolder) {
        try {
            // Windows would otherwise create an unsupported method exception...
            if (!System.getProperty(OS_NAME).toLowerCase().contains(WIN)) {
                fileSystem.close();
            }
        } catch (IOException e) {
            throw new ExecutionException(ERR_CLOSE_FILESYSTEM).formatWith(fileOrFolder)
                    .withReason(ERR_CLOSE_FILESYSTEM_REASON)
                    .withResolution(RESOLUTION_CUSTOMER_SUPPORT);
        } catch (UnsupportedOperationException e) {
            // some file systems do not support close()
        }
    }
}