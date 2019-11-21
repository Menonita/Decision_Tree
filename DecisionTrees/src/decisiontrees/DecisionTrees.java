
package decisiontrees;

/**
 *
 * @author Omar
 */
public class DecisionTrees {

    private static TreeTable TT;
    private static Node N;
    
    public static void main(String[] args) {
        // TODO code application logic here
        TT=new TreeTable();
        //TT.printTable();
        
        N=new Node(TT.getTable());
        N.query();
    }
    
}
