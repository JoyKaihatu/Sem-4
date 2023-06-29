import numpy as np
def lu_decomposition(matrix):
    n = len(matrix)
    lower = np.zeros((n, n))
    upper = np.zeros((n, n))

    for i in range(n):
        lower[i, i] = 1.0

        for j in range(i + 1):
            sum_upper = sum(lower[i, k] * upper[k, j] for k in range(j))
            upper[i, j] = matrix[i, j] - sum_upper

        for j in range(i, n):
            sum_lower = sum(lower[i, k] * upper[k, j] for k in range(i))
            lower[i, j] = (matrix[i, j] - sum_lower) / upper[i, i]

    return lower, upper


def inverse_matrix(matrix):
    n = len(matrix)
    identity = np.eye(n)
    lower, upper = lu_decomposition(matrix)

    inv_matrix = np.zeros((n, n))
    for i in range(n):
        y = np.zeros(n)
        x = np.zeros(n)
        y[0] = identity[i, 0] / lower[0, 0]

        for j in range(1, n):
            sum_lower = sum(lower[j, k] * y[k] for k in range(j))
            y[j] = (identity[i, j] - sum_lower) / lower[j, j]

        x[n - 1] = y[n - 1] / upper[n - 1, n - 1]
        for j in range(n - 2, -1, -1):
            sum_upper = sum(upper[j, k] * x[k] for k in range(j + 1, n))
            x[j] = (y[j] - sum_upper) / upper[j, j]

        inv_matrix[i] = x

    return inv_matrix


matrix = np.array([[10,2,-1],[-3,-6,2],[1,1,5]])
matrixB = np.array([27,-61.5,-21.5])

print(inverse_matrix(matrix))