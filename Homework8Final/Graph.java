import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    int numVertex;
    int numEdge;
    ArrayList<ArrayList<Integer>> graph;

    public Graph() {
        numVertex = 0;
        numEdge = 0;
        graph = new ArrayList<>();

    }

    public Graph(int vertexCount) {

        numVertex = vertexCount;
        numEdge = 0;
        graph = new ArrayList<>(numVertex);
        for (int i = 0; i < numVertex; i++)
            graph.add(new ArrayList<>());
    }


    public ArrayList<Integer> getNeighbors(int u) {

        return graph.get(u);
    }

    public boolean edgePresent(int u, int v) {

        return (graph.get(u).contains(v));

    }

    public void addEdge(int u, int v) {
        // lets assume that the vertices are already created.
        if (u >= 0 && u < numVertex && v >= 0 && v < numVertex) {
            if (!edgePresent(u, v))
                graph.get(u).add(v);

            if (!edgePresent(v, u))
                graph.get(v).add(u);
            numEdge++;
        } else throw new IndexOutOfBoundsException();


    }

    public void addOneWayEdge(int u, int v){
        if (u >= 0 && u < numVertex && v >= 0 && v < numVertex) {
            if (!edgePresent(u, v))
                graph.get(u).add(v);
            numEdge++;
        } else throw new IndexOutOfBoundsException();


    }

    public void BFS() { //traverses the tree using BFS
        boolean[] visited = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++) {
            if (!visited[i]) {
                BFSHelper(i, visited);
            }
        }
    }

    private void BFSHelper(int value, boolean[] visited) {

        Queue<Integer> queue = new LinkedList<Integer>();
        visited[value] = true;
        queue.add(value);
        while (queue.size() != 0) {
            value = queue.poll();
            System.out.print(value + " ");
            Iterator<Integer> neighbors = this.getNeighbors(value).listIterator();
            while (neighbors.hasNext()) {
                int temp = neighbors.next();
                if (!visited[temp]) {
                    visited[temp] = true;
                    queue.add(temp);
                }
            }

        }
    }

    public void DFS() { //Traverses the tree using DFS
        boolean[] visited = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++) {
            if (!visited[i]) {
                DFSHelper(i, visited);
            }
        }
    }

    private void DFSHelper(int value, boolean[] visited) {
        //int size = 1;
        Stack<Integer> queue = new Stack<Integer>();
        visited[value] = true;
        queue.add(value);
        while (queue.size() != 0) {
            value = queue.pop();
            System.out.print(value + " ");
            Iterator<Integer> neighbors = this.getNeighbors(value).listIterator();
            while (neighbors.hasNext()) {
                int temp = neighbors.next();
                if (!visited[temp]) {
                    visited[temp] = true;
                    //size++;
                    queue.add(temp);
                }
            }

        }
    }


    public int numOfComponentsBFS() { //finds the number of components using BFS
        int counter = 0;
        boolean[] visited = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++) {
            if (!visited[i]) {
                numOfComponentsBFSHelper(i, visited);
                counter++;
            }
        }
        return counter;
    }

    private void numOfComponentsBFSHelper(int value, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<Integer>();
        visited[value] = true;
        queue.add(value);
        while (queue.size() != 0) {
            value = queue.poll();
            Iterator<Integer> neighbors = this.getNeighbors(value).listIterator();
            while (neighbors.hasNext()) {
                int temp = neighbors.next();
                if (!visited[temp]) {
                    visited[temp] = true;
                    queue.add(temp);
                }
            }

        }

    }

    public int numOfComponentsDFS() { //finds the number of components using DFS
        int counter = 0;
        boolean[] visited = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++) {
            if (!visited[i]) {
                numOfComponentsDFSHelper(i, visited);
                counter++;
            }
        }
        return counter;
    }

    private void numOfComponentsDFSHelper(int value, boolean[] visited) {
        int size = 1;
        Stack<Integer> queue = new Stack<Integer>();
        visited[value] = true;
        queue.add(value);
        while (queue.size() != 0) {
            value = queue.pop();
            Iterator<Integer> neighbors = this.getNeighbors(value).listIterator();
            while (neighbors.hasNext()) {
                int temp = neighbors.next();
                if (!visited[temp]) {
                    visited[temp] = true;
                    size++;
                    queue.add(temp);
                }
            }

        }
    }


    public int largestComponent() { //Finds size of the largest component using DFS
        ArrayList<Integer> componentSizes = new ArrayList<Integer>();
        boolean[] visited = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++) {
            if (!visited[i]) {
                largestComponentHelper(i, visited, componentSizes);
            }
        }
        int max = 0;
        for (int i = 0; i < componentSizes.size(); i++) {
            max = Math.max(max, componentSizes.get(i));
        }
        //System.out.println(componentSizes);
        return max;
    }

    private void largestComponentHelper(int value, boolean[] visited, ArrayList<Integer> componentSizes) {
        int size = 1;
        Stack<Integer> stack = new Stack<Integer>();
        visited[value] = true;
        stack.add(value);
        while (stack.size() != 0) {
            value = stack.pop();
            //System.out.print(value + " ");
            Iterator<Integer> neighbors = this.getNeighbors(value).listIterator();
            while (neighbors.hasNext()) {
                int temp = neighbors.next();
                if (!visited[temp]) {
                    visited[temp] = true;
                    size++;
                    stack.add(temp);
                }
            }

        }
        componentSizes.add(size);
    }


    public static int flyReading(File file) throws FileNotFoundException { //relies on directly reading from the file rather than using an established adjacency list
        //seems to have a long runtime, although the algorithm would get results that consistently matched with the number of components using BFS and DFS
        //Uses MyLinkedList
        int max = 0;
        Scanner initialScan = new Scanner(file);
        while (initialScan.hasNextInt()) {
            //System.out.println("loop");
            max = Math.max(max, initialScan.nextInt());
        }
        Integer [] findSet = new Integer[max+1];
        ArrayList<MyLinkedList<Integer>> set = new ArrayList<MyLinkedList<Integer>>(max + 1);
        for (int i = 0; i < findSet.length; i++) {
            findSet[i] = i;
            set.add(new MyLinkedList<Integer>());
            set.get(i).addFirst(i);
        }
      //  System.out.println("arrays and lists initialized");
        //System.out.println(Arrays.toString(findSet));
        //System.out.println();

        //for(int i=0;i<set.size();i++){
        //    set.get(i).printListForward();
        //    System.out.println();
        //}
        //System.out.println();

        Scanner scan = new Scanner(file);
       // System.out.println("about to begin looping");
        while (scan.hasNextLine()) {
            String[] nums = scan.nextLine().split("\t");
            int [] temp = new int [2];
            temp[0] = Integer.parseInt(nums[0]);
            temp[1] = Integer.parseInt(nums[1]);
          //  System.out.println(Arrays.toString(temp));
           // System.out.println();
            if(findSet[temp[0]]!=findSet[temp[1]]) {
                int newSetNumber = Math.min(findSet[temp[0]], findSet[temp[1]]);
                // System.out.println("the new set number is " + newSetNumber);
                int other = Math.max(findSet[temp[0]], findSet[temp[1]]);
                set.get(newSetNumber).appendList(set.get(other));

                int a = findSet[newSetNumber];
                for (int i = 0; i < set.get(newSetNumber).size(); i++) {
                    findSet[set.get(newSetNumber).get(i)] = a;
                }
            }
            // System.out.println(Arrays.toString(findSet));
           // System.out.println();
            //System.out.println("loop is running");
        }

        List<Integer> list = Arrays.asList(findSet);
        Set<Integer> s = new HashSet<Integer>(list);

       // System.out.println(s);
       // System.out.println();

        return s.size();
    }

    public static int flyReadingJava(File file) throws FileNotFoundException { //relies on directly reading from the file rather than using an established adjacency list
        //Same as flyReading except it uses the java linked list rather than MyLinkedList
        int max = 0;
        Scanner initialScan = new Scanner(file);
        while (initialScan.hasNextInt()) {
            //System.out.println("loop");
            max = Math.max(max, initialScan.nextInt());
        }
        Integer [] findSet = new Integer[max+1];
        ArrayList<LinkedList<Integer>> set = new ArrayList<LinkedList<Integer>>(max + 1);
        for (int i = 0; i < findSet.length; i++) {
            findSet[i] = i;
            set.add(new LinkedList<Integer>());
            set.get(i).addFirst(i);
        }
        //  System.out.println("arrays and lists initialized");
        //System.out.println(Arrays.toString(findSet));
        //System.out.println();

        //for(int i=0;i<set.size();i++){
        //    set.get(i).printListForward();
        //    System.out.println();
        //}
        //System.out.println();

        Scanner scan = new Scanner(file);
        // System.out.println("about to begin looping");
        while (scan.hasNextLine()) {
            String[] nums = scan.nextLine().split("\t");
            int [] temp = new int [2];
            temp[0] = Integer.parseInt(nums[0]);
            temp[1] = Integer.parseInt(nums[1]);
            //  System.out.println(Arrays.toString(temp));
            // System.out.println();
            if(findSet[temp[0]]!=findSet[temp[1]]) {
                int newSetNumber = Math.min(findSet[temp[0]], findSet[temp[1]]);
                // System.out.println("the new set number is " + newSetNumber);
                int other = Math.max(findSet[temp[0]], findSet[temp[1]]);
                set.get(newSetNumber).addAll(set.get(other));

                int a = findSet[newSetNumber];
                for (int i = 0; i < set.get(newSetNumber).size(); i++) {
                    findSet[set.get(newSetNumber).get(i)] = a;
                }
            }
            // System.out.println(Arrays.toString(findSet));
            // System.out.println();
            //System.out.println("loop is running");
        }

        List<Integer> list = Arrays.asList(findSet);
        Set<Integer> s = new HashSet<Integer>(list);

        // System.out.println(s);
        // System.out.println();

        return s.size();
    }






    public int numOfTrees(){ //reports the number of connected components that are trees
        int counter =0;
        boolean [] visited = new boolean[numVertex];
        for(int i=0;i<numVertex;i++){
            if(!visited[i])
                if(!numOfTreesHelper(i,visited,-1))
                    counter++;
        }
        return counter;
    }

    private boolean numOfTreesHelper(int value, boolean [] visited, int parent){
        visited[value] = true;
        int temp;
        Iterator<Integer> iter = this.getNeighbors(value).listIterator();
        while(iter.hasNext()){
            temp = iter.next();
            if(!visited[temp]){
                if(numOfTreesHelper(temp,visited,value))
                    return true;
            }
            else if(temp!=parent)
                return true;
        }
        return false;
    }

}
