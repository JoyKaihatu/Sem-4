import numpy as np


def lu_factorization_step(A):
    n = A.shape[0]
    L = np.eye(n)
    U = np.zeros_like(A)

    for k in range(n):
        U[k, k:] = A[k, k:] - L[k, :k] @ U[:k, k:]
        L[k + 1:, k] = (A[k + 1:, k] - L[k + 1:, :k] @ U[:k, k]) / U[k, k]

    return L, U


def substitution_step(L, U, b):
    n = L.shape[0]
    y = np.zeros(n)
    x = np.zeros(n)

    y[0] = b[0] / L[0, 0]

    for k in range(1, n):
        y[k] = (b[k] - L[k, :k] @ y[:k]) / L[k, k]

    x[-1] = y[-1] / U[-1, -1]

    for k in range(n - 2, -1, -1):
        x[k] = (y[k] - U[k, k + 1:] @ x[k + 1:]) / U[k, k]

    return x


# Example usage
A = np.array([[3, -0.1, -0.2], [0.1, 7, -0.3], [0.3, -0.2, 10]])
b = np.array([7.85, -19.3, 71.4])

L, U = lu_factorization_step(A)
x = substitution_step(L, U, b)


print("x: ", x)
