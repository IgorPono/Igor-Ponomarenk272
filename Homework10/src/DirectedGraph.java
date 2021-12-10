import java.util.*;
import java.io.*;

public class DirectedGraph {
    ArrayList<DirectedNodeList> dGraph;
    int numVertex;
    boolean[] marked;
    int[] finishing;
    int[] set;
    HashMap<Integer, ArrayList<Integer>> SCComponents;
    int currentLeader = 0;
    int counter = 0;
    int componentCounter = 0;
    int maxComponent = 0;
    int currentComponent = 0;


    public DirectedGraph() {
        dGraph = new ArrayList<>();
        numVertex = 0;

    }

    public DirectedGraph(int n) {
        numVertex = n;
        dGraph = new ArrayList<>(n);
        marked = new boolean[n];
        set = new int[n];
        SCComponents = new HashMap<Integer, ArrayList<Integer>>(n);
        for (int i = 0; i < numVertex; i++)
            dGraph.add(new DirectedNodeList());

    }

    public void addEdge(int u, int v) {
        // assume all vertices are created
        // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.

        if (u >= 0 && u < numVertex && v >= 0 && v < numVertex) {
            if (u != v) {
                getNeighborList(u).addToOutList(v);
                getNeighborList(v).addToInList(u);
            }
        } else throw new IndexOutOfBoundsException();
    }

    public DirectedNodeList getNeighborList(int u) {
        return dGraph.get(u);
    }

    public void printAdjacency(int u) {
        DirectedNodeList dnl = getNeighborList(u);
        System.out.println("vertices going into " + u + "  " + dnl.getInList());
        System.out.println("vertices going out of " + u + "  " + dnl.getOutList());
        System.out.println();
    }

    public void postOrderDepthFirstTraversal() {
        this.marked = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++)
            if (!marked[i])
                postOrderDFT(i);

    }

    public void postOrderDFT(int v) {

        marked[v] = true;

        for (Integer u : dGraph.get(v).getOutList())
            if (!marked[u]) postOrderDFT(u);
        System.out.println(v);
    }


    public void depthFirstTraversal() {
        this.marked = new boolean[numVertex];
        for (int i = 0; i < numVertex; i++)
            if (!marked[i])
                dFT(i);

    }

    public void dFT(int v) {
        System.out.println(v);
        marked[v] = true;

        for (Integer u : dGraph.get(v).getOutList())
            if (!marked[u]) dFT(u);

    }


    public void numOfSCC() {
        resetCounters();
        finishing = new int[numVertex];
        this.postOrderDepthFirstTraversalReverse();
        //System.out.println();
        //System.out.println(Arrays.toString(finishing));
        counter = 0;
        //now perform Depth First Traversal except on finishing number labels
        this.depthFirstTraversalUtil();
        //System.out.println();
        System.out.println("Number of components is " + componentCounter);
        System.out.println("Size of the largest component is " + maxComponent);
        resetCounters();
        //System.out.println(Arrays.toString(set));


    }

    public HashedDirectedGraph createHashedGraph() {
        HashedDirectedGraph returnThis = new HashedDirectedGraph(this);
        return returnThis;
    }

    public void depthFirstTraversalUtil() {
        this.marked = new boolean[numVertex];
        for (int i = finishing.length - 1; i >= 0; i--)
            if (!marked[finishing[i]]) {
                currentLeader = finishing[i];
                SCComponents.put(currentLeader, new ArrayList<Integer>());
                componentCounter++;
                dFTUtil(finishing[i]);
                maxComponent = Math.max(maxComponent, currentComponent);
                currentComponent = 0;
            }

    }

    public void dFTUtil(int v) {
        marked[v] = true;
        currentComponent++;
        SCComponents.get(currentLeader).add(v);
        set[v] = currentLeader;
        for (Integer u : dGraph.get(v).getOutList()) {
            if (!marked[u]) {
                dFTUtil(u);
            }
        }

    }

    public void postOrderDepthFirstTraversalReverse() {
        this.marked = new boolean[numVertex];

        for (int i = 0; i < numVertex; i++)
            if (!marked[i]) {
                postOrderDFTReverse(i);
            }

    }

    public void postOrderDFTReverse(int v) {

        marked[v] = true;

        for (Integer u : dGraph.get(v).getInList())
            if (!marked[u]) postOrderDFTReverse(u);
        finishing[counter] = v;
        counter++;
    }

    public int find(int u) {
        return set[u];
    }


    public void resetCounters() {
        counter = 0;
        componentCounter = 0;
        maxComponent = 0;
        currentComponent = 0;
        currentLeader = 0;
    }


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Slashdot0902.txt");
        File file2 = new File("src/Test2.txt");
        File file3 = new File("src/TestFile3.txt");
        File testFile = file;
        Scanner initialScan = new Scanner(testFile);
        int max = 0;
        if (initialScan.hasNext())
            max = initialScan.nextInt();
        while (initialScan.hasNextInt()) {
            max = Math.max(max, initialScan.nextInt());
        }
        //System.out.println(max);
        DirectedGraph test = new DirectedGraph(max + 1);
        //System.out.println();


        Scanner scan = new Scanner(testFile);
        while (scan.hasNextLine()) {
            test.addEdge(Integer.parseInt(scan.next()), Integer.parseInt(scan.next()));
        }


        test.numOfSCC();
        HashedDirectedGraph hashedGraph = test.createHashedGraph();

        Scanner s = new Scanner(testFile);
        while (s.hasNextLine()) {
            int vertex1 = Integer.parseInt(s.next());
            int vertex2 = Integer.parseInt(s.next());
            hashedGraph.addEdge(vertex1, vertex2);
        }
        System.out.println("Number of verticies in the reduced graph is " + hashedGraph.hGraph.size());
        //System.out.println(hashedGraph.SCComponents.size());
        Iterator edgeCounter = hashedGraph.hGraph.keySet().iterator();
        int numOfEdgesLocal = 0;
        while (edgeCounter.hasNext()) {
            int tempValue = (int) edgeCounter.next();
            numOfEdgesLocal += hashedGraph.hGraph.get(tempValue).inList.size();
        }


        //System.out.println();
        System.out.println("Number of edges in the reduced graph with repeats is " + hashedGraph.numOfEdges);
        System.out.println("Number of edges in the reduced graph without repeats is " + numOfEdgesLocal);
        //Included both of these people I was told 28331 was the correct amount of edges but that number seems to include repeated edges between
        //components, so I included an answer with both the repeated edges and just the unique edges
        //Although it is definitely possible that I am misunderstanding the context of the data
        System.out.println();

        /*Iterator tempiter = hashedGraph.hGraph.keySet().iterator(); //used for printing values represented by a leader, works with smaller data tests/for testing
        while (tempiter.hasNext()) {
            Integer currentValue = (Integer) tempiter.next();
            System.out.println("Leader is " + currentValue);
            System.out.println("InList is " + hashedGraph.hGraph.get(currentValue).inList);
            System.out.println("OutList is " + hashedGraph.hGraph.get(currentValue).outList);
            System.out.println("Values Represented by Current Leader "  + hashedGraph.SCComponents.get(currentValue));
            System.out.println();
        }*/


    }


}
