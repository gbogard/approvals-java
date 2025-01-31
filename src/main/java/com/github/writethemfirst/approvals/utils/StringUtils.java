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

import static java.util.stream.IntStream.range;

public class StringUtils {
    public static boolean sameContent(String expected, String actual) {
        final String[] expectedElements = splitOnLineEndings(expected);
        final String[] actualElements = splitOnLineEndings(actual);
        return expectedElements.length == actualElements.length &&
            range(0, expectedElements.length).allMatch(i -> expectedElements[i].equals(actualElements[i]));
    }

    private static String[] splitOnLineEndings(final String expected) {
        return expected.split("\r\n|\n");
    }
}
