public class Main {
    public static void main(String[] args) {
        Logger logger = new LoggerStub(); // Replace with the appropriate logger
        StringCalculatorCLI cli = new StringCalculatorCLI(System.in, System.out, logger);
        cli.run();
    }
}
