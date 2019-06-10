package src.Model;

class RBNode extends HNode{
    RBNode left,right,position;
    short color;
    
    public RBNode(Comparable key, Object data){
        super(key,data);
        this.color=RBTree.BLACK;
    }
}
