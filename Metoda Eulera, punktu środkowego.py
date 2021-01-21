y_1 = 0
ograniczenie_dolne = 1


def zagadnienie(x, y):
    return (3 - 2 * (1 + 3 * x - y)**(1 / 2))


#    return (4 - 2*((1 + 3*x - y)**(1/2))


def funkcja(x):
    return float(-(x**2) + x)
    #return float(-x**2 + x)


def daj_wezly(x, ograniczenie_górne, h):
    to_ret = [x]
    while x + h < ograniczenie_górne:
        x += h
        to_ret.append(x)
    return to_ret


def daj_bledy(wartosci_x, wartosci_y):
    to_ret = [0]
    for x, y in zip(wartosci_x[1:], wartosci_y[1:]):
        to_ret.append(abs(y - funkcja(x)))

    return to_ret


def euler(wartosci_x, wartosci_y, h):
    for indeks, x in enumerate(wartosci_x[:-1]):
        wartosci_y.append(wartosci_y[indeks] +
                          h * zagadnienie(x, wartosci_y[indeks]))
    return wartosci_y


def zmodyfikowany_euler(wartosci_x, wartosci_y, h):
    for indeks, x in enumerate(wartosci_x[:-1]):
        wartosci_y.append(wartosci_y[indeks] + h * zagadnienie(
            x + h / 2, wartosci_y[indeks] +
            h / 2 * zagadnienie(x, wartosci_y[indeks])))
    return wartosci_y


N = int(input("podaj liczbę N: "))
while N < 1 | (N % 1) != 0:
    N = int(
        input(
            "N musi być liczbą naturalną większą od zera.\nspróbuj ponownie: ")
    )
ograniczenie_górne = float(input("podaj ograniczenie górne: "))
while ograniczenie_górne <= 1:
    ograniczenie_górne = float(
        input(
            "ograniczenie górne musi być liczbą większą od 1.\nspróbuj ponownie: "
        ))
h = (ograniczenie_górne - ograniczenie_dolne) / N

wartosci_x = daj_wezly(ograniczenie_dolne, ograniczenie_górne, h)

print(
    "\n*****  ***  *****  ***  *****  ***  *****  ***  *****  ***  *****  ***\n"
)
wartosci_y = [y_1]
wartosci_y = euler(wartosci_x, wartosci_y, h)
bledy = daj_bledy(wartosci_x, wartosci_y)
wartosci_yZ = [y_1]
wartosci_yZ = zmodyfikowany_euler(wartosci_x, wartosci_yZ, h)
bledyZ = daj_bledy(wartosci_x, wartosci_yZ)

print("i\t| węzły x_i\t| y(x_i)\t| y_i Euler\t| błąd  \t| y_i Zmod\t| błąd ")
for i in range(len(wartosci_x)):
    print(i, '\t| %.4f' % wartosci_x[i], "\t| ",
          '%.4f' % funkcja(wartosci_x[i]), "\t| ", '%.4f' % wartosci_y[i],
          "\t| ", '%.4f' % bledy[i], "\t| ", '%.4f' % wartosci_yZ[i], "\t| ",
          '%.4f' % bledyZ[i])
print(
    "\n*****  ***  *****  ***  *****  ***  *****  ***  *****  ***  *****  ***")
print("\nbłąd maksymalny metody Eulera: \t\t\t", max(bledy),
      "\nbłąd maksymalny metody zmodyfikowanej: \t", max(bledyZ))

#print("\nMETODA EULERA")
#print("\nWartości węzłów y:")
#for i, y in enumerate(wartosci_y):
#    print(f"y{i}: ", y)

#print("\nWartości węzłów y(xn):")
#for i, x in enumerate(wartosci_x):
#    print(f"y(x{i}): ", funkcja3(x))

#print("\nBłędy metody:")
#for i, blad in enumerate(bledy):
#    print(f"dla k = {i+1}: ", blad)
#print("\nBłąd maksymalny: ", max(bledy))

#print("-----------------------")
#print("ZMODYFIKOWANA METODA EULERA")
#print("\nWartości węzłów y:")
#for i, y in enumerate(wartosci_yZ):
#    print(f"y{i}: ", y)

#print("\nWartości węzłów y(xn):")
#for i, x in enumerate(wartosci_x):
#    print(f"y(x{i}): ", funkcja3(x))

#print("\nBłędy metody:")
#for i, blad in enumerate(bledy):
#    print(f"dla k = {i+1}: ", blad)
#print("\nBłąd maksymalny: ", max(bledy))
