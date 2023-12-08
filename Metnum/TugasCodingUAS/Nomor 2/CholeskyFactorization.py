import numpy as np
def cholesky_factorization(A):
    n = len(A)
    L = [[0.0] * n for _ in range(n)]

    for i in range(n):
        for j in range(i + 1):
            if i == j:
                L[i][j] = np.sqrt(A[i][i] - sum(L[i][k] ** 2 for k in range(j)))
            else:
                L[i][j] = (1.0 / L[j][j]) * (A[i][j] - sum(L[i][k] * L[j][k] for k in range(j)))

    return L


def solve_linear_equation(A, b):
    L = cholesky_factorization(A)
    n = len(A)
    y = [0.0] * n
    x = [0.0] * n

    # Proses 2.1: Forward substitution: Ly = b
    for i in range(n):
        y[i] = (b[i] - sum(L[i][j] * y[j] for j in range(i))) / L[i][i]

    # Proses2.2 : Backward substitution: L^T x = y
    for i in range(n - 1, -1, -1):
        x[i] = (y[i] - sum(L[j][i] * x[j] for j in range(i + 1, n))) / L[i][i]

    return x

matrix = [[10,2,-1],[-3,-6,2],[1,1,5]]
matrixB = [27,-61.5,-21.5]


z = solve_linear_equation(matrix,matrixB)

print(z)