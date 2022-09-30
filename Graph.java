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
    System.out.println("\n");
    System.out.println("Array of nodes adjacent to node " + s.name);
    System.out.println(rtnArray);
    return rtnArray;
  }

  public void findConnectedComponents() {

  }
}
