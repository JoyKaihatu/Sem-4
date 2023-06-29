def gaussian_elimination(A, b):
    n = len(A)
    # Proses 1: forward elimination
    for i in range(n):
        # Proses 1.1: Menentukan pivot yang digunakan untuk me-nolkan lower triangle
        pivot = A[i][i]
        for j in range(i + 1, n):
            # Proses 1.2: Mencari faktor pembagi.
            factor = A[j][i] / pivot
            for k in range(i, n):
                # Proses 1.3: Melakukan pengurangan dari hasil kali faktor
                A[j][k] = A[j][k] - factor * A[i][k]
            b[j] = b[j] - factor * b[i]
    # Proses 2: back substitution
    x = [0] * n
    for i in range(n - 1, -1, -1):
        # Porses 2.1 : Mencari nilai x1,x2,x3 dengan membagi semua nilai pada row dengan nilai pada matrix B di row yang sama
        x[i] = b[i] / A[i][i]
        for j in range(i - 1, -1, -1):
            b[j] = b[j] - A[j][i] * x[i]
    # Proses 2.2 : Mereturn nilai x1,x2,x3 yang di simpan dalam array x
    return x

#MATRIX NORMAL
matrix = [[10,2,-1],[-3,-6,2],[1,1,5]]
matrixB = [27,-61.5,-21.5]


y = gaussian_elimination(matrix,matrixB)
print("Output: ",y)





