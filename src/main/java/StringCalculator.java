import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Logger logger;

    public StringCalculator() {
        logger = new LoggerStub();
    }

    public StringCalculator(Logger logger) {
        this.logger = logger;
    }

    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        List<String> delimiters = new ArrayList<>();
        delimiters.add(","); // Default delimiter
        delimiters.add("\n"); // Default newline delimiter

        // Check for custom delimiters
        if (input.startsWith("//")) {
            int delimiterEndIndex = input.indexOf("\n");
            if (delimiterEndIndex == -1) {
                delimiterEndIndex = input.indexOf(",");  // use comma if no newline
                if (delimiterEndIndex == -1) {
                    delimiterEndIndex = input.length();  // or use the end of string if no comma
                }
            }

            String delimiterPart = input.substring(2, delimiterEndIndex);
            if (delimiterPart.startsWith("[")) {
                Matcher delimiterMatcher = Pattern.compile("\\[(.*?)\\]").matcher(delimiterPart);
                while (delimiterMatcher.find()) {
                    delimiters.add(Pattern.quote(delimiterMatcher.group(1)));
                }
            } else {
                delimiters.add(Pattern.quote(delimiterPart));
            }
            input = input.substring(delimiterEndIndex + 1);
        }

        // Build regex pattern string combining all delimiters
        String delimiterRegex = String.join("|", delimiters);

        // Split the numbers using the combined delimiter regex
        String[] numbers = input.split(delimiterRegex);
        List<Integer> negativeNumbers = new ArrayList<>();
        int sum = 0;

        for (String numberString : numbers) {
            if (!numberString.isEmpty()) {
                int number = Integer.parseInt(numberString.trim());
                if (number < 0) {
                    negativeNumbers.add(number);
                }
                if (number > 1000) {
                    logger.log(number);  // Log the number if it's greater than 1000
                } else {
                    sum += number; // Sum only includes numbers <= 1000
                }
            }
        }
        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }
        return sum;
    }
}
