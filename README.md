# Decision_Tree
Decision tree made in Java

A decision tree is a support tool that uses a tree-like model of decisions and their consequences. Decision trees are commonly used on decision analysis to help identify a strategy most likely to reach a goal. 
To make a decision tree, you need to have a problem you want to solve based on previous given information. Let’s take the following table as an example: Based on the information given and the resultant answers, you can create a tree that represents that same table. Note that not every column of information is included on the tree. This is because not every column of information brings useful information, or the information of other columns are more significant than others based of the analysis made (explain below). 

![Decision_Tree](https://user-images.githubusercontent.com/17207087/69584735-91e4d380-0fa3-11ea-92b4-e7920082b6d3.png)

#### Explanation of the analysis and code:
The code was programmed on Java mainly because of it was focused on Objects and it was needed to create the nodes of the tree (even though it could’ve been done on other programming languages like C, C++ or Python and get the same or even better results).
The code was separated on three parts: The Main class, the Node class and the TreeTable class. The Main class starts the program, gets the table saved on the TreeTable class and creates the first Node. The TreeTable class stores the data of the table that will be used (the table is hardcoded, but it’s mainly to demonstrate how it works). Finally, we have the node class, where every calculation is made and where it creates the tree. 
The variables that were used on the Node class were:

![Variables](https://user-images.githubusercontent.com/17207087/69585075-4bdc3f80-0fa4-11ea-8b02-c77003614bf6.png)

The logic behind some variables will be explain later (like the ‘data’ variable), but the meaning of each one of them is as follows:
* column: saves the number of the column with the most information gain
*	leaf: tells if the node is a leaf or not
*	table: a matrix where it saves the information of the given table
*	systemEntropy: saves the entropy of the system
*	data: saves relevant information for future calculations
*	newData: saves the items of the column with the most information gain
*	branches:  saves the branches created on a list

The process to transform the table to a tree can be separated on 5 steps:
1.	Get the entropy of the system
2.	Get the entropy of every element of the table
3.	Calculate the information gain
4.	Create a new branch
5.	Repeat until leaf is found

Is important to get the entropy of the system first because it can tell us if there are more branches from that point on or it’s a leaf. If the entropy is equals to zero (no chaos, 100% certainty of a result), then it means that the node is a leaf and the process ends. 
If the entropy not equals to zero, then the analysis must continue and it is necessary to get the entropy of every element of the table. The entropy is calculated with the following formula:

![image](https://user-images.githubusercontent.com/17207087/69585274-d624a380-0fa4-11ea-9ce2-2df40d4a2b37.png)
Where X is the column, Y is the element you want to get the entropy from, TotalY is the amount of items Y found on the table and yes/no are the amount of times the result shows up. Note that the Logarithm is base 2, this is because of the number of answers found on the table. If there were 3 different answers (for example: yes, no, maybe), the Logarithm would be base 3. The formula of the entropy for general purpose would look something like this:

![image](https://user-images.githubusercontent.com/17207087/69585310-f18fae80-0fa4-11ea-8b8d-82161716d4f7.png)

On the variable declaration, the ‘data’ variable was declared as an ArrayList of ArrayLists and the second ArrayList stores arrays of strings. That was probably the most confusing variable of all, but now it’s time to explain the logic behind it. There are a lot of items inside the table which we want to get the tree from, but just some of them are related by columns. The Outlook column contained Rainy, Overcast and Sunny. The Temperature column contained Hot, Mild and Cold. It’s important to make this distinction for the next part, the information gain.

The first ArrayList will contain the items found on each column so that it will be easier to organize in the future. The second array will contain the information of each element of the column in form of an Array of Strings. The array of Strings will contain the following information: Name of the element, Amount of times found on the table and the Entropy of that element. 

![image](https://user-images.githubusercontent.com/17207087/69590465-36bbdc80-0fb5-11ea-9ac1-a538968791d2.png)

On the code, there is a cycle that check all the columns and calls the method TableDataValues() for each one. The TableDataValues() function is responsible of returning the list of arrays. It receives the column where it’s going to search for the different items on the column. It will create a temporal list to save the items so it won’t repeat. It will run a cycle to check every row on the table and check the element is already saved on the list. If it’s not, it will call the function TableEntropyData(), where it receives the row and column where the element was found and returns an array of the data previously talk about. The TableDataValues() saves all the arrays the TableEntropyData() function returns on a list and it returns that list to be saved on the ‘data’ list. 

Once it’s done, the next step is to calculate the Information Gain. The information gain tells us what column is the one that gives us the must amount of information. In other words, the most relevant column to find the answer. The Information Gain is calculated using the following formula:

![image](https://user-images.githubusercontent.com/17207087/69590566-8f8b7500-0fb5-11ea-989d-c42dd5172409.png)
Where S is the System, X is the column, k is the number of items in the column and n is the current item checked.

Once obtained the column with the most information gained, the next and final step is to create branches. For that, we call out the function CreateBranch() and start identifying the different items on the column, saving them on the variable newData. Then, inside a cycle that repeats the same times as the number of different items found on the column and call another function to create the new table for the branches, called BranchTable(). 

What is done on the BranchTable() function is really simple. It receives the name of the element we are looking for, the number of rows the table is going to have and the column where is going to look for the element. The column and the name of the element is to search the rows that are related with that result. The ones that are related are separated and saved on a new matrix. Once the new table is created, it creates a new Node where it’s sent as it’s table and the Node is added to the variable ‘branch’. As the new node is created, this whole process will be repeated until the branch is identify as a leaf. 

