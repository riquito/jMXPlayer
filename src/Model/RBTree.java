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


package src.Model;

/**
 *
 * @author Riquito
 */
public class RBTree extends Tree{
    public static final short BLACK = 0;
    public static final short RED = 1;
    
    RBNode root;
    /** Creates a new instance of RBTree */
    public RBTree() {
        super();
    }
    
    private void leftRotate(RBNode x){
        RBNode y=x.right;
        x.right=y.left;
        
        if(y.left!=null){
            y.left.p=x;
        }
        y.p=x.p;
        if(x.p==null){
            this.root=y;
        }
        else {
            if(x==x.p.left)
                 x.p.left=y;
            else x.p.right=y;
        }
        y.left=x;
        x.p=y;
    }
    
    private void rightRotate(RBNode x){
        RBNode y=x.left;
        x.left=y.right;
        
        if(y.right!=null){
            y.right.p=x;
        }
        y.p=x.p;
        if(x.p==null){
            this.root=y;
        }
        else {
            if(x==x.p.right)
                 x.p.right=y;
            else x.p.left=y;
        }
        y.right=x;
        x.p=y;
    }
    
    public void insert(RBNode x){
        super.insert(x);
        x.color=RBTree.RED;
        while(x!=this.root && x.p.color==RBTree.BLACK){
            if(x.p==x.p.p.left){
                RBNode y=x.p.p.right;
                if (y.color==RBTree.RED){
                    x.p.color=RBTree.BLACK;
                    y.color=RBTree.BLACK;
                    x.p.p.color=RBTree.RED;
                    x=x.p.p;
                }
                else{
                    if(x==x.p.right){
                        x=x.p;
                        this.leftRotate(x);
                    }
                    x.p.color=RBTree.BLACK;
                    x.p.p.color=RBTree.RED;
                    this.rightRotate(x.p.p);
                }
            }
            else {
                RBNode y=x.p.p.left;
                if (y.color==RBTree.RED){
                    x.p.color=RBTree.BLACK;
                    y.color=RBTree.BLACK;
                    x.p.p.color=RBTree.RED;
                    x=x.p.p;
                }
                else{
                    if(x==x.p.left){
                        x=x.p;
                        this.rightRotate(x);
                    }
                    x.p.color=RBTree.BLACK;
                    x.p.p.color=RBTree.RED;
                    this.leftRotate(x.p.p);
                }
            }
        }
        this.root.color=RBTree.BLACK;
    }
    
}
