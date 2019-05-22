package com.company;
import java.util.Scanner;

public class RedBlack {
    private final int RED = 0;
    private final int BLACK = 1;
    private int glebokoscMaks = 0;
    private int wysokosc = 0;
    private int wysokoscBLACK = 0;
    private int licznik = 0;
    private class Wezel {
        int klucz = -1, kolorek = BLACK;
        Wezel lewySyn = nil, prawySyn = nil, ojciec = nil;
        Wezel(int klucz) {
            this.klucz = klucz;
        }
    }
    private final Wezel nil = new Wezel(-1);
    private Wezel korzen = nil;
    public void drukowanko(Wezel w) {
        if (w == nil) return;
        drukowanko(w.lewySyn);
        System.out.print(((w.kolorek ==RED)?"węzeł czerwony ":"węzeł czarny ")+"wartość: "+w.klucz +" wartość ojca: "+w.ojciec.klucz +"\n");
        drukowanko(w.prawySyn);
    }
    public void glebokoscMaks() {
        glebokoscMaks(korzen, 1);
        System.out.println("głębokość maksymalna: " + glebokoscMaks);
    }
    public void glebokoscMaks(Wezel w, int i){
        if(w.prawySyn != null) glebokoscMaks(w.prawySyn, i+1);
        if(w.lewySyn != null) glebokoscMaks(w.lewySyn, i+1);
        if(i > this.glebokoscMaks) this.glebokoscMaks = i;
        return;
    }
    public void wysokosc(Wezel w, int i){
        wysokosc = i;
        if(w.ojciec.klucz != -1) wysokosc(w.ojciec, i+1);
    }
    public void wysokoscBLACK(Wezel w, int i){
        wysokoscBLACK = i;
        if(w.ojciec.klucz != -1) {
            if (w.kolorek==BLACK) wysokoscBLACK(w.ojciec, i+1);
            else wysokoscBLACK(w.ojciec, i);
        }
    }
    private int policzRED(Wezel w) {
        if (w == nil) return licznik;
        policzRED(w.lewySyn);
        if (w.kolorek == RED) licznik++;
        policzRED(w.prawySyn);
        return licznik;
    }

    private Wezel znajdz(Wezel znajdzWezel, Wezel wWezle) {
        if (korzen == nil) return null;
        if (znajdzWezel.klucz < wWezle.klucz) {
            if (wWezle.lewySyn != nil) {
                return znajdz(znajdzWezel, wWezle.lewySyn);
            }
        }
        else if (znajdzWezel.klucz > wWezle.klucz) {
            if (wWezle.prawySyn != nil) {
                return znajdz(znajdzWezel, wWezle.prawySyn);
            }
        }
        else
            return wWezle;
        return null;
    }

    private void znajdzPro(Wezel z) {
        if((z = znajdz(z, korzen))==null) System.out.println("nie ma");
        wysokosc(z, 0);
        wysokoscBLACK(z, 0);
        System.out.print(((z.kolorek ==RED)?"czerwony ":"czarny ")+"wysokosc: " + wysokosc + " wysokoscBLACK: " + wysokoscBLACK + "\n");
    }

    private void wstaw(Wezel wezel) {
        Wezel temp = korzen;
        if (korzen == nil) {
            korzen = wezel;
            wezel.kolorek = BLACK;
            wezel.ojciec = nil;
        } else {
            wezel.kolorek = RED;
            while (true) {
                if (wezel.klucz < temp.klucz) {
                    if (temp.lewySyn == nil) {
                        temp.lewySyn = wezel;
                        wezel.ojciec = temp;
                        break;
                    } else {
                        temp = temp.lewySyn;
                    }
                } else if (wezel.klucz >= temp.klucz) {
                    if (temp.prawySyn == nil) {
                        temp.prawySyn = wezel;
                        wezel.ojciec = temp;
                        break;
                    }
                    else {
                        temp = temp.prawySyn;
                    }
                }
            }
            napraw(wezel);
        }
    }
    private void napraw(Wezel wezel) {
        while (wezel.ojciec.kolorek == RED) {
            Wezel uncle = nil;
            if (wezel.ojciec == wezel.ojciec.ojciec.lewySyn) {
                uncle = wezel.ojciec.ojciec.prawySyn;

                if (uncle != nil && uncle.kolorek == RED) {
                    wezel.ojciec.kolorek = BLACK;
                    uncle.kolorek = BLACK;
                    wezel.ojciec.ojciec.kolorek = RED;
                    wezel = wezel.ojciec.ojciec;
                    continue;
                }
                if (wezel == wezel.ojciec.prawySyn) {
                    //Double rotation needed
                    wezel = wezel.ojciec;
                    rotacjaLewo(wezel);
                }
                wezel.ojciec.kolorek = BLACK;
                wezel.ojciec.ojciec.kolorek = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotacjaPrawo(wezel.ojciec.ojciec);
            } else {
                uncle = wezel.ojciec.ojciec.lewySyn;
                if (uncle != nil && uncle.kolorek == RED) {
                    wezel.ojciec.kolorek = BLACK;
                    uncle.kolorek = BLACK;
                    wezel.ojciec.ojciec.kolorek = RED;
                    wezel = wezel.ojciec.ojciec;
                    continue;
                }
                if (wezel == wezel.ojciec.lewySyn) {
                    //Double rotation needed
                    wezel = wezel.ojciec;
                    rotacjaPrawo(wezel);
                }
                wezel.ojciec.kolorek = BLACK;
                wezel.ojciec.ojciec.kolorek = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotacjaLewo(wezel.ojciec.ojciec);
            }
        }
        korzen.kolorek = BLACK;
    }

    void rotacjaLewo(Wezel wezel) {
        if (wezel.ojciec != nil) {
            if (wezel == wezel.ojciec.lewySyn) {
                wezel.ojciec.lewySyn = wezel.prawySyn;
            } else {
                wezel.ojciec.prawySyn = wezel.prawySyn;
            }
            wezel.prawySyn.ojciec = wezel.ojciec;
            wezel.ojciec = wezel.prawySyn;
            if (wezel.prawySyn.lewySyn != nil) {
                wezel.prawySyn.lewySyn.ojciec = wezel;
            }
            wezel.prawySyn = wezel.prawySyn.lewySyn;
            wezel.ojciec.lewySyn = wezel;
        } else {//Need to rotate korzen
            Wezel right = korzen.prawySyn;
            korzen.prawySyn = right.lewySyn;
            right.lewySyn.ojciec = korzen;
            korzen.ojciec = right;
            right.lewySyn = korzen;
            right.ojciec = nil;
            korzen = right;
        }
    }

    void rotacjaPrawo(Wezel wezel) {
        if (wezel.ojciec != nil) {
            if (wezel == wezel.ojciec.lewySyn) {
                wezel.ojciec.lewySyn = wezel.lewySyn;
            } else {
                wezel.ojciec.prawySyn = wezel.lewySyn;
            }

            wezel.lewySyn.ojciec = wezel.ojciec;
            wezel.ojciec = wezel.lewySyn;
            if (wezel.lewySyn.prawySyn != nil) {
                wezel.lewySyn.prawySyn.ojciec = wezel;
            }
            wezel.lewySyn = wezel.lewySyn.prawySyn;
            wezel.ojciec.prawySyn = wezel;
        } else {//Need to rotate korzen
            Wezel left = korzen.lewySyn;
            korzen.lewySyn = korzen.lewySyn.prawySyn;
            left.prawySyn.ojciec = korzen;
            korzen.ojciec = left;
            left.prawySyn = korzen;
            left.ojciec = nil;
            korzen = left;
        }
    }

    void przeszczep(Wezel target, Wezel with){
        if(target.ojciec == nil){
            korzen = with;
        }else if(target == target.ojciec.lewySyn){
            target.ojciec.lewySyn = with;
        }else
            target.ojciec.prawySyn = with;
        with.ojciec = target.ojciec;
    }

    boolean usuwanko(Wezel z){
        if((z = znajdz(z, korzen))==null)return false;
        Wezel x;
        Wezel y = z; // temporary reference y
        int y_original_color = y.kolorek;

        if(z.lewySyn == nil){
            x = z.prawySyn;
            przeszczep(z, z.prawySyn);
        }else if(z.prawySyn == nil){
            x = z.lewySyn;
            przeszczep(z, z.lewySyn);
        }else{
            y = minimum(z.prawySyn);
            y_original_color = y.kolorek;
            x = y.prawySyn;
            if(y.ojciec == z)
                x.ojciec = y;
            else{
                przeszczep(y, y.prawySyn);
                y.prawySyn = z.prawySyn;
                y.prawySyn.ojciec = y;
            }
            przeszczep(z, y);
            y.lewySyn = z.lewySyn;
            y.lewySyn.ojciec = y;
            y.kolorek = z.kolorek;
        }
        if(y_original_color==BLACK)
            naprawka(x);
        return true;
    }

    void naprawka(Wezel x){
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
            }else{
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

    Wezel minimum(Wezel s){
        while(s.lewySyn !=nil){
            s = s.lewySyn;
        }
        return s;
    }

    public void uruchom() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n[1] dodaj elementy (jako ostatni podaj -999)\n"
                    + "[2] usuń elementy\n"
                    + "[3] drukuj drzewo\n"
                    + "[4] podaj glebokość\n"
                    + "[5] policz czerwone węzły\n"
                    + "[6] znajdz element\n");
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
                        System.out.print("\nusuwanie " + elem);
                        if (usuwanko(node)) System.out.print(": usunieto!");
                        else System.out.print(": nie istnieje!");
                        elem = scan.nextInt();
                    }
                    break;
                case 3:
                    drukowanko(korzen);
                    break;
                case 4:
                    glebokoscMaks();
                    //glebokoscMin();
                    break;
                case 5:
                    System.out.println("ilość czerwonych węzłów: " + policzRED(korzen));
                    break;
                case 6:
                    elem = scan.nextInt();
                    node = new Wezel(elem);
                    znajdzPro(node);
                    break;
            }
        }
    }
    public static void main(String[] args) {
        RedBlack rbt = new RedBlack();
        rbt.uruchom();
    }
}