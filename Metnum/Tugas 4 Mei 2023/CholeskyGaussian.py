import numpy as np

def cholesky_gaussian_elimination(A, b):
    n = A.shape[0]
    L = np.zeros_like(A)

    # Cholesky factorization
    for i in range(n):
        for j in range(i+1):
            if i == j:
                L[i, j] = np.sqrt(A[i, i] - np.sum(L[i, :j]**2))
            else:
                L[i, j] = (A[i, j] - np.sum(L[i, :j] * L[j, :j])) / L[j, j]

    # Solve Ly = b using forward substitution
    y = np.zeros(n)
    for i in range(n):
        y[i] = (b[i] - np.sum(L[i, :i] * y[:i])) / L[i, i]

    # Solve L^Tx = y using back substitution
    x = np.zeros(n)
    for i in range(n):
        x[i] = (y[i] - np.sum(L[i, :i] * x[:i])) / L[i, i]

    return x

A = np.array([[4, 2, 2], [2, 5, 4], [2, 4, 11]])
b = np.array([4, 6, 9])

print(cholesky_gaussian_elimination(A,b))