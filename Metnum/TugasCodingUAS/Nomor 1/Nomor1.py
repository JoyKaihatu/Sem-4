import numpy as np
import matplotlib.pyplot as plt

def f(x):
    return x**3 - 6*x**2 + 11*x - 6.1

# Metode Graphical
def graphical_method():
    # Buat rentang arraynya
    x = np.linspace(-10, 10, 400)
    y = f(x) # lalu masukin hasil array yang ada

    # mulai gambar grafiknya
    plt.plot(x, y)
    plt.axhline(0, color='black', linewidth=0.5)
    plt.xlabel('x')
    plt.ylabel('f(x)')
    plt.title('Graphical Method')
    plt.grid(True)
    plt.show()

# Metode Bisection
def bisection_method(a, b):
    max_iter = 100
    tolerance = 0.0005

    # cek apakah interval dapat diaplikasikan
    if f(a) * f(b) >= 0:
        print("Metode bisection tidak dapat diaplikasikan pada interval ini.")
        return None

    # mulai melakukan iterasi
    iterasi = 1
    while iterasi <= max_iter:
        c = (a + b) / 2

        if abs(f(c)) < tolerance:
            return c
        
        if f(c) * f(a) < 0:
            b = c
        else:
            a = c

        iterasi += 1

    print("Metode bisection tidak konvergen setelah", max_iter, "iterasi.")
    return None


# Metode False Position
def false_position_method(a, b):
    max_iter = 100
    tolerance = 0.0005

    # Cek false positionnya
    if f(a) * f(b) >= 0:
        print("Metode false position tidak dapat diaplikasikan pada interval ini.")
        return None

    # buat iterasi selama iterasi <= max_iter
    iterasi = 1
    while iterasi <= max_iter:
        # cek dengan rumus
        c = (a * f(b) - b * f(a)) / (f(b) - f(a))

        if abs(f(c)) < tolerance:
            return c
        
        if f(c) * f(a) < 0:
            b = c
        else:
            a = c
            
        iterasi += 1

    print("Metode false position tidak konvergen setelah", max_iter, "iterasi.")
    return None

# Metode Simple Fixed-Point Iteration
def fixed_point_iteration_method(g, x0):
    max_iter = 100
    tolerance = 0.0005

    iterasi = 1
    while iterasi <= max_iter:
        x1 = g(x0)
        if abs(x1 - x0) < tolerance:
            return x1
        x0 = x1
        iterasi += 1

    print("Metode simple fixed-point iteration tidak konvergen setelah", max_iter, "iterasi.")
    return None

# Metode Newton-Raphson
def newton_raphson_method(f, f_prime, x0):
    max_iter = 100
    tolerance = 0.0005

    iterasi = 1
    while iterasi <= max_iter:
        x1 = x0 - f(x0) / f_prime(x0)
        if abs(x1 - x0) < tolerance:
            return x1
        x0 = x1
        iterasi += 1

    print("Metode Newton-Raphson tidak konvergen setelah", max_iter, "iterasi.")
    return None

# Metode Modified Secant
def modified_secant_method(f, x0, p):
    max_iter = 100
    tolerance = 0.0005

    iterasi = 1
    while iterasi <= max_iter:
        x1 = x0 - p * f(x0) / (f(x0 + p * x0) - f(x0))
        if abs(x1 - x0) < tolerance:
            return x1
        x0 = x1
        iterasi += 1

    print("Metode modified secant tidak konvergen setelah", max_iter, "iterasi.")
    return None

# Metode Graphical
graphical_method()

# Menentukan initial guess untuk metode lainnya
initial_guess = 2.5

# Metode Bisection
a, b = 2, 3
root_bisection = bisection_method(a, b)
print("Metode Bisection: x =", root_bisection)

# Metode False Position
root_false_position = false_position_method(a, b)
print("Metode False Position: x =", root_false_position)

# Metode Simple Fixed-Point Iteration
g = lambda x: (x**3 - 6*x**2 + 6.1) / 11
root_fixed_point_iteration = fixed_point_iteration_method(g, initial_guess)
print("Metode Simple Fixed-Point Iteration: x =", root_fixed_point_iteration)

# Metode Newton-Raphson
f_prime = lambda x: 3*x**2 - 12*x + 11
root_newton_raphson = newton_raphson_method(f, f_prime, initial_guess)
print("Metode Newton-Raphson: x =", root_newton_raphson)

# Metode Modified Secant
p = 0.01
root_modified_secant = modified_secant_method(f, initial_guess, p)
print("Metode Modified Secant: x =", root_modified_secant)
