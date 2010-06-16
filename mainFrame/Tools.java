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


package mainFrame;

import java.io.File;

/**
 *
 * @author Riquito
 */
public class Tools {
    
    /** Creates a new instance of Tools */
    public Tools() {
    }
    
    /**
     * Join two or more pathname components, inserting the system file separator
     *  as needed
     * 
     */
    static String joinPath(String basename, String filename) {
        if (basename.endsWith("/")||basename.endsWith("\\")||basename.endsWith(":"))
            basename=basename.substring(0,basename.length()-2);
        return basename+File.separator+filename;
    }
}
