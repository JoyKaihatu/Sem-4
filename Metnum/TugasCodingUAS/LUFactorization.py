import numpy as np

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


matrix = [[10,2,-1],[-3,-6,2],[1,1,5]]
matrixB = [27,-61.5,-21.5]

x = LUSolve(matrix,matrixB)


