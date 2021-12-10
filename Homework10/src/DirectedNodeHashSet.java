import java.util.*;
public class DirectedNodeHashSet
{
    HashSet<Integer> inList;
    HashSet<Integer> outList;


    public DirectedNodeHashSet() {

        inList = new HashSet<>();
        outList = new HashSet<>();


    }

    public void addToInList(int p) {

        inList.add(p);

    }

    public void addToOutList(int p) {

        outList.add(p);

    }

    public DirectedNodeHashSet(HashSet<Integer> in, HashSet<Integer> out) {
        inList = in;
        outList=out;
    }

    public int getInDegree(){
        return inList.size();
    }

    public int getOutDegree(){
        return outList.size();
    }

    public HashSet<Integer> getInList(){
        return inList;
    }
    public HashSet<Integer> getOutList(){
        return outList;
    }



}
