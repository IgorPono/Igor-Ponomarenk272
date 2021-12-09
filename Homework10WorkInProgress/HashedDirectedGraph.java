import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HashedDirectedGraph {
    HashMap<Integer, DirectedNodeHashSet> hGraph;//HashMap<Integer, DirectedNodeHashSet> hGraph;
    int numVertex;
    int[] set;
    HashMap<Integer, ArrayList<Integer>> SCComponents;//what each SCC contains
    int numOfEdges;

    public HashedDirectedGraph(DirectedGraph g) {
        hGraph = new HashMap<Integer, DirectedNodeHashSet>(g.SCComponents.keySet().size());
        numVertex = g.numVertex;
        set = g.set;
        SCComponents = g.SCComponents;
        numOfEdges=0;
        Iterator<Integer> itr = g.SCComponents.keySet().iterator();
        while (itr.hasNext()) {
            int temp = itr.next();
            //System.out.println(temp);
            hGraph.put(temp, new DirectedNodeHashSet());
        }
    }

    public void addEdge(int u, int v) {
        // assume all vertices are created
        // directed edge u to v will cause outdegree of u to go up and indegree of v to go up. CHANGE THIS, CONNECTED COMPONENTS NOT VERTEXES
        if (u >= 0 && u < numVertex && v >= 0 && v < numVertex) {
            if (set[u] != set[v]) {
                //if (!hGraph.get(set[u]).outList.contains(set[v]))
                //    hGraph.get(set[u]).addToOutList(set[v]);
                //if (!hGraph.get(set[v]).inList.contains(set[u]))
                //    hGraph.get(set[v]).addToInList(set[u]);
                //if(!hGraph.get(set[u]).outList.contains(set[v]) && !hGraph.get(set[v]).inList.contains(set[u])){
                    hGraph.get(set[u]).addToOutList(set[v]);
                    hGraph.get(set[v]).addToInList(set[u]);
                //}
                numOfEdges++;
            }
        } else {
            //System.out.println("set[u] is equal to "+ set[u]);
            //System.out.println("set[u] is equal to "+ set[v]);
            throw new IndexOutOfBoundsException();
        }
    }


}
