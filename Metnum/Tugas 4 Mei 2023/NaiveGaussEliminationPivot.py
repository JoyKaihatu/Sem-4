# No 2
def gaussian_elimination_pivot(A, b):
    n = len(A)
    # forward elimination
    for i in range(n):
        # partial pivoting
        max_row = i
        for j in range(i+1, n):
            if abs(A[j][i]) > abs(A[max_row][i]):
                max_row = j
        A[i], A[max_row] = A[max_row], A[i]
        b[i], b[max_row] = b[max_row], b[i]
        # elimination
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



#Matrix Pivot

matrixPivot = [[0.0003,3.0000],
               [1.0000, 1.0000]]
matrixPivotB = [2.0001, 1.0000]

print("No 2 (Naive Gauss Pivot) : ",gaussian_elimination_pivot(matrixPivot, matrixPivotB))