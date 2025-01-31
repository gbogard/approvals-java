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
package com.github.writethemfirst.approvals.reporters;

import com.github.writethemfirst.approvals.Reporter;
import com.github.writethemfirst.approvals.files.ApprovalFiles;

import static java.lang.String.format;

/**
 * # ThrowsReporter
 *
 * That basic reporter simply allows to throw an {@link AssertionError} in case a mismatch is found in the approval
 * files. It is always available since it's only throwing a pure Java error, and requires no particular software or
 * library to be present on the running machine.
 */
public class ThrowsReporter implements Reporter {

    @Override
    public void mismatch(final ApprovalFiles files) {
        throw new AssertionError(format(
            "expected: <%s> but was: <%s>",
            files.approvedContent(),
            files.receivedContent()));
    }

    /**
     * A {@link ThrowsReporter} is considered as always available since it has no external requirements at all.
     *
     * @return True. Always and forever.
     */
    @Override
    public boolean isAvailable() {
        return true;
    }
}
