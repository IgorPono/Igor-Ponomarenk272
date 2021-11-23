import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WeightedEdge implements Comparable<WeightedEdge> {
    int v1;
    int v2;
    double weight;

    public WeightedEdge() {
        v1 = 0;
        v2 = 0;
        weight = 0;

    }

    public WeightedEdge(int i, int j, double w) {

        v1 = i;
        v2 = j;
        weight = w;

    }

    public double getWeight() {
        return weight;

    }

    public int compareTo(WeightedEdge other) {

        return (Double.valueOf(weight).compareTo(Double.valueOf(other.weight)));

    }

    public String toString() {

        return ("(" + v1 + "," + v2 + ")" + weight);

    }



    public static ArrayList<WeightedEdge> KruskalUnionSet(PriorityQueue<WeightedEdge> queue, int numOfVertexes) {
        int numOfEdges = 0;
        ArrayList<WeightedEdge> minTree = new ArrayList<WeightedEdge>(numOfVertexes - 1);
        ConnectedComponents components = new ConnectedComponents(numOfVertexes, 0);
        while (minTree.size() < numOfVertexes - 1) {
            WeightedEdge current = queue.poll();


            if (components.findSet[current.v1] != components.findSet[current.v2]) {
                minTree.add(current);
                components.mergeComponents(current.v1, current.v2);
            }


        }
        return minTree;
    }



    /*public static ArrayList<WeightedEdge> KruskalTreeCompression(PriorityQueue<WeightedEdge> queue, int numOfVertexes) {
        ArrayList<WeightedEdge> minTree = new ArrayList<WeightedEdge>(numOfVertexes - 1);
        CompressedPaths components = new CompressedPaths(numOfVertexes, 0);
        while (minTree.size() < numOfVertexes - 1) {
            WeightedEdge current = queue.poll();
            int p1 = components.find(current.v1);
            int p2 = components.find(current.v2);
            if(p1!=p2){
                minTree.add(current);
                components.merge(p1,p2);
            }
        }
        return minTree;
    } */

    public static ArrayList<WeightedEdge> KruskalTreeCompression(PriorityQueue<WeightedEdge> queue, int numOfVertexes) {
        ArrayList<WeightedEdge> minTree = new ArrayList<WeightedEdge>(numOfVertexes - 1);
        CompressedPaths components = new CompressedPaths(numOfVertexes, 0);
        while (minTree.size() < numOfVertexes - 1) {
            WeightedEdge current = queue.poll();
            if(components.find(current.v1)!=(components.find(current.v2))){
                minTree.add(current);
                components.merge(components.find(current.v1),components.find(current.v2));
            }
        }
        return minTree;
    }


    public static void main(String[] args) throws FileNotFoundException {
        List<WeightedEdge> alw = new ArrayList<>();
        int max = 0;
        File actualEdges = new File("src/artist_edges.txt");
        File actualWeights = new File("src/Weights.txt");
        File testEdges = new File("src/EdgesTest.txt");
        File testWeights = new File("src/WeightsTest.txt");
        File currentEdges = actualEdges;
        File currentWeights = actualWeights;
        Scanner initialScan = new Scanner(currentEdges);
        while (initialScan.hasNextInt()) {
            max = Math.max(max, initialScan.nextInt());
        }
        //System.out.println(max+1);
        Scanner edgesScanner = new Scanner(currentEdges);
        Scanner weightScanner = new Scanner(currentWeights);
        while (edgesScanner.hasNextLine()) {
            alw.add(new WeightedEdge(Integer.parseInt(edgesScanner.next()), Integer.parseInt(edgesScanner.next()), Double.parseDouble(weightScanner.next())));
            //String e = edgesScanner.nextLine();
            //String w = weightScanner.next();
            //String [] line = e.split("\\s");
            //int v1 = Integer.parseInt(line[0]);
            //int v2 = Integer.parseInt(line[1]);
            //double weight = Double.parseDouble(w);
            //alw.add(new WeightedEdge(v1,v2,weight));
        }


        PriorityQueue<WeightedEdge> pq2 = new PriorityQueue<>(alw);
        PriorityQueue<WeightedEdge> pq3 = new PriorityQueue<>(alw);



        ArrayList<WeightedEdge> b,c;
        b=KruskalUnionSet(pq2,max+1);
        c=KruskalTreeCompression(pq3,max+1);
        int totalWeightb=0;
        int totalWeightc=0;
        for(int i=0;i<b.size();i++){
            totalWeightb+=b.get(i).getWeight();
        }
        for(int i=0;i<c.size();i++){
            totalWeightc+=c.get(i).getWeight();
        }

        System.out.println(totalWeightb);
        System.out.println(totalWeightc);

        System.out.println(b.size());
        System.out.println(c.size());
       /* Comparator<WeightedEdge> crp = Comparator.comparingDouble(WeightedEdge::getWeight);
        Collections.sort(alw,crp);
        System.out.println(alw);
        
        crp= crp.reversed();
        Collections.sort(alw,crp);
        System.out.println(alw);*/


    }

}
