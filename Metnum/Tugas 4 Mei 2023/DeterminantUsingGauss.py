#No 3
def determinant(A):
    n = len(A)
    # make a copy of A
    B = [row[:] for row in A]
    # forward elimination with partial pivoting
    sign = 1
    for i in range(n):
        # partial pivoting
        max_row = i
        for j in range(i+1, n):
            if abs(B[j][i]) > abs(B[max_row][i]):
                max_row = j
        if max_row != i:
            B[i], B[max_row] = B[max_row], B[i]
            sign *= -1
        # elimination
        pivot = B[i][i]
        if pivot == 0:
            return 0
        for j in range(i+1, n):
            factor = B[j][i] / pivot
            for k in range(i, n):
                B[j][k] = B[j][k] - factor * B[i][k]
    # compute determinant
    det = sign
    for i in range(n):
        det *= B[i][i]
    return det

#No 3 Matrix
A = [[1, 2, -1], [2, -1, 3], [3, 1, 1]]

print("No 3 (Determinan) : ",determinant(A))