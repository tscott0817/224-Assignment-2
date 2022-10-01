// CS224 Fall 2022

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Graph {
  ArrayList<Node> nodes;

  public Graph() {
    nodes = new ArrayList<Node>();
  }

  public void addNode(Node node) {
    for (Node n: this.nodes) {
      if (n == node) {
        System.out.println("ERROR: graph already has a node " + n.name);
        assert false;
      }
    }
    this.nodes.add(node);
  }

  public void addEdge(Node n1, Node n2) {

    // outgoing edge
    int idx1 = this.nodes.indexOf(n1);
    if (idx1 < 0) {
      System.out.println("ERROR: node " + n1.name + " not found in graph");
      assert false;
    }

    int idx2 = this.nodes.indexOf(n2);
    if (idx2 < 0) {
      System.out.println("ERROR: node " + n2.name + " not found in graph");
      assert false;
    }
    n1.addEdge(n2);
  }

  public ArrayList<Node> DFS(Node s) {

    // Create stack and push first node to it
    Stack<Node> stack = new Stack<Node>();
    stack.push(s);
    boolean[] visited = new boolean[this.nodes.size() + 1]; // Not sure if this should be + 1 or not
    ArrayList<Node> rtnArray = new ArrayList<>();

    // Loop through stack and check if elements have been visited
    while (!stack.isEmpty()) {
      Node u;
      u = stack.peek();
      stack.pop();

      // If node not visited yet, mark as checked and then add to return array
      // if it is adjacent to initial node
      if (!visited[u.name]) {
        visited[u.name] = true;
        for (Node v : u.adjlist) {
          rtnArray.add(v);
        }
      }
    }
    //System.out.print("DFS("+ s.name + ")" + "\n");
    return rtnArray;
  }

  public void findConnectedComponents() {

    ArrayList<Node> initialNode;
    ArrayList<Node> connectedToS;
    ArrayList<Node> connectedToV;
    ArrayList<Node> tempArray = new ArrayList<>();

    // Loop through all Nodes starting at arbitrary Node S
    for (Node s: this.nodes) {

      // Call DFS on current Node S
      initialNode = DFS(s);
      tempArray.add(s); // Add the starting node to array
      System.out.print("DFS(" + s.name + ")\n");

      // Loop through all available Nodes V, check whether there is an ede
      // connecting to Node s
      for (Node v: this.nodes) {

        // If there is an edge from V to S, add to the temp array of connections
        if (initialNode.contains(v)) {

          // Call DFS on all the Nodes V that have a path to S
          connectedToS = DFS(v);
          //System.out.println("V connections to Node S = " + s.name + ": " + connectedToS);

          for (int i = 0; i < connectedToS.size(); i++) {

            // Call DFS one more time to check the array produced by the edges
            // connecting to Node V
            if (!tempArray.contains(connectedToS)) {
              tempArray.add(connectedToS.get(i));
              connectedToV = DFS(connectedToS.get(i));

              // Loop one last time to check values that are connected to V
              // TODO: I need to further check these new values, so the nested for loops are probably not the best approach
              for (int j = 0; j < connectedToV.size(); j++) {
                tempArray.add(connectedToV.get(j));
                //System.out.println("Connected to  " + connectedToV.get(j) + ": " + connectedToV);

              }
            }
          }
        }
      }
      //System.out.println("Array of All Connections: " + tempArray);

      // Take all connected nodes values from temp array, remove duplicates and sort
      ArrayList<Node> rtnArray = new ArrayList<>();
      for (Node n: tempArray) {
        if (!rtnArray.contains(n)) {
          rtnArray.add(n);
        }
      }

      // Sort the final ArrayList
      for(int i = 0; i < rtnArray.size(); i++){
        for(int j = i; j < rtnArray.size(); j++){
          if(rtnArray.get(i).name > rtnArray.get(j).name){
            Node tempNode = rtnArray.get(i);
            rtnArray.set(i, rtnArray.get(j));
            rtnArray.set(j, tempNode);
          }
        }
      }
      System.out.println(rtnArray);
      //System.out.println("Connected component from S =  " + s.name + ": " + rtnArray);
      System.out.println("\n");

      // Empty arrays for next iteration
      tempArray.removeAll(tempArray);
      rtnArray.removeAll(rtnArray);
    }
  }
}
