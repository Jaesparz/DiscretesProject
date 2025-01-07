package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisjointSet<T> {
    private final Map<T, T> parent = new HashMap<>();

    public DisjointSet(List<T> elements) {
        for (T element : elements) {
            parent.put(element, element);
        }
    }

    public T find(T element) {
        if (parent.get(element) != element) {
            parent.put(element, find(parent.get(element)));
        }
        return parent.get(element);
    }

    public void union(T element1, T element2) {
        T root1 = find(element1);
        T root2 = find(element2);
        if (!root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }

    public boolean isConnected(T element1, T element2) {
        return find(element1).equals(find(element2));
    }
}

