# -*- coding: utf-8 -*-
"""Untitled4.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1weOZoShRfAAjWtNxhRxcemYpm5T1DNy5
"""
#No 3A1
import matplotlib.pyplot as plt


# Persamaan 1: (x - 4)^2 + (y - 4)^2 = 5
def equation1(x):
    return ((5 - (x - 4)**2)**0.5) + 4, -((5 - (x - 4)**2)**0.5) + 4


# Persamaan 2: x^2 + y^2 = 16
def equation2(x):
    return ((16 - x**2)**0.5), -((16 - x**2)**0.5)


# Plot persamaan-persamaan
x = [i/10 for i in range(-100, 180)]  # Menghasilkan nilai x dari -10 hingga 17 dengan increment 0.1
y1_pos = []
y1_neg = []
y2_pos = []
y2_neg = []


for i in range(len(x)):
    y1_pos_val, y1_neg_val = equation1(x[i])
    y1_pos.append(y1_pos_val)
    y1_neg.append(y1_neg_val)
    y2_pos_val, y2_neg_val = equation2(x[i])
    y2_pos.append(y2_pos_val)
    y2_neg.append(y2_neg_val)


plt.plot(x, y1_pos, 'b', label='Equation 1')
plt.plot(x, y1_neg, 'b')
plt.plot(x, y2_pos, 'r', label='Equation 2')
plt.plot(x, y2_neg, 'r')


# Titik perkiraan awal
initial_guess_x = 2.5
initial_guess_y = 2.5
plt.scatter(initial_guess_x, initial_guess_y, color='green', label='Initial Guess')


plt.xlabel('x')
plt.ylabel('y')
plt.title('Graphical Approach - Initial Guess')
plt.legend()
plt.grid(True)
plt.axis('equal')
plt.show()

#No 3A2
# Fungsi persamaan
def equation1(x, y):
    return (x - 4)**2 + (y - 4)**2 - 5

def equation2(x, y):
    return x**2 + y**2 - 16

# Metode Gaus-Seidel
def gauss_seidel():
    x = 1.0  # Perkiraan awal x
    y = 1.0  # Perkiraan awal y
    error = 1.0  # Kesalahan awal

    while error > 1e-4:
        x_new = (4 + y - (5 - (x - 4)**2)**0.5)**0.5  # Perhitungan x baru
        y_new = (16 - x**2)**0.5  # Perhitungan y baru

        error = max(abs(x_new - x), abs(y_new - y))  # Menghitung kesalahan
        x = x_new
        y = y_new

    return x, y

# Solusi
solution_x, solution_y = gauss_seidel()
print("Solusi:")
print("x =", solution_x)
print("y =", solution_y)

#No 3A3
# Fungsi persamaan
def equation1(x, y):
    return (x - 4)**2 + (y - 4)**2 - 5

def equation2(x, y):
    return x**2 + y**2 - 16

# Metode Gaus-Seidel dengan relaksasi
def gauss_seidel_relaxation(relaxation):
    x = 1.0  # Perkiraan awal x
    y = 1.0  # Perkiraan awal y
    error = 1.0  # Kesalahan awal

    while error > 1e-4:
        x_new = relaxation * ((4 + y - (5 - (x - 4)**2)**0.5)**0.5) + (1 - relaxation) * x  # Perhitungan x baru dengan relaksasi
        y_new = relaxation * (16 - x**2)**0.5 + (1 - relaxation) * y  # Perhitungan y baru dengan relaksasi

        error = max(abs(x_new - x), abs(y_new - y))  # Menghitung kesalahan
        x = x_new
        y = y_new

    return x, y

# Relaksasi yang ditentukan
relaxation_factor = 0.5

# Solusi
solution_x, solution_y = gauss_seidel_relaxation(relaxation_factor)
print("Solusi:")
print("x =", solution_x)
print("y =", solution_y)

#No 3A4
# Fungsi persamaan
def equation1(x, y):
    return -x**2 + x + 0.75 - y

def equation2(x, y):
    return y + 5*x*y - x**2

# Turunan parsial
def partial_derivative_x(f, x, y, h=1e-6):
    return (f(x + h, y) - f(x - h, y)) / (2 * h)

def partial_derivative_y(f, x, y, h=1e-6):
    return (f(x, y + h) - f(x, y - h)) / (2 * h)

# Metode Newton-Raphson
def newton_raphson():
    x = 1.2  # Perkiraan awal x
    y = 1.2  # Perkiraan awal y
    error = 1.0  # Kesalahan awal

    while error > 1e-4:
        f1 = equation1(x, y)
        f2 = equation2(x, y)

        df1_dx = partial_derivative_x(equation1, x, y)
        df1_dy = partial_derivative_y(equation1, x, y)
        df2_dx = partial_derivative_x(equation2, x, y)
        df2_dy = partial_derivative_y(equation2, x, y)

        determinant = df1_dx * df2_dy - df1_dy * df2_dx

        x_new = x - (f1 * df2_dy - f2 * df1_dy) / determinant
        y_new = y - (f2 * df1_dx - f1 * df2_dx) / determinant

        error = max(abs(x_new - x), abs(y_new - y))  # Menghitung kesalahan
        x = x_new
        y = y_new

    return x, y

# Solusi
solution_x, solution_y = newton_raphson()
print("Solusi:")
print("x =", solution_x)
print("y =", solution_y)

#No 3B1
import matplotlib.pyplot as plt

# Persamaan pertama: y = −x^2 + x + 0.75
x = []
y1 = []
for i in range(-20, 21):
    x_val = i / 10
    y1_val = -x_val**2 + x_val + 0.75
    x.append(x_val)
    y1.append(y1_val)

# Persamaan kedua: y + 5xy = x^2
y2 = []
for x_val in x:
    if 1 + 5 * x_val != 0:
        y2_val = x_val**2 / (1 + 5 * x_val)
        y2.append(y2_val)
    else:
        y2.append(None)

# Titik perkiraan awal
initial_guess_x = 1.2
initial_guess_y = -initial_guess_x**2 + initial_guess_x + 0.75

# Gambar grafik
plt.plot(x, y1, label='y = −x^2 + x + 0.75')
plt.plot(x, y2, label='y + 5xy = x^2')
plt.scatter(initial_guess_x, initial_guess_y, color='red', label='Initial Guess')

# Konfigurasi grafik
plt.xlabel('x')
plt.ylabel('y')
plt.title('Pendekatan Grafis')
plt.legend()

# Tampilkan grafik
plt.show()

#No 3B2
def gauss_seidel(x0, y0, error=1e-4, max_iter=100):
    x = x0
    y = y0
    iterasi = 0
    while True:
        x_new = (-y + (x**2)) / (5 * x)
        y_new = -x**2 + x + 0.75
        dx = abs(x_new - x)
        dy = abs(y_new - y)
        x = x_new
        y = y_new
        iterasi += 1
        if dx < error and dy < error:
            break
        if iterasi > max_iter:
            break
    return x, y

# Titik perkiraan awal
initial_guess_x = 1.2
initial_guess_y = 1.2

# Solusi menggunakan metode Gauss-Seidel
solusi = gauss_seidel(initial_guess_x, initial_guess_y, error=1e-4)

# Menampilkan solusi
print("Solusi:")
print("x =", solusi[0])
print("y =", solusi[1])

#No 3B3
def gauss_seidel_relaxation(x0, y0, relaxation_factor, error=1e-4, max_iter=100):
    x = x0
    y = y0
    iterasi = 0
    while True:
        x_new = (-y + (x**2)) / (5 * x)
        y_new = -x**2 + x + 0.75
        dx = abs(x_new - x)
        dy = abs(y_new - y)
        x = relaxation_factor * x_new + (1 - relaxation_factor) * x
        y = relaxation_factor * y_new + (1 - relaxation_factor) * y
        iterasi += 1
        if dx < error and dy < error:
            break
        if iterasi > max_iter:
            break
    return x, y

# Titik perkiraan awal
initial_guess_x = 1.2
initial_guess_y = 1.2

# Nilai relaksasi
relaxation_factor = 0.8

# Solusi menggunakan metode Gauss-Seidel dengan relaksasi
solusi = gauss_seidel_relaxation(initial_guess_x, initial_guess_y, relaxation_factor, error=1e-4)

# Menampilkan solusi
print("Solusi:")
print("x =", solusi[0])
print("y =", solusi[1])

#No 3B4
def newton_raphson(x0, y0, error=1e-4, max_iter=100):
    x = x0
    y = y0
    iterasi = 0
    while True:
        f1 = -x**2 + x + 0.75 - y
        f2 = y + 5 * x * y - x**2
        jacobian = [[-2 * x + 1, -1], [-2 * x + 5 * y, 5 * x + 1]]
        delta_x = (jacobian[0][0] * f1 + jacobian[0][1] * f2) / (jacobian[0][0]**2 + jacobian[0][1]**2)
        delta_y = (jacobian[1][0] * f1 + jacobian[1][1] * f2) / (jacobian[1][0]**2 + jacobian[1][1]**2)
        x -= delta_x
        y -= delta_y
        iterasi += 1
        if abs(delta_x) < error and abs(delta_y) < error:
            break
        if iterasi > max_iter:
            break
    return x, y

# Titik perkiraan awal
initial_guess_x = 1.2
initial_guess_y = 1.2

# Solusi menggunakan metode Newton-Raphson
solusi = newton_raphson(initial_guess_x, initial_guess_y, error=1e-4)

# Menampilkan solusi
print("Solusi:")
print("x =", solusi[0])
print("y =", solusi[1])