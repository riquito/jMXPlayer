package src.Model;

public class Tree {
    HNode root;
    
    public Tree(){
        root=null;
    }
    
    protected void insert(HNode Main_node){
        //XXX non considera chiavi esistenti
        HNode Child_node = null;
        HNode root_node = this.root;
        while (root_node != null){
            Child_node=root_node;
            if(Main_node.key.compareTo(root_node.key)<0)
                 root_node=root_node.left;
            else root_node=root_node.right;
        }
        Main_node.position=Child_node;
        if (Child_node==null)
            this.root=Main_node;
        else {
            if (Main_node.key.compareTo(Child_node.key)<0)
                Child_node.left=Main_node;
            else Child_node.right=Main_node;
        }
    }
    
    public void put(Comparable key, Object value){
        if (key==null) {
            throw new NullPointerException();
        }
        HNode Main_node=new HNode(key,value);
        this.insert(Main_node);
    }
    
    public Object get(Comparable key){
        HNode Main_node=this.root;
        int comp;
        
        while (Main_node!=null && (comp=key.compareTo(Main_node.key))!=0)
          if (comp<0)
            Main_node=Main_node.left;
          else
            Main_node=Main_node.right;
        return Main_node != null ? Main_node.value : null;
    }
    
    public Object getNearest(Integer key){
        HNode Main_node=this.root;
        int comp;
        
        Comparable nearest=null;
        int diff=0, tmp_diff=0;
        
        if (Main_node!=null) {
            //creo falso che verra' sicuramente sovrascritto nella prima
            // iterazione, per non aggiungere if per il primo caso
            // nel ciclo while
            diff=1+Math.abs(key-(Integer)Main_node.key);
        }
        while (Main_node!=null && (comp=key.compareTo((Integer)Main_node.key))!=0) {
            if (diff>(tmp_diff=Math.abs(key-(Integer)Main_node.key))) {
                diff=tmp_diff;
                nearest=Main_node.key;
            }
            
            if (comp<0)
                Main_node=Main_node.left;
            else 
                Main_node=Main_node.right;
        }
        return Main_node != null ? Main_node.key : nearest;
    }
}