public class Zbior {
    private int[] ojciec, ranga, all;

    public Zbior(int rozmiar) {
        ojciec = new int[rozmiar];
        ranga = new int[rozmiar];
        all = new int[rozmiar];
        for (int i = 0; i < rozmiar; i++) {
            all[i] = i;
            ojciec[i] = i;
        }
    }

    public int find(int i) {
        int o = ojciec[i];
        if (i == o) return i;
        return ojciec[i] = find(o);
    }

    public void union(int i, int j) {
        System.out.println("union(" + i + ", " + j + ")");
        int ki = find(i), kj = find(j);
        if (kj == ki) {
            System.out.println("zero zmian\n");
            return;
        }
        if (ranga[ki] > ranga[kj]) ojciec[kj] = ki;
        else if (ranga[kj] > ranga[ki]) ojciec[ki] = kj;
        else {
            ojciec[kj] = ki;
            ranga[ki]++;
        }
        System.out.println(this);
    }

    public String toString() {
        String r = "klucze:\t| ";
        for (int i : all) r += i + " | ";
        r += "\nojciec:\t| ";
        for (int i : ojciec) r += i + " | ";
        r += "\nranga:\t| ";
        for (int i : ranga) r += i + " | ";
        return r + "\n";
    }

    public static void main(String[] args) {
        Zbior z = new Zbior(7);
        System.out.println(z);

        z.union(1,2);
        z.union(1,2);
        z.union(3,4);
        z.union(1,0);
        z.union(6,4);
        z.union(1,3);
        z.union(5, 6);
        z.union(0, 6);

        z.find(4);
        System.out.println("find(4)\n" + z);
    }
}