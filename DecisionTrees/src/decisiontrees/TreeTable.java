
package decisiontrees;

/**
 *
 * @author Omar
 */
public class TreeTable {
    
    private String[][] table;
    
    public TreeTable(){
        table=new String[][]{
            {"What's the Outlook?", "How's the Temp?", "How's the Humidity?", "Is it Windy? (True/False)","Go out and play?"},
            //{"Outlook", "Temp", "Humidity", "Windy","Play?"},
            {"Rainy","Hot", "High", "False","No"},
            {"Rainy","Hot", "High", "True","No"},
            {"Overcast","Hot", "High", "False","Yes"},
            {"Sunny","Mild", "High", "False","Yes"},
            {"Sunny","Cold", "Normal", "False","Yes"},
            {"Sunny","Cold", "Normal", "True","No"},
            {"Overcast","Cold", "Normal", "True","Yes"},
            {"Rainy","Mild", "High", "False","No"},
            {"Rainy","Cold", "Normal", "False","Yes"},
            {"Sunny","Mild", "Normal", "False","Yes"},
            {"Rainy","Mild", "Normal", "True","Yes"},
            {"Overcast","Mild", "High", "True","Yes"},
            {"Overcast","Hot", "Normal", "False","Yes"},
            {"Sunny","Mild", "High", "True","No"}
        };
    }
    
    public String[][] getTable(){
        return table;
    }
    
    public void printTable(){
        String resp="";
        for(int i=0; i<table.length; i++){
            for(int j=0; j<table[0].length; j++){
                resp+=table[i][j]+"\t";
            }
            resp+="\n";
        }
        System.out.println(resp);
    }
}
