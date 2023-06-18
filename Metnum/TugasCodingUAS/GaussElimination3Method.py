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
def lu_factorization(matrix):
    n = len(matrix)
    lower = [[0.0] * n for _ in range(n)]
    upper = [[0.0] * n for _ in range(n)]

    for i in range(n):
        # Upper Triangular
        for k in range(i, n):
            sum_uk = sum(lower[i][j] * upper[j][k] for j in range(i))
            upper[i][k] = matrix[i][k] - sum_uk

        # Lower Triangular
        for k in range(i, n):
            if i == k:
                lower[i][i] = 1.0
            else:
                sum_lk = sum(lower[k][j] * upper[j][i] for j in range(i))
                lower[k][i] = (matrix[k][i] - sum_lk) / upper[i][i]

    return lower, upper


def lu_substitution(lower, upper, b):
    n = len(lower)
    y = [0.0] * n
    x = [0.0] * n

    # Forward Substitution: Ly = b
    for i in range(n):
        sum_ly = sum(lower[i][j] * y[j] for j in range(i))
        y[i] = (b[i] - sum_ly) / lower[i][i]

    # Backward Substitution: Ux = y
    for i in range(n - 1, -1, -1):
        sum_ux = sum(upper[i][j] * x[j] for j in range(i + 1, n))
        x[i] = (y[i] - sum_ux) / upper[i][i]

    return x

def LUSolve(A,B):
    lower,upper = lu_factorization(A)
    x = lu_substitution(lower,upper,B)
    return x

# Cholesky Factorization
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

    # Forward substitution: Ly = b
    for i in range(n):
        y[i] = (b[i] - sum(L[i][j] * y[j] for j in range(i))) / L[i][i]

    # Backward substitution: L^T x = y
    for i in range(n - 1, -1, -1):
        x[i] = (y[i] - sum(L[j][i] * x[j] for j in range(i + 1, n))) / L[i][i]

    return x



# 10x1 + 2x2 − x3 = 27
#
# −3x1 − 6x2 + 2x3 = −61.5
#
# x1 + x2 + 5x3 = −21.5

#MATRIX NORMAL
matrix = [[10,2,-1],[-3,-6,2],[1,1,5]]
matrixB = [27,-61.5,-21.5]

#MATRIX IN NUMPY
matrixn = np.array([[10,2,-1],[-3,-6,2],[1,1,5]])
matrixnb = np.array([27,-61.5,-21.5])
x = LUSolve(matrix,matrixB)
y = gaussian_elimination(matrix,matrixB)
z = solve_linear_equation(matrix,matrixB)
a = np.linalg.solve(matrixn,matrixnb)
#
# print(a)
# print("Naive Gauss Solve : ",y)
# print("LU Solve : ", x)
# print(z)

for i in range(len(matrix)):
    wa = 0
    for j in range(len(matrix)):
        wa = wa + matrix[i][j] * a[j]
    print(wa)




