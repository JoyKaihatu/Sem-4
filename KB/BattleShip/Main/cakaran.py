import random

tes = [i for i in range(1,27)]

print(tes)

for i in range(13):
    a = random.randint(0,len(tes)-1)
    print(tes.pop(a))