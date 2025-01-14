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
package com.github.writethemfirst.approvals.utils;

import com.github.writethemfirst.approvals.approvers.Approver;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

class FunctionUtilsTest {
    private final Approver approvals = new Approver();

    @Test
    void shouldBeCalledWithAllArguments() {
        final List<Integer> args1 = Arrays.asList(10, 20, 30);
        final List<Long> args2 = Arrays.asList(4L, 5L, 0L);
        final String results = FunctionUtils.applyCombinations(args1, args2, (a, b) -> a / b);
        approvals.verify(results);
    }


}
