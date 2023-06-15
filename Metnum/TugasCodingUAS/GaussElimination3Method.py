import numpy as np


def gaussian_elimination(A, b):
    n = len(A)
    # forward elimination
    for i in range(n):
        pivot = A[i][i]
        for j in range(i + 1, n):
            factor = A[j][i] / pivot
            for k in range(i, n):
                A[j][k] = A[j][k] - factor * A[i][k]
            b[j] = b[j] - factor * b[i]
    # back substitution
    x = [0] * n
    for i in range(n - 1, -1, -1):
        x[i] = b[i] / A[i][i]
        for j in range(i - 1, -1, -1):
            b[j] = b[j] - A[j][i] * x[i]
    return x


# LU Factorization
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


# Cholesky Factorization
def cholesky_factorization(A):
    n = len(A)
    L = np.zeros_like(A)

    for i in range(n):
        for j in range(i + 1):
            if i == j:
                temp = A[i, j] - np.sum(L[i, :j] ** 2)
                L[i, j] = np.sqrt(temp) if temp > 0 else 0
            else:
                L[i, j] = (A[i, j] - np.dot(L[i, :j], L[j, :j])) / L[j, j]

    return L


def solve_equation(L, b):
    n = len(L)
    y = np.zeros(n)

    for i in range(n):
        y[i] = (b[i] - np.dot(L[i, :i], y[:i])) / L[i, i]

    x = np.zeros(n)

    for i in range(n - 1, -1, -1):
        x[i] = (y[i] - np.dot(L[i + 1:, i], x[i + 1:])) / L[i, i]

    return x


def gaussian_cholesky(A,b):
    L = cholesky_factorization(A)
    x = solve_equation(L, b)
    return x



# LU Factorization Matrix
A = np.array([[3, -0.1, -0.2], [0.1, 7, -0.3], [0.3, -0.2, 10]])
b = np.array([7.85, -19.3, 71.4])



AB = np.array([[4, 2, 2], [2, 5, 4], [2, 4, 11]])
bB = np.array([4, 6, 9])

# 10x1 + 2x2 − x3 = 27
#
# −3x1 − 6x2 + 2x3 = −61.5
#
# x1 + x2 + 5x3 = −21.5


matrix = [[10, 2, -1], [-3, -6, 2], [1, 1, 5]]

matrixB = [27, -61.5, -21.5]

matrixC = [[3, -0.1, -0.2], [0.1, 7, -0.3], [0.3, -0.2, 10]]
matrixD = [7.85, -19.3, 71.4]

matrixE = np.array([[6,15,55],[15,55,225],[55,225,979]])
matrixF = np.array([76,295,1259])

L, U = lu_factorization_step(A)
x = substitution_step(L, U, b)

print("Naive Gauss Solve : ", gaussian_elimination(matrixC, matrixD))
print("LU Solve : ", x)
print("Cholesky Solve : ", gaussian_cholesky(AB,bB))
