import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StringCalculatorCLI {

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final Logger logger;

    public StringCalculatorCLI(InputStream inputStream, OutputStream outputStream, Logger logger) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.logger = logger;
    }

    public void run() {
        Scanner scanner = new Scanner(inputStream);
        PrintStream out = new PrintStream(outputStream);

        out.println("Welcome to StringCalculator");
        out.println("Enter commands in the format: scalc 'numbers', e.g., scalc '1,2,3'");
        out.println("Type 'exit' to quit.");

        while (true) {
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                break;
            }
            if (input.startsWith("scalc '") && input.endsWith("'")) {
                input = input.substring(7, input.length() - 1);
                try {
                    int result = calculate(input);
                    out.println("The result is " + result);
                } catch (Exception e) {
                    out.println("Error: " + e.getMessage());
                }
            } else {
                out.println("Invalid input format. Please use the format: scalc 'numbers'");
            }
        }

        scanner.close();
        out.println("Exiting...");
    }
    public int calculate(String input) {
        StringCalculator calculator = new StringCalculator(logger);
        return calculator.add(input);
    }
}
