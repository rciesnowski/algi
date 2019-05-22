import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class NajdluzszyWspolnyPodciag {
    int dlugosc(String s1, String s2) {
        char[] sX = s1.toCharArray();
        char[] sY = s2.toCharArray();
        int m = sX.length;
        int n = sY.length;
        int tablica[][] = new int[m+1][n+1];
        for (int i=0; i<=m; i++) for (int j=0; j<=n; j++) {
            if (i == 0 || j == 0) tablica[i][j] = 0; //dla krawedzi
            else if (sX[i-1] == sY[j-1]) tablica[i][j] = tablica[i-1][j-1] + 1;
            else tablica[i][j] = max(tablica[i-1][j], tablica[i][j-1]);
        }
        return tablica[m][n]; //wartosc z prawego dolnego
    }

    Set<String> wypisanie(String s1, String s2) {
        char[] sX = s1.toCharArray();
        char[] sY = s2.toCharArray();
        int m = sX.length;
        int n = sY.length;
        int tablica[][] = new int[m+1][n+1];
        for (int i=0; i<=m; i++) for (int j=0; j<=n; j++) {
            if (i == 0 || j == 0) tablica[i][j] = 0; //dla krawedzi
            else if (sX[i-1] == sY[j-1]) tablica[i][j] = tablica[i-1][j-1] + 1;
            else tablica[i][j] = max(tablica[i-1][j], tablica[i][j-1]);
        }
        return wypisanie(tablica, s1, s2, m, n);
    }

    Set<String> wypisanie(int[][] tablica, String s1, String s2, int m, int n) {
        Set<String> zestaw = new HashSet<>();
        if (m == 0 || n == 0) zestaw.add("");
        else if (s1.charAt(m - 1) == s2.charAt(n - 1))
            for (String lcs : wypisanie(tablica, s1, s2, m - 1, n - 1)) zestaw.add(lcs + s1.charAt(m - 1));
        else {
            if (tablica[m - 1][n] >= tablica[m][n - 1]) zestaw.addAll(wypisanie(tablica, s1, s2, m - 1, n));
            if (tablica[m][n - 1] >= tablica[m - 1][n]) zestaw.addAll(wypisanie(tablica, s1, s2, m, n - 1));
        }
        return zestaw;
    }

    int max(int a, int b) {
        return (a > b)? a : b;
    }

    public static void main(String[] args) {
        NajdluzszyWspolnyPodciag przyklad = new NajdluzszyWspolnyPodciag();
        Scanner scan = new Scanner(System.in);
        System.out.println("podaj dwa ciągi znaków:");
        String s1 = scan.next();
        String s2 = scan.next();

        System.out.println("najdluzszy wspolny podciag ma dlugosc " + przyklad.dlugosc(s1, s2));
        System.out.println("zestaw najdłuższych współnych podciągów: " + przyklad.wypisanie(s1, s2));
    }
}