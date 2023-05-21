package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {

        List<Item> lst = new ArrayList<>(); // Create a new items list
        lst.addAll(this.lst); // Copy the original list
        Collections.sort(lst, new relativeWeightComparator()); // Sort the new list according to the costume comparator
        return lst.iterator();
    }

    // Comparator class for sorting Item list according to relative weight as defined in Q2
    private class relativeWeightComparator implements Comparator<Item> {

        public int compare(Item a, Item b) {
            double relativeWeightA = (double) (a.value / a.weight);
            double relativeWeightB = (double) (b.value / b.weight);
            return Double.compare(relativeWeightB, relativeWeightA);
        }
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {

        return true;
    }

    private double calculateFraction(double leftSpace, Item element) {
        return (double) (leftSpace / element.weight);
    }

    // Calculate the summation of Items list by weight
    private double sum(List<Item> lst) {
        double summation = 0;
        for (Item item : lst) {
            summation += (double) item.weight;
        }
        return summation;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {

        if (!(sum(candidates_lst) + element.weight <= capacity)) {
            double fraction = calculateFraction(capacity - sum(candidates_lst), element);
            element.weight = fraction * element.weight;
            element.value = fraction * element.value;
        }

        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {

        return (sum(candidates_lst) == capacity);
    }
}
