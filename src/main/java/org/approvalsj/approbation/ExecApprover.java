package org.approvalsj.approbation;

import java.io.IOException;
import java.nio.file.Path;

import static java.lang.Runtime.*;
import static java.lang.String.*;

public class ExecApprover implements Approver {
    private final String command;
    final String programFiles = System.getenv("ProgramFiles");

    public ExecApprover(String command) {
        this.command = command;
    }

    @Override
    public void approve(Path approved, Path received) {
        String cmdLine = command
                .replace("%programFiles%", programFiles.toString())
                .replace("%approved%", approved.toString())
                .replace("%received%", received.toString());
        try {
            getRuntime().exec(cmdLine);
        } catch (IOException e) {
            System.err.println(format("Cannot execute %s : %s", cmdLine, e));
        }
    }
}
