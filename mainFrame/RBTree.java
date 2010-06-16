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

class RBNode extends HNode{
    RBNode left,right,p;
    short color;
    
    public RBNode(Comparable key, Object data){
        super(key,data);
        this.color=RBTree.BLACK;
    }
}

class HNode {
    HNode left,right,p;
    
    Comparable key;
    Object value;
    
    public HNode(Comparable key, Object value){
        this.key=key;
        this.value=value;
        this.left=this.right=this.p=null;
    }
}

class Tree {
    HNode root;
    
    public Tree(){
        root=null;
    }
    
    protected void insert(HNode z){
        //XXX non considera chiavi esistenti
        HNode y=null;
        HNode x=this.root;
        while (x!=null){
            y=x;
            if(z.key.compareTo(x.key)<0)
                 x=x.left;
            else x=x.right;
        }
        z.p=y;
        if (y==null)
            this.root=z;
        else {
            if (z.key.compareTo(y.key)<0)
                y.left=z;
            else y.right=z;
        }
    }
    
    public void put(Comparable key, Object value){
        if (key==null) {
            throw new NullPointerException();
        }
        HNode x=new HNode(key,value);
        this.insert(x);
    }
    
    public Object get(Comparable key){
        HNode x=this.root;
        int comp;
        
        while (x!=null && (comp=key.compareTo(x.key))!=0)
          if (comp<0)
            x=x.left;
          else
            x=x.right;
        return x != null ? x.value : null;
    }
    
    public Object getNearest(Integer key){
        HNode x=this.root;
        int comp;
        
        Comparable nearest=null;
        int diff=0, tmp_diff=0;
        
        if (x!=null) {
            //creo falso che verra' sicuramente sovrascritto nella prima
            // iterazione, per non aggiungere if per il primo caso
            // nel ciclo while
            diff=1+Math.abs(key-(Integer)x.key);
        }
        while (x!=null && (comp=key.compareTo((Integer)x.key))!=0) {
            if (diff>(tmp_diff=Math.abs(key-(Integer)x.key))) {
                diff=tmp_diff;
                nearest=x.key;
            }
            
            if (comp<0)
                x=x.left;
            else 
                x=x.right;
        }
        return x != null ? x.key : nearest;
    }
}