package src.Model;

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
