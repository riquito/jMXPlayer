package src.Model;

public class Tree {
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