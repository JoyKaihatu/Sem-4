import numpy as np


def choleskyFactorization(A):
    """
    Performs Cholesky factorization on a positive definite matrix A.

    Parameters:
        A (ndarray): Positive definite matrix.

    Returns:
        L (ndarray): Lower triangular matrix L such that A = LL^T.
    """
    # Check if the matrix is square
    if A.shape[0] != A.shape[1]:
        raise ValueError("Input matrix must be square.")

    n = A.shape[0]  # Dimension of the matrix
    L = np.zeros_like(A)  # Initialize the lower triangular matrix L

    for i in range(n):
        for j in range(i + 1):
            if i == j:
                L[i, i] = np.sqrt(A[i, i] - np.sum(L[i, :i] ** 2))
            else:
                L[i, j] = (A[i, j] - np.sum(L[i, :j] * L[j, :j])) / L[j, j]

    return L

A = np.array([[6,15,55],[15,55,225],[55,225,979]])
print(choleskyFactorization(A))