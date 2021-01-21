from sympy import *

x, y, fx, fx_, x_p, x_n = symbols('x y fx fx_ x_p, x_n')

#============= Stycznych ============ 
#Function for stycznie
fx =(E**(2*x)) -5*x -4

#Example function
# fx= x**3 + x + 1

#============= Prostych ============ 
x_n= (E**(2*x)-4)/5  # Pierwiastek negatywny
x_p= 0.5*ln(5*x+4)   # Pierwiastek dodatni


# This take 'doklandosc'. It's uses in checkError()
while True:
  try:
    val = float(input("Podaj dokładność ε: ")) 
    if val > 1 or val < 0:
      print("Niepoprawne ε")
    else:
      print("ε = ", val)
      break
  except ValueError:
    print("Błąd, spróbuj ponownie");    
  

while True:
  try:
    x_0 = float(input("Podaj x_0: ")) 
    print("x_0 = ", x_0)
    break
  except ValueError:
    print("Błąd, spróbuj ponownie"); 


list_stycznie = []
list_stycznie.append(x_0)
list_proste = []
list_proste.append(x_0)
list_proste_n = []
list_proste_n.append(x_0)


def checkError(x_i, x_0):
    error = Abs(x_i - x_0)
    print("| Błąd: ", error.n(30))
    print("==============================================================")

    if(error <= val):
      return True
    else:
      return False 


def derivative(fx):
    fx_=diff(fx , x)
    print("| Pochodna: ", fx_)
    return fx_


def next_X(x_0):
    x_i = x_0 - (fx.subs({x:x_0})/fx_.evalf(subs={x:x_0}))
    # print("Fx= " , fx.subs({x:x0}))
    print("| Następne x(i): ", x_i.n(30))
    list_stycznie.append(x_i.n(30))
    return x_i


print("\n==============================================================")
fx_ = derivative(fx)
print("==============================================================")



def runProg():
    i=1
    while True:
      print("| Krok: ", i)
      x_0=list_stycznie.pop()
      x_i = next_X(x_0)
      i+=1
      if(checkError(x_i,x_0)==True):
        return i-1  

#=================Iteracja prostych Positive =================
def iteracjaProstychPositive(x_1):
    x_i =x_p.subs({x:x_1})
    print("| Następne x(i): ", x_i.n((30)))
    list_proste.append(x_i.n(30))
    return x_i


def runIteracjaProstychPositive():
    i=1
    while True:
      print("| Krok: ", i)
      x_0=list_proste.pop()
      x_i = iteracjaProstychPositive(x_0)
      i+=1
      if(checkError(x_i,x_0)==True):
        return i-1 


#=================Iteracja prostych Negative =================
def iteracjaProstychNegative(x_1):
    x_i =x_n.subs({x:x_1})
    print("| Następne x(i): ", x_i.n((30)))
    list_proste_n.append(x_i.n(30))
    return x_i


def runIteracjaProstychNegative():
    i=1
    while True:
      print("| Krok: ", i)
      x_0=list_proste_n.pop()
      x_i = iteracjaProstychNegative(x_0)
      i+=1
      if(checkError(x_i,x_0)==True):
        list_proste_n.clear()
        list_proste_n.append(x_i)
        return i-1 


print("\n==============================================================")
print("| Metoda stycznych")
print("==============================================================")
iStycznych = runProg()
print("\n==============================================================")
print("| Iteracje proste - pierwiastek dodatni")
print("==============================================================")
iProste = runIteracjaProstychPositive()
print("\n==============================================================")
print("| Iteracje proste - pierwiastek ujemny")
print("==============================================================")
iProste_n = runIteracjaProstychNegative()


ansr_n = list_proste_n.pop().n(30)
ansr_styczn = list_stycznie.pop().n(30)
ansr_d = list_proste.pop().n(30)
print("\n| Podsumowanie:")
print("| Metoda iteracji stycznych:")
print("| Liczba kroków: ", iStycznych, "\t| x ≈ ", ansr_styczn)
print("| Iteracje proste - pierwiastek dodatni: ")
print("| Liczba kroków: ", iProste, "\t| x ≈ ", ansr_d)
print("| Iteracje proste - pierwiastek ujemny: ")
print("| Liczba kroków: ", iProste_n, "\t| x ≈ ", ansr_n)
print("==============================================================")