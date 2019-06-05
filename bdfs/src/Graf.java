import java.io.*;
import java.util.*;

class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";     // RED
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN

}

class Graf {
    private int wierzcholki;
    private LinkedList[] listaS;
    private Graf(int wierzcholki) {
        this.wierzcholki = wierzcholki;
        listaS = new LinkedList[wierzcholki];
        for (int i = 0; i< wierzcholki; ++i)
            listaS[i] = new LinkedList();
    }

    private void dodajK(int v, int w) {
        listaS[v].add(w);
    }

    void DFS() {
        boolean[] szare = new boolean[wierzcholki];
        for (int i=0; i<wierzcholki; ++i) if (!szare[i]) {
            System.out.print(ConsoleColors.RED + "[C] " + ConsoleColors.RESET);
            DFS(i, szare);
        }
    }
    private void DFS(int w, boolean[] sz) {
        sz[w] = true;
        System.out.print(ConsoleColors.CYAN_BOLD + (w+1) + " " + ConsoleColors.RESET);
        for (int n : (Iterable<Integer>) listaS[w]) if (!sz[n]) {
            System.out.print(ConsoleColors.YELLOW + "[" + (w+1) + "->" + (n+1) + "] " + ConsoleColors.RESET);
            DFS(n, sz);
        }
    }

    void BFS() {
        boolean[] szare = new boolean[wierzcholki];
        for (int i=0; i<wierzcholki; ++i)
            if (!szare[i])
                BFS(i, szare);
    }
    private void BFS(int w, boolean[] sz) {
        LinkedList<Integer> queue = new LinkedList<>();
        sz[w]=true;
        queue.add(w);
        while (queue.size() != 0) {
            w = queue.poll();
            System.out.print(ConsoleColors.CYAN_BOLD + (w+1) +" " + ConsoleColors.RESET);
            for (int n : (Iterable<Integer>) listaS[w]) if (!sz[n]) {
                    System.out.print(ConsoleColors.YELLOW + "[" + (w+1) + "->" + (n+1) + "] " + ConsoleColors.RESET);
                    sz[n] = true;
                    queue.add(n);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("graf2.txt"));
        int liczbaW = scanner.nextInt();
        int[][] macierz = new int[liczbaW][liczbaW];
        for(int i=0; i < liczbaW; i++) for (int j=0; j < liczbaW; j++) macierz[i][j] = scanner.nextInt();
        Graf g = new Graf(liczbaW);
        for (int i=0;i<liczbaW;i++) for (int j=0;j<liczbaW;j++) if (macierz[i][j] == 1) g.dodajK(i,j);

        System.out.println("BFS:");
        g.BFS();
        System.out.println("\n\nDFS:");
        g.DFS();
    }
} 