import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class StringCalculatorLoggingTests {

    @Test
    public void testStringCalculatorDoesNotLogNumbersBelow1000(){
        Logger mockLogger = mock(Logger.class);
        StringCalculator calculator = new StringCalculator(mockLogger);

        calculator.add("");
        verify(mockLogger, times(0)).log(anyInt());
    }
}
