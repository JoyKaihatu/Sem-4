## No 1
def gaussian_elimination(A, b):
    n = len(A)
    # forward elimination
    for i in range(n):
        pivot = A[i][i]
        for j in range(i+1, n):
            factor = A[j][i] / pivot
            for k in range(i, n):
                A[j][k] = A[j][k] - factor * A[i][k]
            b[j] = b[j] - factor * b[i]
    # back substitution
    x = [0] * n
    for i in range(n-1, -1, -1):
        x[i] = b[i] / A[i][i]
        for j in range(i-1, -1, -1):
            b[j] = b[j] - A[j][i] * x[i]
    return x





matrix = [[3,-0.1,-0.2],
          [0.1,7,-0.3,],
          [0.3,-0.2,10]]

matrixB = [7.85,-19.3,71.4]




print("No 1 (Naive Gauss) : ",gaussian_elimination(matrix,matrixB))
