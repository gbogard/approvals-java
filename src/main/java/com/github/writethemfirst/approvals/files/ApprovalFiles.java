/*
 * Approvals-Java - Approval testing library for Java. Alleviates the burden of hand-writing assertions.
 * Copyright © 2018 Write Them First!
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.writethemfirst.approvals.files;

import com.github.writethemfirst.approvals.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static com.github.writethemfirst.approvals.utils.FileUtils.createParentDirectories;
import static com.github.writethemfirst.approvals.utils.FileUtils.silentRead;
import static java.lang.String.format;

/**
 * # ApprovalFiles
 *
 * *Approval Testing* relies on 2 specific files for storing the execution results of the *Program Under Tests* and
 * running comparisons over the output of the program.
 *
 * *Approvals-Java* provides various features, including being able to approve single files, but also to validate the
 * output of a program using an approved folder (which can then contain various sub-folders and files).
 *
 * This class allows to store references of the 2 particular entries to consider: the *approved* and *received* entries,
 * which are used to store the reference data, and the temporary output of the program for comparison. The class manages
 * both cases provided by the framework: single files, or complete folders.
 *
 * This class not only will hold the references of the *approved* and *received* entries, but it'll also provide all the
 * necessary methods allowing to compare and validate the files or folders.
 */
public class ApprovalFiles extends ApprovedAndReceived {

    private final boolean approvedWasEmpty;

    /**
     * Constructs an ApprovalFiles instance for a pair of *approved* and *received* entries.
     *
     * @param approved         An *approved* entry (can be either a file or a folder)
     * @param received         A *received* entry (can be either a file or a folder)
     * @param approvedWasEmpty remembers if the approved file existed before approbation
     */
    private ApprovalFiles(final Path approved, final Path received, final boolean approvedWasEmpty) {
        super(approved, received);
        this.approvedWasEmpty = approvedWasEmpty;
    }

    /**
     * Constructs an ApprovalFiles instance for a pair of *approved* and *received* entries.
     *
     * @param approved An *approved* entry (can be either a file or a folder)
     * @param received A *received* entry (can be either a file or a folder)
     */
    public ApprovalFiles(final Path approved, final Path received) {
        this(approved, received, false);
    }


    /**
     * Builds a pair of approval entries from the provided folder and method name. The path for both *approved* and
     * *received* files will be computed and used as approval files.
     *
     * @param folder     The folder in which the approval files should be located
     * @param methodName The name of the method calling the test. It is used to actually name the approval files
     * @return An {@link ApprovalFiles} object, containing the pair of generated *approved* and *received* entries
     */
    public static ApprovalFiles build(final Path folder, final String methodName) {
        return new ApprovalFiles(
            buildApprovalFilePath(folder, methodName, "approved"),
            buildApprovalFilePath(folder, methodName, "received"));
    }


    /**
     * Creates a default approval file if it doesn't exist yet, with received content. If it already exists, that method
     * does nothing.
     */
    public void createApprovedFileIfNeeded() {
        if (approvedWasEmpty) {
            FileUtils.copy(received, approved);
        }
    }

    /**
     * Creates an empty approval file if it doesn't exist yet. If it already exists, that method does nothing.
     */
    public ApprovalFiles createEmptyApprovedFileIfNeeded() {
        final File file = approved.toFile();
        if (!file.exists()) {
            try {
                createParentDirectories(approved);
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                return new ApprovalFiles(approved, received, true);
            } catch (final IOException e) {
                throw new RuntimeException(format("Can't create an empty file at <%s>.", file), e);
            }
        } else {
            return this;
        }
    }

    @Override
    public String approvedContent() {
        return approvedWasEmpty ? "" : silentRead(approved);
    }

    /**
     * Builds and retrieves the path to an approval file from the folder, method name, and extension to use.
     *
     * @param folder     The folder in which the approval file will be searched for
     * @param methodName The name of the method calling the test. It is used to actually name the approval files
     * @param extension  The extension to use for the approval file (by default could be either *approved* or
     *                   *received*)
     * @return The path to the approval file computed from all the specified information
     */
    static Path buildApprovalFilePath(final Path folder, final String methodName, final String extension) {
        return folder.resolve(format("%s.%s", methodName.replaceAll(" ", "_"), extension));
    }


}
