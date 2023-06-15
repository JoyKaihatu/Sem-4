import numpy as np

def lu_factorization_step(A):
    n = A.shape[0]
    L = np.eye(n)
    U = np.zeros_like(A)

    for k in range(n):
        U[k, k:] = A[k, k:] - L[k, :k] @ U[:k, k:]
        L[k + 1:, k] = (A[k + 1:, k] - L[k + 1:, :k] @ U[:k, k]) / U[k, k]

    return L, U


A = np.array([[6,15,55],[15,55,225],[55,225,979]])
b = np.array([76,295,1259])

testing = np.linalg.cholesky(A)
testin2 = np.transpose(testing)
testing3 = np.matmul(testing,testin2)
L, U = lu_factorization_step(A)
d = np.divide(b,testing)

np.mat
print("L: ")
print(L)
print("d: ")
print(d)
print("------testing------")
print(testing)
print("------testing3-----")
print(testing3)
print("------testing2-----")
print(testin2)