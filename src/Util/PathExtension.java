/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package src.Util;

import java.io.File;

/**
 *
 * @author Riquito
 */
public class PathExtension {
    /**
     * Join two or more pathname components, inserting the system file separator
     *  as needed
     * 
     */
    public static String join(String basePath, String fileName) {
        if (basePath.endsWith("/") || basePath.endsWith("\\") || basePath.endsWith(":")) {
        	basePath = basePath.substring(0, basePath.length() - 2);
        }

        return basePath + File.separator + fileName;
    }
}
