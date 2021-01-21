from itertools import chain, combinations
import math
import time


# import seaborn as sns
# import pandas as pd
# import matplotlib.pyplot as plt
# import statsmodels.api as sm
# from statsmodels.formula.api import ols

# initial_set = [0, 1, 8, 27, 64, 125]

def calculate_cube_roots(initial_set):
    to_ret = []
    for i in initial_set:
        floor = math.floor(i**(1/3))
        if floor ** 3 == i:
            to_ret.append(floor)
        elif (floor + 1)**3 == i:
            to_ret.append(floor+1)
        else:
            to_ret.append(i)
    return to_ret


def create_subsets(initial_set):
    return list(chain.from_iterable(combinations(initial_set, x) for x in range(2, len(initial_set)+1)))
 
class Subset:
    xvalues = []
    yvalues = []
    result = 0
    error = 0
    time = 0

    def __init__(self, xvalues, yvalues):
        self.xvalues = xvalues
        self.yvalues = yvalues


    def lagrange(self, x):
        xvalues = self.xvalues
        yvalues = self.yvalues
        factors = []
        for count, i in enumerate(xvalues):
            subresults = []
            for j in xvalues:
                if i != j:
                    subresults.append(((x-j)/(i-j)))
            factor = 1
            for l in subresults:
                factor *= l
            factors.append(factor*yvalues[count])
        return sum(factors)
    
    def perform_algorithm(self, x):
        start = time.time()
        self.result = self.lagrange(x)
        end = time.time()
        self.error = self.result - szukane**(1/3)
        self.time = end - start

initial_set = []
filepath = input("Podaj ścieżkę pliku w którym trzymasz dane w postaci:\n\tx_1 y_1\n\tx_2 y_2\n\t...\n\tx_n y_n\n")
d = {}
with open(filepath) as f:
    for line in f:
       (key, val) = line.split()
       initial_set.append(int(key))
       d[int(key)] = float(val)

szukane = float(input("Podaj liczbę z której chcesz otrzymać interpolację pierwiastka sześciennego. Może być to int lub float.\n"))

subsets = create_subsets(initial_set)
objects = []
for i in subsets:
    yvalues = []
    for key in i:
        yvalues.append(d[key])
    objects.append(Subset(i, yvalues))
for i in objects:
    i.perform_algorithm(szukane)

precision = 4
row_format ="{:>25}" * (3)


answer = objects[1]

for i in objects:
    if abs(i.error) < abs(answer.error):
        answer = i

print(f"\nNajlepszy wynik uzyskaliśmy na pozdbioru: {str(answer.xvalues)}. Wynik {answer.result:.{precision}f} zawiera błąd {answer.error:.{precision}f}")
print("\nReszta wyników zawarta jest w tabeli:")

print(row_format.format("Podzbiór", "Wynik interpolacji", "Błąd"))
for i in objects:
    print(row_format.format(
        str(i.xvalues),
        f"{i.result:.{precision}f}",
        f"{i.error:.{precision}f}",
        ))


def abs(num):
    if num<0:
        return -num
    else:
        return num



# Roma plots
# error = []
# result = []
# time = []
# df = pd.DataFrame()
# for i in objects:
#     result.append(i.result)
#     error.append(i.error)
#     time.append(i.time)
#
# df.insert(0,'Result',result)
# df.insert(1,'Error', error)
# df.insert(2,'Time', time)



# # fit simple linear regression model
# model = ols('Result ~ Error', data=df).fit()
#
# #view model summary
# print(model.summary())
# # define figure size
# fig = plt.figure(figsize=(12,8))
#
# # produce regression plots
#
# fig = sm.graphics.plot_regress_exog(model, 'Error', fig=fig)
# plt.show()
#
#
# sns.regplot(x="Result", y="Error", data=df)
#
# sns.lmplot(x="Result", y="Error", data=df,
#            logistic=True, y_jitter=.03)
#
# plt.show()
#
# sns.residplot(x="Result", y="Error", data=df,
#               scatter_kws={"s": 80})
# plt.show()
#
# sns.jointplot(x="Result", y="Error", data=df, kind="reg");
#
# plt.show()