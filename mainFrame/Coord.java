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
import java.awt.Rectangle;


public class Coord extends Rectangle {
    public Coord() {
        
    }
    public Coord(int x, int y, int width, int height) {
        super(x,y,width,height);
    }
    public Coord scale(double ratio){
        if (ratio==1.0) return this;
        return new Coord((int)Math.round(x*ratio),
            (int)Math.round(y*ratio),
            (int)Math.round(this.getWidth()*ratio),
            (int)Math.round(this.getHeight()*ratio)
        );
    }
}