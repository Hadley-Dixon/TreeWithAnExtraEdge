// Hadley Dixon, Ellen Veomett, Lab Assignment 5, 11/16/23

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /***
         * The code below can be edited.  It is simply here to support your own testing.
         */
        int[][] Example1 = {{0, 1}, {1, 2}, {1, 3}, {1, 4}, {4, 5}, {3, 5}, {5, 6}};
        int[] sol1 = findCutEdge(Example1);
        System.out.println("The first solution is");
        System.out.println(Arrays.toString(sol1));

        int[][] Example2 = {{0, 1}, {1, 2}, {1, 3}, {2, 3}, {4, 5}, {3, 5}, {5, 6}};
        int[] sol2 = findCutEdge(Example2);
        System.out.println("The second solution is");
        System.out.println(Arrays.toString(sol2));

        int[][] Example3 = {{0, 1}, {1, 2}, {1, 3}, {2, 6}, {4, 5}, {3, 5}, {5, 6}};
        int[] sol3 = findCutEdge(Example3);
        System.out.println("The third solution is");
        System.out.println(Arrays.toString(sol3));

        int[][] Example4 = {{0, 1}, {1, 2}, {2, 0}, {2, 3}, {3, 4}, {4, 5}, {5, 2}};
        int[] sol4 = findCutEdge(Example4);
        System.out.println("The fourth solution is");
        System.out.println(Arrays.toString(sol4));

        int[][] Example5 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}, {5, 4}, {5, 6}, {6, 5}};
        int[] sol5 = findCutEdge(Example5);
        System.out.println("The fifth solution is");
        System.out.println(Arrays.toString(sol5));

        int[][] Example6 = {{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 3}, {5, 4}, {5, 6}, {6, 5}};
        int[] sol6 = findCutEdge(Example6);
        System.out.println("The sixth solution is");
        System.out.println(Arrays.toString(sol6));

        int[][] Example7 = {{0, 0}};
        int[] sol7 = findCutEdge(Example7);
        System.out.println("The \]lkjn sixth solution is");
        System.out.println(Arrays.toString(sol7));
    }

    /***
     * Method which returns an edge from a cyclic graph such that, once removed from the graph, a tree remains.
     * @param edges int[][] an array of n pairs of numbers
     * @return returns an int[] which will be a pair of numbers, corresponding to an edge in the cycle
     */
    public static int[] findCutEdge(int[][] edges) {
        // Relied heavily on this resource to understand disjoint sets:
        // https://www.geeksforgeeks.org/introduction-to-disjoint-set-data-structure-or-union-find-algorithm/

        int n = edges.length + 1; // Initialize the number of vertices in the graph
        DisjointSets disSet = new DisjointSets(n); // Create an instance of a disjoint set

        // Iterate through edges in the array of edges
        for (int[] edge : edges) {
            // Store the representative elements as roots
            int rep1 = disSet.Find(edge[0]);
            int rep2 = disSet.Find(edge[1]);

            if (rep1 == rep2) { // If a cycle is created
                return edge; // Return the edge that forms a cycle
            }
            disSet.Union(rep1, rep2); // Union the set of roots
        }

        return edges[0]; // No cycle found
    }

    private static class DisjointSets {

        public int n;
        private int[] setArr;

        /***
         * Constructor (required to create disjoint sets)
         * @param numElts initially sets are singletons: {0}, {1}, . . . {numElts-1}
         */
        public DisjointSets(int numElts){
            n = numElts;
            int[] tempArr = new int[n];
            for(int i=0; i<n; i++){
                tempArr[i] = -1;
            }
            setArr = tempArr;
        }

        /***
         * Finds which set int k is in
         * @param k int whose set we want to find
         * @return representative element (root of uptree)
         */
        public int Find(int k)
        {
            // if `k` is not the root
            if (setArr[k] >= 0)
            {
                // path compression
                setArr[k] =  Find(setArr[k]);
                return setArr[k];
            }
            else{
                return k;
            }
        }

        /***
         * remember rank is an upper bound on tree height
         * (or more precisely, rank <= -treeHeight -1)
         * @param k set element (int)
         * @return rank of set containing k
         */
        private int getRank(int k){
            return setArr[Find(k)];
        }

        /***
         * Take union of two sets.  Union by rank
         * @param a int in one set
         * @param b int in the other set
         */
        public void Union(int a, int b)
        {
            // find the root of the sets in which elements `x` and `y` belongs
            int x = Find(a);
            int y = Find(b);

            // if `x` and `y` are present in the same set
            if (x == y) {
                return;
            }

            // Always attach a smaller depth tree under the root of the deeper tree.
            if (getRank(x) > getRank(y)) { // remember ranks are negative
                setArr[x] = y;
            }
            else if (getRank(x) < getRank(y)) {
                setArr[y] = x;
            }
            else {
                setArr[x] = y;
                setArr[Find(y)] = setArr[Find(y)] -1;
            }
        }
        public void printSets(){
            System.out.println(Arrays.toString(setArr));
        }
    }
}