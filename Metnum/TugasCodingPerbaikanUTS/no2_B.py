def newton_Raphson() :
    x0 = 3.5

    for i in range(3):
        fx = (x0)**3 - 6 * (x0)**2 + 11 * x0 - 6.1
        fxAksen= 3 * (x0)**2 - 12 * x0 + 11

        xPerubahan = x0 - fx/fxAksen
        x0 = xPerubahan

    return x0

result = newton_Raphson()
print(result)
