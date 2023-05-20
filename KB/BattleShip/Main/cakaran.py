import numpy as np
array = [0 for _ in range(10)]
array.append(1)
print(array)
np.random.shuffle(array)
print(array)

x = array[0] + 1
print(x)