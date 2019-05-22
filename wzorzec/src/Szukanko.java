import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

class Szukanko {
    void naiwn(String pat, String txt) {
        System.out.println("\nALGORYTM NAIWNY");
        int M = pat.length();
        int N = txt.length();
        int z = 0;
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) if (txt.charAt(i + j) != pat.charAt(j) && txt.charAt(i + j) != '?') break;
            if (j == M) {
                System.out.print(i + " ");
                z++;
            }
        }
        System.out.println("\n" + z + " dopasowań\n");
    }

    void KMP(String pat, String txt) {
        System.out.println("ALGORYTM KMP");
        int M = pat.length();
        int N = txt.length();
        int[] presufiks = new int[M];
        int j = 0;
        dlugosc(pat, M, presufiks);
        int z = 0;
        int i = 0;
        while (i < N) {
            if ((pat.charAt(j) == txt.charAt(i)) | (txt.charAt(j) == '?')){
                j++;
                i++;
            }
            if (j == M) {
                System.out.print((i - j) + " ");
                j = presufiks[j - 1];
                z++;
            }
            //przeskok
            else if (i < N && pat.charAt(j) != txt.charAt(i) && txt.charAt(i) != '?') {
                if (j != 0) j = presufiks[j - 1];
                else i++;
            }
        }
        System.out.println("\n" + z + " dopasowań\n");
    }

    public final static int d = 256;
    void rabinKarp(String pat, String txt) {
        System.out.println("ALGORYTM RABINA-KARPA");
        int q = 101;
            int M = pat.length();
            int N = txt.length();
            int i, j, z = 0;
            int haszPat = 0;
            int haszTxt = 0;
            int h = 1;
            for (i = 0; i < M - 1; i++) h = (h * d) % q;
            for (i = 0; i < M; i++) {
                haszPat = (d * haszPat + pat.charAt(i)) % q;
                haszTxt = (d * haszTxt + txt.charAt(i)) % q;
            }
            for (i = 0; i <= N - M; i++) {
                if (haszPat == haszTxt) {
                    for (j = 0; j < M; j++) if (txt.charAt(i + j) != pat.charAt(j) && txt.charAt(i + j) != '?') break;
                    if (j == M) {
                        System.out.print(i + " ");
                        z++;
                    }
                }
                if (i < N - M) {
                    haszTxt = (d * (haszTxt - txt.charAt(i) * h) + txt.charAt(i + M)) % q;
                    if (haszTxt < 0) haszTxt = (haszTxt + q);
                }
            }
            System.out.println("\n" + z + " dopasowań\n");
        }

    private void dlugosc(String pat, int M, int[] presufiks) {
        int len = 0;
        int i = 1;
        presufiks[0] = 0;
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                presufiks[i] = len;
                i++;
            }
            else {
                if (len != 0) len = presufiks[len - 1];
                else {
                    presufiks[i] = len;
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in); // standardowe wejście
        System.out.print("podaj wzorzec: ");
        String pat = scanner.nextLine();
        System.out.print("podaj tekst: ");
        String txt = scanner.nextLine();
        scanner.close();

        String pat1 = new String(Files.readAllBytes(Paths.get(pat)));
        String txt1 = new String(Files.readAllBytes(Paths.get(txt)));
        pat1 = pat1.replaceAll("\\r|\\n", "");
        txt1 = txt1.replaceAll("\\r|\\n", "");

        long start1 = System.nanoTime();
        new Szukanko().naiwn(pat1, txt1);
        long stop1 = System.nanoTime();

        long start2 = System.nanoTime();
        new Szukanko().KMP(pat1, txt1);
        long stop2 = System.nanoTime();

        long start3 = System.nanoTime();
        new Szukanko().rabinKarp(pat1, txt1);
        long stop3 = System.nanoTime();

        System.out.println("czas naiwny:\t\t" + (stop1 - start1));
        System.out.println("czas KMP:\t\t\t" + (stop2 - start2));
        System.out.println("czas RabinKarpa:\t" + (stop3 - start3));
    }
} 