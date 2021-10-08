import java.util.*;

public class MaxHeap<E extends Comparable<E>> extends ArrayList<E> {
    // construct an empty Heap using ArrayList
    // with root at index 0.
    // don't use binary tree for implementing the heap.
    public ArrayList<E> list;

    public MaxHeap() {
        list = new ArrayList<E>();
    }

    // returns max value
    public E findMax() {
        return list.get(0);
    }

    // adds a new value to the heap at the end of the Heap and
    // adjusts values up to the root to ensure Max heap property is satisfied.
    // parent of node at i is given by the formula (i-1)/2
    // throw appropriate exception
    public void addHeap(E val) {
        list.add(val);
        int index = list.size() - 1;
        while (list.get(index).compareTo(list.get((index - 1) / 2)) > 0) {
            E temp = list.get(index);
            list.set(index, list.get((index - 1) / 2));
            list.set(((index - 1) / 2), temp);
            index = (index - 1) / 2;
        }
    }


    //returns the max value at the root of the heap by swapping the last value
    // and percolating the value down from the root to preserve max heap property
    // children of node at i are given by the formula 2i+1,2i+2, to not exceed the
    // bounds of the Heap index, namely, 0 ... size()-1.
    // throw appropriate exception
    public E removeHeap() { //what is this method supposed to do?
        E value = this.findMax();
        E temp = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        removeHeapHelper(0);
        return temp;
    }

    public void removeHeapHelper(int index){
        int largestIndex = index;
        int leftChild = 2*index+1;
        int rightChild = 2*index+2;
        if((leftChild<list.size())&&list.get(index).compareTo(list.get(leftChild))<0){
            largestIndex = leftChild;
        }
        if((rightChild<list.size())&&list.get(largestIndex).compareTo(list.get(rightChild))<0){
            largestIndex = rightChild;
        }
        if(largestIndex!=index){
            E temp = list.get(index);
            list.set(index,list.get(largestIndex));
            list.set(largestIndex,temp);
            removeHeapHelper(largestIndex);
        }

    }

    // takes a list of items E and builds the heap and then prints
    // decreasing values of E with calls to removeHeap().
    public void heapSort(List<E> list) {
        this.buildHeap(list);
        int size = list.size();
        while (size>0) {
            System.out.println(removeHeap());
            size--;
        }

    }

    // merges the other maxheap with this maxheap to produce a new maxHeap.
    public void heapMerge(MaxHeap<E> other) {
        this.buildHeap(other.list);
    }

    //takes a list of items E and builds the heap by calls to addHeap(..)
    public void buildHeap(List<E> list) {
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            this.addHeap((E) iter.next());
        }
    }

    public void printHeap() {
        System.out.print(list);
    }


    public static void main(String[] args) {
    }
}


