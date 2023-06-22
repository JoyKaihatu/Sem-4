function F = myFunction(x)
  F(1) = -x(1)^2 + x(1) + 0.75 - x(2);
  F(2) = x(2) + 5 * x(1) * x(2) - x(1)^2;
endfunction

initial_guess = [1.2; 1.2];
solution = fsolve(@myFunction, initial_guess);

disp("Solusi:");
disp(["x = ", num2str(solution(1))]);
disp(["y = ", num2str(solution(2))]);
