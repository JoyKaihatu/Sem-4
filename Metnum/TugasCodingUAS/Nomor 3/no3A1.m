function F = myEquations(x)
    F = zeros(2, 1);
    F(1) = (x(1) - 4)^2 + (x(2) - 4)^2 - 5;
    F(2) = x(1)^2 + x(2)^2 - 16;
endfunction

initial_guess = [1.2; 1.2];
options = optimset('fsolve');
options.TolFun = 1e-4;
[solution, fval, exitflag] = fsolve(@(x) myEquations(x), initial_guess, options);

if exitflag == 1
    fprintf('Solusi:\n');
    fprintf('x = %f\n', solution(1));
    fprintf('y = %f\n', solution(2));
else
    fprintf('Tidak ada solusi yang ditemukan.\n');
end





