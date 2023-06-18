import math

import numpy as np


def cholesky_factorization(matrix):
    n = len(matrix)
    L = [[0.0] * n for _ in range(n)]

    for i in range(n):
        for j in range(i + 1):
            temp_sum = sum(L[i][k] * L[j][k] for k in range(j))
            if i == j:
                L[i][j] = math.sqrt(matrix[i][i] - temp_sum)
            else:
                L[i][j] = (1.0 / L[j][j] * (matrix[i][j] - temp_sum))

    return L


def cholesky_solver(matrix, b):
    n = len(matrix)
    L = cholesky_factorization(matrix)

    # Forward substitution: Ly = b
    y = [0.0] * n
    for i in range(n):
        temp_sum = sum(L[i][j] * y[j] for j in range(i))
        y[i] = (b[i] - temp_sum) / L[i][i]

    # Back substitution: L^T x = y
    x = [0.0] * n
    for i in range(n - 1, -1, -1):
        temp_sum = sum(L[j][i] * x[j] for j in range(i + 1, n))
        x[i] = (y[i] - temp_sum) / L[i][i]

    return x


def is_pos_def(x):
    return np.all(np.linalg.eigvals(x) > 0)


def cholesky(a):
    n = len(a)
    for k in range(n):
        a[k, k] = math.sqrt(a[k, k] - np.dot(a[k, 0:k], a[k, 0:k]))
        if is_pos_def(a) is False:
            print("Matrix is not postivie definite")
            break
        for i in range(k + 1, n):
            a[i, k] = (a[i, k] - np.dot(a[i, 0:k], a[k, 0:k])) / a[k, k]
    for k in range(1, n):
        a[0:k, k] = 0.0
    return a


def choleskySolve(L, b):
    n = len(b)
    for k in range(n):
        b[k] = (b[k] - np.dot(L[k, 0:k], b[0:k])) / L[k, k]

    for k in range(n - 1, -1, -1):
        b[k] = (b[k] - np.dot(L[k + 1:n, k], b[k + 1:n])) / L[k, k]
    return b


def choleskyReal(a):
    a = np.array(a, float)
    L = np.zeros_like(a)
    n, _ = np.shape(a)

    for j in range(n):
        for i in range(j, n):
            if i == j:
                sumk = 0
                for k in range(j):
                    sumk += L[i, k] ** 2
                L[i, j] = np.sqrt(a[i, j] - sumk)
            else:
                sumk = 0
                for k in range(j):
                    sumk += L[i, k] * L[j, k]
                L[i, j] = (a[i, j] - sumk) / L[j, j]
    return L


def choleskySlicing(a):
    a = np.array(a, float)
    L = np.zeros_like(a)
    n, _ = np.shape(a)
    for j in range(n):
        for j in range(n):
            for i in range(j, n):
                if i == j:
                    L[i, j] = np.sqrt(a[i, j] - np.sum(L[i, :j] ** 2))
                else:
                    L[i, j] = (a[i, j] - np.sum(L[i, :j] * L[j, :j])) / L[j, j]

    return L

def solveLUChol(L,U,b):
    L = np.array(L, float)
    U = np.array(U, float)
    b = np.array(b, float)
    n, _ = np.shape(L)
    y = np.zeros(n)
    x = np.zeros(n)

    #Forward Subtitution
    for i in range(n):
        sumj = 0
        for j in range(i):
            sumj += L[i,j] * y[j]
        y[i] = (b[i] - sumj)/L[i,i]
    #Backward Subtitution
    for i in range(n-1,-1,-1):
        sumj = 0
        for j in range(i+1,n):
            sumj += U[i,j] * x[j]
        x[i] = (y[i] - sumj)/U[i,i]
    return x

def solveLUCholesky(L, U, b):
    L = np.array(L, float)
    U = np.array(U, float)
    b = np.array(b, float)
    n, _ = np.shape(L)
    y = np.zeros(n)
    x = np.zeros(n)

    # Forward Subtitution
    for i in range(n):
        y[i] = (b[i] - np.sum(L[i, :i] * y[:i])) / L[i, i]

    # Backward Subtitution
    for i in range(n - 1, -1, -1):
        x[i] = (y[i] - np.sum(U[i, i + 1:n] * x[i + 1:n])) / U[i, i]
    return x


matrixE = np.array([[6, 15, 55], [15, 55, 225], [55, 225, 979]])
matrixF = np.array([76, 295, 1259])
A = np.array([[3,-.1,-.2], [.1,7,-.3], [.3,-.2, 10]])
b = np.array([7.85, -19.3, 71.4])

AA = [[8.00,3.22,0.8,0.00,4.10],
      [3.22,7.76,2.33,1.91,-1.03],
      [0.8,2.33,5.25,1.00,3.02],
      [0.00,1.91,1.00,7.50,1.03],
      [4.10,-1.03,3.02,1.03,6.44]]

BB = [9.45,-12.20,7.78,-8.1,10.0]

L = choleskySlicing(A)

print("--------------------A,B-----------------")
print(L)
print(np.linalg.solve(A,b))
print(solveLUChol(L,np.transpose(L),b))
print(solveLUCholesky(L, np.transpose(L), b))

print("-----------------------AA,BB----------------")
L = choleskySlicing(AA)
print(L)
print(np.linalg.solve(AA,BB))
print(solveLUChol(L,np.transpose(L),BB))
print(solveLUCholesky(L,np.transpose(L),BB))

# print(ril.T)
#
# print("x")
# print(x)
# print("L")
# print(L)
# print("LL")
# print(LL)
# print("xx")
# print(xx)
# print("ril")
# print(ril)
#
# print("choleskySlicing")
# print(choleskySlicing(matrixE))
