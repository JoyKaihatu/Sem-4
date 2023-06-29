def mencariF(x) :
    return (x)**3 - 6 * (x)**2 + 11 * x - 6.1

def modified_SecantMethod() :
    x0 = 3.5
    delta = 0.01

    for i in range(3) :
        fx = mencariF(x0)
        xPerubahan = x0 - (delta * x0 * fx) / (mencariF(x0 + delta * x0) - fx)
        x0 = xPerubahan

    return xPerubahan

result = modified_SecantMethod()
print(result)