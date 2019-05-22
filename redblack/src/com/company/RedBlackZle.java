package com.company;
import java.util.Scanner;

public class RedBlackZle {
    private final int RED = 0;
    private final int BLACK = 1;
    private int glebokosc = 0;
    private int licznik = 0;

    private class Wezel {
        int klucz = -1, kolorek = BLACK;
        Wezel lewySyn = nil, prawySyn = nil, ojciec = nil;
        Wezel(int k) {
            this.klucz = k;
        }
    }
    private final Wezel nil = new Wezel(-1);
    private Wezel korzen = nil;

    public void drukowanko(Wezel w) {
        if (w == nil) return;
        drukowanko(w.lewySyn);
        System.out.print(((w.kolorek==RED)?"węzeł czerwony ":"węzeł czarny ")+"wartość: "+w.klucz +" wartość ojca: "+w.ojciec.klucz +"\n");
        drukowanko(w.prawySyn);
    }
    public void glebokosc() {
        glebokosc(korzen, 1);
        System.out.println("głębokość drzewa: " + glebokosc);
    }
    public void glebokosc(Wezel w, int i){
        if(w.prawySyn != null) glebokosc(w.prawySyn, i+1);
        if(w.lewySyn != null) glebokosc(w.lewySyn, i+1);
        if(i > this.glebokosc) this.glebokosc = i;
        return;
    }
    private int policzRED(Wezel w) {
        if (w == nil) return licznik;
        policzRED(w.lewySyn);
        if (w.kolorek == RED) licznik++;
        policzRED(w.prawySyn);
        return licznik;
    }

    private Wezel znajdz(Wezel z, Wezel w) {
        if (korzen == nil) return null;
        if (z.klucz < w.klucz) {
            if (w.lewySyn != nil) {
                return znajdz(z, w.lewySyn);
            }
        }
        else if (z.klucz > w.klucz) if (w.prawySyn != nil) return znajdz(z, w.prawySyn);
        else return w;
        return null;
    }
    private void wstaw(Wezel w) {
        Wezel temp = korzen;
        if (korzen == nil) {
            korzen = w;
            w.kolorek = BLACK;
            w.ojciec = nil;
        }
        else {
            w.kolorek = RED;
            while (true) {
                if (w.klucz < temp.klucz) {
                    if (temp.lewySyn == nil) {
                        temp.lewySyn = w;
                        w.ojciec = temp;
                        break;
                    }
                    else temp = temp.lewySyn;
                }
                else {
                    if (temp.prawySyn == nil) {
                        temp.prawySyn = w;
                        w.ojciec = temp;
                        break;
                    } else temp = temp.prawySyn;
                }
            }
            napraw(w);
        }
    }
    boolean usuwanko(Wezel z){
        if((z = znajdz(z, korzen))==null) return false;
        Wezel x;
        Wezel y = z; // temporary reference y
        int y_original_color = y.kolorek;
        if(z.lewySyn == nil){
            x = z.prawySyn;
            przeszczep(z, z.prawySyn);
        }
        else if(z.prawySyn == nil){
            x = z.lewySyn;
            przeszczep(z, z.lewySyn);
        }
        else {
            y = minimum(z.prawySyn);
            y_original_color = y.kolorek;
            x = y.prawySyn;
            if(y.ojciec == z) x.ojciec = y;
            else {
                przeszczep(y, y.prawySyn);
                y.prawySyn = z.prawySyn;
                y.prawySyn.ojciec = y;
            }
            przeszczep(z, y);
            y.lewySyn = z.lewySyn;
            y.lewySyn.ojciec = y;
            y.kolorek = z.kolorek;
        }
        if(y_original_color==BLACK) naprawUsuwanko(x);
        return true;
    }

    private void napraw(Wezel w) {
        while (w.ojciec.kolorek == RED) {
            Wezel wujek = nil;
            if (w.ojciec == w.ojciec.ojciec.lewySyn) {
                wujek = w.ojciec.ojciec.prawySyn;
                if (wujek != nil && wujek.kolorek == RED) {
                    w.ojciec.kolorek = BLACK;
                    wujek.kolorek = BLACK;
                    w.ojciec.ojciec.kolorek = RED;
                    w = w.ojciec.ojciec;
                    continue;
                }
                if (w == w.ojciec.prawySyn) {
                    w = w.ojciec;
                    rotacjaLewo(w);
                }
                w.ojciec.kolorek = BLACK;
                w.ojciec.ojciec.kolorek = RED;
                rotacjaPrawo(w.ojciec.ojciec);
            }
            else {
                wujek = w.ojciec.ojciec.lewySyn;
                if (wujek != nil && wujek.kolorek == RED) {
                    w.ojciec.kolorek = BLACK;
                    wujek.kolorek = BLACK;
                    w.ojciec.ojciec.kolorek = RED;
                    w = w.ojciec.ojciec;
                    continue;
                }
                if (w == w.ojciec.lewySyn) {
                    w = w.ojciec;
                    rotacjaPrawo(w);
                }
                w.ojciec.kolorek = BLACK;
                w.ojciec.ojciec.kolorek = RED;
                rotacjaLewo(w.ojciec.ojciec);
            }
        }
        korzen.kolorek = BLACK;
    }
    void rotacjaLewo(Wezel w) {
        if (w.ojciec != nil) {
            if (w == w.ojciec.lewySyn) {
                w.ojciec.lewySyn = w.prawySyn;
            }
            else w.ojciec.prawySyn = w.prawySyn;
            w.prawySyn.ojciec = w.ojciec;
            w.ojciec = w.prawySyn;
            if (w.prawySyn.lewySyn != nil) w.prawySyn.lewySyn.ojciec = w;
            w.prawySyn = w.prawySyn.lewySyn;
            w.ojciec.lewySyn = w;
        }
        else {
            Wezel prawy = korzen.prawySyn;
            korzen.prawySyn = prawy.lewySyn;
            prawy.lewySyn.ojciec = korzen;
            korzen.ojciec = prawy;
            prawy.lewySyn = korzen;
            prawy.ojciec = nil;
            korzen = prawy;
        }
    }
    void rotacjaPrawo(Wezel w) {
        if (w.ojciec != nil) {
            if (w == w.ojciec.lewySyn) w.ojciec.lewySyn = w.lewySyn;
            else w.ojciec.prawySyn = w.lewySyn;
            w.lewySyn.ojciec = w.ojciec;
            w.ojciec = w.lewySyn;
            if (w.lewySyn.prawySyn != nil) w.lewySyn.prawySyn.ojciec = w;
            w.lewySyn = w.lewySyn.prawySyn;
            w.ojciec.prawySyn = w;
        }
        else {//Need to rotate korzen
            Wezel lewy = korzen.lewySyn;
            korzen.lewySyn = korzen.lewySyn.prawySyn;
            lewy.prawySyn.ojciec = korzen;
            korzen.ojciec = lewy;
            lewy.prawySyn = korzen;
            lewy.ojciec = nil;
            korzen = lewy;
        }
    }
    void przeszczep(Wezel cel, Wezel z){
        if(cel.ojciec == nil) korzen = z;
        else if(cel == cel.ojciec.lewySyn) cel.ojciec.lewySyn = z;
        else cel.ojciec.prawySyn = z;
        z.ojciec = cel.ojciec;
    }
    void naprawUsuwanko(Wezel x){
        while(x!= korzen && x.kolorek == BLACK){
            if(x == x.ojciec.lewySyn){
                Wezel w = x.ojciec.prawySyn;
                if(w.kolorek == RED){
                    w.kolorek = BLACK;
                    x.ojciec.kolorek = RED;
                    rotacjaLewo(x.ojciec);
                    w = x.ojciec.prawySyn;
                }
                if(w.lewySyn.kolorek == BLACK && w.prawySyn.kolorek == BLACK){
                    w.kolorek = RED;
                    x = x.ojciec;
                    continue;
                }
                else if(w.prawySyn.kolorek == BLACK){
                    w.lewySyn.kolorek = BLACK;
                    w.kolorek = RED;
                    rotacjaPrawo(w);
                    w = x.ojciec.prawySyn;
                }
                if(w.prawySyn.kolorek == RED){
                    w.kolorek = x.ojciec.kolorek;
                    x.ojciec.kolorek = BLACK;
                    w.prawySyn.kolorek = BLACK;
                    rotacjaLewo(x.ojciec);
                    x = korzen;
                }
            }
            else{
                Wezel w = x.ojciec.lewySyn;
                if(w.kolorek == RED){
                    w.kolorek = BLACK;
                    x.ojciec.kolorek = RED;
                    rotacjaPrawo(x.ojciec);
                    w = x.ojciec.lewySyn;
                }
                if(w.prawySyn.kolorek == BLACK && w.lewySyn.kolorek == BLACK){
                    w.kolorek = RED;
                    x = x.ojciec;
                    continue;
                }
                else if(w.lewySyn.kolorek == BLACK){
                    w.prawySyn.kolorek = BLACK;
                    w.kolorek = RED;
                    rotacjaLewo(w);
                    w = x.ojciec.lewySyn;
                }
                if(w.lewySyn.kolorek == RED){
                    w.kolorek = x.ojciec.kolorek;
                    x.ojciec.kolorek = BLACK;
                    w.lewySyn.kolorek = BLACK;
                    rotacjaPrawo(x.ojciec);
                    x = korzen;
                }
            }
        }
        x.kolorek = BLACK;
    }

    Wezel minimum(Wezel w){
        while(w.lewySyn !=nil) w = w.lewySyn;
        return w;
    }

    public void uruchom() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n[1] dodaj elementy (jako ostatni podaj -999)\n"
                    + "[2] usuń elementy\n"
                    + "[3] drukuj drzewo\n"
                    + "[4] podaj glebokość\n"
                    + "[5] policz czerwone węzły\n");
            int wybor = scan.nextInt();
            int elem;
            Wezel node;
            switch (wybor) {
                case 1:
                    elem = scan.nextInt();
                    while (elem != -999) {
                        node = new Wezel(elem);
                        wstaw(node);
                        elem = scan.nextInt();
                    }
                    break;
                case 2:
                    elem = scan.nextInt();
                    while (elem != -999) {
                        node = new Wezel(elem);
                        System.out.print("\nDeleting item " + elem);
                        if (usuwanko(node)) System.out.print(": deleted!");
                        else System.out.print(": does not exist!");
                        elem = scan.nextInt();
                    }
                    break;
                case 3:
                    drukowanko(korzen);
                    break;
                case 4:
                    glebokosc();
                    break;
                case 5:
                    System.out.println("ilość czerwonych węzłów: " + policzRED(korzen));
                    break;
            }
        }
    }
    public static void main(String[] args) {
        RedBlackZle drzewk = new RedBlackZle();
        drzewk.uruchom();
    }
}