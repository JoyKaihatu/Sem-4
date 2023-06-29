import numpy as np
import matplotlib.pyplot as plt


def f(x):
    return x**3 - 6*x**2 + 11*x - 6.1

        # -12-21*x+18*x**2-2.75*x**3

        # x**3 - 6*x**2 + 11*x - 6.1

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


graphical_method()