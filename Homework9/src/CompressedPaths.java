import java.util.ArrayList;

public class CompressedPaths {
    int[] findSet;
    ArrayList<MyLinkedList<Integer>> vertexSets;
    static int numVertex;
    static int numEdges;
    static ArrayList<WeightedEdge> edgeSet;
    static int totalChanges;
    int[] setArr;
    int[] height;
    int count;

    public CompressedPaths(int n, int m) {
        numVertex = n;
        numEdges = m;
        totalChanges = 0;
        count = 0;
        findSet = new int[n];
        setArr = new int[n];
        height = new int[n];
        vertexSets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            findSet[i] = i;
            setArr[i] = i;
            height[i] = 0;
            vertexSets.add(new MyLinkedList<Integer>());
            vertexSets.get(i).addFirst(i);
        }
        // edgeSet = new ArrayList<>();
    }

    public int find(int x) {
        int r = x;
        while (findSet[r] != r)
            r = findSet[r];
        int i = x;
        while (i != r) {
            int j = findSet[i];
            findSet[i] = r;
            i = j;
        }
        return r;
    }


    public void merge(int a, int b) {
        if (height[a] == height[b]) {
            height[a] = height[a] + 1;
            findSet[b] = a;
        } else {
            if (height[a] > height[b])
                findSet[b] = a;
            else
                findSet[b] = a;
        }
    }

    /*public void merge(int a, int b) {
        if (height[a] == height[b]) {
            height[a] = height[a] + 1;
        }
        findSet[b] = a;
    }*/
}
