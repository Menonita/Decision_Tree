
package decisiontrees;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Omar
 */
public class Node {
    
    private int column;
    private boolean leaf;
    private String[][] table;
    private double systemEntropy;
    private ArrayList<ArrayList<String[]>> data;
    private ArrayList<String> newData;
    private ArrayList<Node> branches;
    
    public Node(String[][] info){
        leaf=false;
        data=new ArrayList<>(); //Stores the obtained data of every element on the table
        newData=new ArrayList<>(); //Stores the different values found on the column with more 'information gain'
        branches=new ArrayList<>(); //Saves the tables that will be given to the branches created from this node
        
        table=new String[info.length][info[0].length];
        for(int i=0; i<info.length; i++){
            for(int j=0; j<info[0].length; j++){
                table[i][j]=info[i][j];
            }
        }
        
        systemEntropy=getSystemEntropy(); //Calculate the Entropy of the given table (the Entropy of the System)
        if(systemEntropy==0){ //if the resultant entropy is equals to zero, then it means we are on a leaf and there's nothing else to check
            leaf=true;
            //System.out.println("Hoja Encontrada");
        }
        else{ //If the entropy is greater than zero, then it's necessary to calculate the entropy of every element of every column
            for(int col=0; col<table[0].length-1; col++){
                data.add(TableDataValues(col)); 
            }
            //printData();
            column=informationGain(); //This function is connected to several other functions (Calculate the Gain, Create new Tables, Create new Branches)
            createBranch(column);
        }
    }
    
    public void query(){
        if(leaf==true){
            System.out.println(table[0][table[0].length-1]+"\n"+table[1][table[0].length-1]);
        }else{
            boolean compare=false;
            String input;
            Scanner scan=new Scanner(System.in);
            System.out.println(table[0][column]);
            input=scan.nextLine();
            for(String info : newData){
                if(info.equalsIgnoreCase(input)){
                    input=info;
                    compare=true;
                    break;
                }
            }
            if(compare==true)
                branches.get(newData.indexOf(input)).query();
            else
                System.out.println("Error: Not a Valid Input");
        }
    }
    
    //Note that this following functions only works on information with 2 posible answers (Yes & No). For it to work with different answers,
    // you need to adapt the check from this function and the 'attributeEntropy' function to check multiple answers
    public double getSystemEntropy(){ //probability of getting 'yes', 'no', and the number of expected answers
        double x,y;
        double yes=0, total;
        
        total=table.length-1; //gets the number of rows of the table (-1 because the title row is excluded)
        for(int i=1; i<table.length;i++){
            if(table[i][table[0].length-1].equals("Yes")){
                yes++;
            }
        }
        
        x=(yes/total)*(Math.log(yes/total)/Math.log(2));
        y=((total-yes)/total)*(Math.log((total-yes)/total)/Math.log(2));
        if(Double.isNaN(-x-y)){ //Because Log(0) is not a number, it's necessary to include this check
            return 0; 
        }
        return -x-y;
    }
    
    public double attributeEntropy(String name, int col, int total){
        double x,y;
        double yes=0, t=total;
        
        for(int i=1; i<table.length; i++){
            if(table[i][col].equals(name)){
                if(table[i][table[0].length-1].equals("Yes")){
                    yes++;
                }
            }
        }
        x=(yes/t)*(Math.log(yes/t)/Math.log(2));
        y=((t-yes)/t)*(Math.log((t-yes)/t)/Math.log(2));
        if(Double.isNaN(-x-y)){
            return 0;
        }
        return -x-y;
    }
    
    public ArrayList<String[]> TableDataValues(int col){
        ArrayList<String[]> list=new ArrayList<>(); //Stores on a list the information of the given column
        ArrayList<String> repeatedData=new ArrayList<>(); //List to check the data doesn't repeat information

        for(int row=1; row<table.length; row++){
            if(!repeatedData.contains(table[row][col])){ //Checks if the value isn't stored yet
                repeatedData.add(table[row][col]); //Adds the value to the list so it wont repeat
                list.add(TableEntropyData(row, col)); //Recieves an array of information and saves it on a list
            }
        }
        return list; //return the list of values of the column checked
    }
    public String[] TableEntropyData(int row, int col){
        String[] entropyData=new String[3]; //Array that contains the element name, the number of times found and its entropy
        int attributes;
        double entropy;
        
        attributes=getAttribute(table[row][col], col);
        entropy=attributeEntropy(table[row][col], col, attributes);
        
        entropyData[0]=table[row][col]; //Name of the element
        entropyData[1]=String.valueOf(attributes); //Amount of times found
        entropyData[2]=String.valueOf(entropy); //Entropy of the element
        
        return entropyData;

    }
    
    public int getAttribute(String name, int col){ //Simple function that gets the amount of time the attribute is found on a column
        int counter=0;
        
        for(int i=1; i<table.length; i++){
            if(table[i][col].equals(name)){
                counter++;
            }
        }
        
        return counter;
    }
    
    public void printData(){
        String resp="";
        for(ArrayList<String[]> info : data) {
            for(String[] gain : info) {
                for(int i=0; i<gain.length; i++){
                    resp+=gain[i]+"\t";
                }
                resp+="\n";
            }
        }
        System.out.println(resp);
    }
    
    public int informationGain(){ //Calculates the information gain and returns the column with the most gain
        double[] gain=new double[data.size()]; //array that stores the gain of every column
        double max=-1;
        int num=0; //An iterator and the column with the most information gain
        
        //This part is the reason why it was created a lisf of lists. The first list represents every column and the second contains 
        // the information of every element. The gain is calculated with the elements of each column, so it's easier to organize this way
        for(ArrayList<String[]> info : data) {
            for(String[] values : info) { //calculates the gain of each column
                gain[num]-=Double.valueOf(values[1])/(table.length-1)*Double.valueOf(values[2]); //formula to calculate the gain         
            }
            gain[num]+=systemEntropy;
            num++;
        }
        for(int i=0; i<gain.length; i++){ //Checks for the maximum gain on the array
            if(gain[i]>max){
                max=gain[i];
                num=i;
            }
        }
        //System.out.println("Max: "+max+"\tColumn: "+num);
        return num; //Sends the number of the column with the grater gain
    }
    
    public void createBranch(int col){ //
        //This is for checking the different posible answiers on one column
        for(int row=1; row<table.length; row++){
            if(!newData.contains(table[row][col])){
                newData.add(table[row][col]);
            }
        }
        //Saves the amount of rows found that have the values gotten from the previous step
        for(int k=0; k<newData.size(); k++){
            //System.out.println("Name: "+newData.get(k)+"\tCount: "+data.get(col).get(k)[1]);
            branchTable(newData.get(k),Integer.valueOf(data.get(col).get(k)[1]), col); //.get(x)=gets the column->.get(y)=get the element-> [1]=get the count of elements
        }
        
    }
    
    public void branchTable(String name, int tableRows, int col){ //Creates a table that the new branch will get
        String[][] newTable=new String[tableRows+1][table[0].length-1]; //Rows=Amount of times the element was found, Columns=Original Length minus the element's column branch
        int aux=0, colAux=0;;
        
        for(int i=0; i<table[0].length; i++){ //Saves the title of the columns
            if(i!=col){
                newTable[0][aux]=table[0][i];
                aux++;
            }
        }
        aux=1;
        for(int i=1; i<table.length; i++){
            if(table[i][col].equals(name)){
                for(int j=0; j<table[0].length; j++){
                    if(j!=col){
                        newTable[aux][colAux]=table[i][j];
                        colAux++;
                    }
                }
                aux++;
            }
            colAux=0;
        }//Note: The use of variables 'aux' & 'colAux' is because the elements of the new table will not be saved on the same position as the original table
        /*String resp="";
        for(int i=0; i<newTable.length; i++){
            for(int j=0; j<newTable[0].length; j++){
                resp+=newTable[i][j]+"\t";
            }
            resp+="\n";
        }
        System.out.println(resp);*/
        branches.add(new Node(newTable)); //Creates a new branch with the new Table as parameter. This will continue until the branches are recognized as leaves
    }
    
}
