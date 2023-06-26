import numpy as np

x = np.array([[1,2,3],
              [4,5,6],
              [7,8,9]
              ])

rowsum = np.sum(x,axis=1)
colsum = np.sum(x,axis=0)

print(np.argmax(rowsum), np.max(rowsum))
print(np.argmax(colsum), np.max(colsum))
