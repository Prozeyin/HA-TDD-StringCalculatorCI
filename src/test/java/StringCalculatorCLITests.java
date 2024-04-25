import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringCalculatorCLITests {

    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    @Test
    public void whenMainMethodIsCalled_ThenWelcomeAndHelpTextArePrinted() {
        // Arrange - simulate user entering "exit" to terminate the main method
        String input = "exit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act - Call the main method which should print the welcome and help text then exit
        Main.main(new String[] {});

        // Assert - Verify the output contains the expected welcome and help text
        String lineSeparator = System.lineSeparator();
        String expectedOutput = "Welcome to StringCalculator" + lineSeparator +
                "Enter commands in the format: scalc 'numbers', e.g., scalc '1,2,3'" + lineSeparator +
                "Type 'exit' to quit." + lineSeparator;
        String actualOutput = outputStreamCaptor.toString();

        assertTrue(actualOutput.contains(expectedOutput), "The expected welcome and help text were not printed.");
    }

    @Test
    public void whenScalcCommandIsGivenWith123_ThenTheResultIs6IsPrinted() {
        // Arrange - simulate user entering "scalc '1,2,3'" followed by "exit"
        String input = "scalc '1,2,3'" + System.lineSeparator() + "exit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act - Call the main method which should process the input and print the result
        Main.main(new String[] {});

        // Assert - Verify the output contains the expected result text "The result is 6"
        String expectedOutput = "The result is 6";
        String actualOutput = outputStreamCaptor.toString();

        assertTrue(actualOutput.contains(expectedOutput), "The output should contain: " + expectedOutput);
    }

    @Test
    public void whenMultipleInputsAreGiven_ThenProgramContinuesToAskForInput() {
        // Arrange - simulate user entering several commands followed by "exit"
        String multipleInputs =
                "scalc '1,2,3'" + System.lineSeparator() +
                        "scalc '4,5'" + System.lineSeparator() +
                        "exit" + System.lineSeparator(); // "exit" to signal the end of input
        InputStream in = new ByteArrayInputStream(multipleInputs.getBytes());
        System.setIn(in);

        // Act - Call the main method which should process each input and exit after "exit"
        Main.main(new String[] {});

        // Assert - Verify the output contains results for each command
        String actualOutput = outputStreamCaptor.toString();

        assertTrue(actualOutput.contains("The result is 6"), "The output should contain: The result is 6");
        assertTrue(actualOutput.contains("The result is 9"), "The output should contain: The result is 9");
    }


}
