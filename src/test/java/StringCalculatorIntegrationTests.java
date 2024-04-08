import org.junit.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorIntegrationTests {
    @Test
    public void testWelcomeMessagePrintsCorrectly(){

        String input = "scalc '0'\nexit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        StringCalculatorCLI calculator = new StringCalculatorCLI(inputStream, outputStream);
        calculator.run();

        assertEquals("0\nExiting...\n", outputStream.toString());
    }
}
