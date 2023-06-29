def secant_Method() :
    x0 = 3.5
    xPangkatMines1 = 2.5

    for i in range(3):
        fxPangkat = (xPangkatMines1)**3 - 6 * (xPangkatMines1)**2 + 11 * xPangkatMines1 - 6.1
        fx = (x0)**3 - 6 * (x0)**2 + 11 * x0 - 6.1

        xPerubahan = x0 - (fx * (x0 - xPangkatMines1) / (fx - fxPangkat))
        xPangkatMines1 = x0
        x0 = xPerubahan
        
    return x0


result = secant_Method()
print(result)