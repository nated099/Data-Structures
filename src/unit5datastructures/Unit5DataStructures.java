
/**
    *          Individual Project 5
    *          Nathaniel Kerr
    *          2/5/2017
    *          Data Structures 
    *          (CS230-1701A-01)
    */  

package unit5datastructures;

import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;

class STL {
    LinkedList<Contributors> list = new LinkedList<Contributors>();
    public Contributors firstLink, baselink, current, node, head, tail;
    static int twelve = 0; //marker for list addition insufficiency
    STL(){
        firstLink = null; //preparing the firstLink node
    }

    public void Pull(){ //for pulling items from the list
        list.removeLast();
    }

    public void Display(){ //method to display the sorted stack
        System.out.println(list.get(0).fName + " test");
        node = list.getLast();
        while(node != null){
            System.out.print(node.Display()+" Index loc: ");
            System.out.println(list.indexOf(node));
            node = node.next;
        }
        if(twelve == 1)
            System.out.println(list.get(0).Display()+" Index loc: "+ list.indexOf(list.get(0)));
    }
    public void Push(String fName, String lName, String country, String phone, double contribution, int id) {
        Contributors current = new Contributors(fName, lName, country, phone, contribution, id); //node to hold our current contributor data
        head = current;
        if(list.isEmpty()){ //first addition only
            list.add(current);
            current.next = firstLink; //adjusting node positions for placement
            firstLink = current;
            System.out.println("ADDED: "+current.fName +" to " +list.indexOf(current));
        }
        else {
            baselink = list.getLast(); //the last node on the link is the top of the stack where we begin
            while(baselink != null) {
                if(current.id > baselink.id ){ //if the current id is greater than the baselink id just add it to the list
                    list.add(current);
                    current.next = firstLink;
                    firstLink = current;
                    System.out.println("Updated loc "+ list.indexOf(current)+" with: "+current.fName +" " + current.id);
                    break;
                }
                else if(current.id < baselink.id) { //if baselink id is greater than current id shuffle the stack appropriately
                    node = list.getFirst();
                    while (node.id < current.id) {
                        node = node.next;
                    }
                    head = node;
                    if(node.next != null){
                        tail = node.next;
                        list.add(list.indexOf(tail), current);
                    }
                    break;
                }
            }
        }
    }
    public String Search(String name){
        System.out.println();
        System.out.println("Searching");
        String[] linesep = name.split(" "); //separates the name if there is a space between first and last name.
        node = list.getLast();              //sets our node to search through the linked list
        String rtn = "";
        String response = "";       
        int i = 0;

        // if the string is split by a space, meaning first and last name entered, ensure that both are aligned to a node in the list
         if(name.indexOf(' ') == -1){ 
            while(node != null){        //constraint to prevent errors and searching past the linked list
                if(node.fName.equalsIgnoreCase(linesep[0])){    //linesep[0] will represent the first or last name in this if-then condition
                    rtn = node.Display();
                    response = "Contributor found";
                    break;              //breaks the loop to prevent overwriting our return variable
                }
                else if(node.lName.equalsIgnoreCase(linesep[0])){
                    rtn = node.Display();
                    response = "Contributor found";
                    break;
                }
                node = node.next;
            }
        }
        else {
             //in this condition, there are two strings given, first and last name. linesep[0] represents the first name and linesep[1] the last
            while(node != null){
                if(node.fName.equalsIgnoreCase(linesep[0]) && node.lName.equalsIgnoreCase(linesep[1])){
                    rtn = node.Display();
                    response = "Contributor found";
                    break;
                }
                else if(node.fName.equalsIgnoreCase(linesep[0])){
                    response = "Contributor first name does not match last name";
                    rtn = "";
                }
                else if(node.lName.equalsIgnoreCase(linesep[1])){
                    response = "Contributor first name does not match last name";
                    rtn = "";
                }
                node = node.next;
            }
            
        }
        
        if(rtn.isEmpty()){
                response = "Name not found";
                rtn = "";
        }
        
        if(!(response.isEmpty())){
            System.out.println(response);       //A string printed to provide feedback on the results of the search
        }
        return rtn;
    }
    public void AltPush(String fName, String lName, String country, String phone, double contribution, int id){ //sorts in reverse order of the push method
        Contributors current = new Contributors(fName, lName, country, phone, contribution, id); //node to hold our current contributor data
        if(list.isEmpty()){     //first addition only
            list.add(current);
            firstLink = current;    //adjusting node positions for placement
            System.out.println("ADDED: "+current.fName +" to " +list.indexOf(current));
        }
        else {
            baselink = list.getFirst(); //the last node on the link is the top of the stack where we begin
            while(baselink != null) {
                if(current.id < baselink.id ){ //if the current id is less than the baselink id just add it to the list
                    list.add(current);
                    current.next = firstLink;
                    firstLink = current;
                    System.out.println("Updated loc "+ list.indexOf(current)+" with: "+current.fName +" " + current.id);
                    twelve = 1;
                    break;     
                }
                else if(current.id > baselink.id) { //if baselink id is less than current id shuffle the stack appropriately
                    node = list.getLast();
                    while(node.id < current.id && node.next != null) {  //sets the node in the position 
                        node = node.next;
                        System.out.println("#");
                    }
                    list.add(list.indexOf(node), current);
                    System.out.println("Updated loc "+ list.indexOf(current)+" with: "+current.fName +" " + current.id);
                    System.out.println(list.indexOf(node));
                    twelve = 1;
                    break;
                }
            }
        }
    }
}

class HashFunction {    //Class to provide hash object to manage Contributor ID's
    int[] array;
    int arraySize, itemsInArray, i;
    
    public HashFunction(int size) { //constructor for future use if expanding the hash table
        arraySize = size;
        array = new int[size];
        Arrays.fill(array, -1);
    }
    public HashFunction() { //default constructor for unit 2
        arraySize = 5;
        array = new int[5];
        Arrays.fill(array, -1);
    }
    public void hashInsertion(int value){
        int errHandle = 0;
        int arrayIndex = value%arraySize; //a modulus has been chosen as an algorithm to apply to the hash indexing for mathematical placement of values and fast searches to find values.
        System.out.println("Attempting to place ID#" + value + " at index #" + arrayIndex);
        
        while(!(array[arrayIndex]==-1)) {    //negative 1 is the signature that an index is not filled. If it is filled the hash insertion function will increment through the index until a empty space is available.
            ++arrayIndex;
            if(arrayIndex > arraySize){
                arrayIndex = 0;
            }
            System.out.print("Collision prevented. Moving to index #");
            errHandle = 1;
        }
        if(errHandle == 1)
            System.out.println(arrayIndex);
        
        array[arrayIndex] = value;
    }
    public void RemoveID(int id){
        int test=id%arraySize;

        for(i=0;i<arraySize;i++){   //In case we go over the size of the array, our variable starts at zero and iterates from there.
            if(test > arraySize){
                test = 0;
            }
            if(array[(test) + i]==id){
                array[(test) + i] = -1;
                System.out.println("Object ID Removed from hash");
            }
        }
    }
    public void HashVisibleTable(){
        System.out.println("Hash Table");
        for(i=0;i<arraySize;i++){       //simple iterative for loop to print all hash index values
            if(array[i]==-1)
                System.out.println("Index #"+i+": is empty");
            else
                System.out.println("Index #"+i+": "+array[i]);
        }
    }
}

class Contributors {
    //Declaration of variables to be used in each object referenced by the linked list
    String fName, lName, country, phone;
    double contribution;
    int id;
    public Contributors next;
    //Object constructor
    public Contributors (String fName, String lName, String country, String phone, double contribution, int id){
        this.fName = fName;
        this.lName = lName;
        this.country = country;
        this.phone = phone;
        this.contribution = contribution;
        this.id = id;
    }
    
    public Contributors(){
        System.out.println(this.fName + this.lName + this.country + this.phone + this.contribution + this.id);            
    }
    public int ReturnID(){
        return this.id;
    }
    public void ReturnAll(int id){
        System.out.println("Name: "+this.fName+" last name: "+this.lName+" Country: "+this.country+" Phone#: "+this.phone+" Contribution: $"+this.contribution+" ID#: "+this.id);
    }
    public String Display() {
        String dsply = ("Name: "+this.fName+" last name: "+this.lName+" Country: "+this.country+" Phone#: "+this.phone+" Contribution: $"+this.contribution+" ID#: "+this.id);
        //System.out.println("Name: "+this.fName+" last name: "+this.lName+" Country: "+this.country+" Phone#: "+this.phone+" Contribution: $"+this.contribution+" ID#: "+this.id);
        return dsply;
    }
}

public class Unit5DataStructures {
   static final String CSV_SEPARATOR = ",";
   public static void main(String[] args) {
        String csvFile = "D:\\Users\\Xxwitherspoon\\Documents\\HOMEWORK\\Data Structures CS230-1701A-01\\contributors.csv"; //variable with the location of the csv file
        String line = "", splitCsv = ",", srch;
        int i = 0;
        STL list = new STL();
        HashFunction hash = new HashFunction();
        Contributors[] contributor = new Contributors[10]; //utilizing an array for easy access to our example contributors. In real life we would have input go straight to the list stack
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) { //trying to create a buffered reader created to take input data from csv file, assuming it's in the location provided

            while ((line = br.readLine()) != null) {    //while loop with the condition that the currently read line is not null
                String[] linesep = line.split(splitCsv);
                // debugging line System.out.println("fname= " + linesep[1] + " , lname=" + linesep[2] + "]");
                contributor[i] = new Contributors(linesep[0], linesep[1], linesep[2], linesep[3], Double.parseDouble(linesep[4]), Integer.parseInt(linesep[5])); 
                //adding each contributor object to the list
                list.Push(linesep[0], linesep[1], linesep[2], linesep[3], Double.parseDouble(linesep[4]), Integer.parseInt(linesep[5]));
                //updating the hash
                hash.hashInsertion(contributor[i].ReturnID());
                i++; //counts for the contributor array object
            }

        } catch (IOException e) {   //
            e.printStackTrace();
        }
        //Utilizing the search method of STL
        list.Display();
        
        System.out.println("Please enter a name to search (first, last or both)");
        //Option pane display to recieve search string
        srch = JOptionPane.showInputDialog("Please enter a name to search (first, last or both)");
        System.out.println(list.Search(srch));
        
       //Output of linked list iterating through each object in the list

        list.Pull();
        System.out.println();
        System.out.println("One object has been popped");   //spacing
        hash.RemoveID(contributor[0].ReturnID());

        System.out.println();
        list.Display();
        
        System.out.println();
        //Sorting the popped item into a different place on the stack
        list.AltPush("Sam", "Phoenix", "USA", "903555777", 500, 54);
        hash.hashInsertion(contributor[0].ReturnID());
        
        System.out.println("The object has been pushed back onto the stack");
        System.out.println();
                
        list.Display();
        System.out.println();
        hash.HashVisibleTable();
    } 
}

