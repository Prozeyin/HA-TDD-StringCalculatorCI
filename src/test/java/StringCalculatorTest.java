import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
public class StringCalculatorTest {

    private Logger logger;
    private StringCalculator calculator;

    @BeforeEach
    public void beforeEach() {
        logger = Mockito.mock(Logger.class);
        calculator = new StringCalculator(logger);
    }

    @Test
    public void testEmptyStringReturnsZero() {
        Assertions.assertEquals(0, calculator.add(""));
    }


    @Test
    public void testAddOneInteger() { Assertions.assertEquals(1, calculator.add("1"));}

    @Test
    public void testAddTwoIntegers() { Assertions.assertEquals(3,calculator.add("1,2")); }

    @Test
    public void testAddMultipleIntegers() { Assertions.assertEquals(5, calculator.add("1,2,1,1")); }

    @Test
    public void testAddWithSpace() { Assertions.assertEquals(8, calculator.add("1, 2, 3, 2")); }

    @Test
    public void testAddWithNewLineBetweenNumbers() {
        Assertions.assertEquals(6, calculator.add("1\n2\n3"));
    }

    @Test
    public void testWithMixedDelimiters() {
        Assertions.assertEquals(6, calculator.add("1\n2,3"));
    }
    @Test
    public void testAddWithCustomSingleCharacterDelimiter() { Assertions.assertEquals(3, calculator.add("//;\n1;2")); }

    @Test
    public void  testAddWithCustomDelimiterOfMultipleCharacters() {
        Assertions.assertEquals(6, calculator.add("//***,1***2***3"));
    }

    @Test
    public void testAddWithDifferentCustomDelimiters() {
        Assertions.assertEquals(10, calculator.add("//abc\n1abc2,3\n4"));
    }

    @Test
    public void testAddWithNegativeNumbers() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculator.add("1,-2,3,-4");
        });
        Assertions.assertTrue(exception.getMessage().contains("Negatives not allowed: [-2, -4]"));
    }

    @Test
    public void testAddNumbersGreaterThan1000AreLogged() {
        calculator.add("2,1001");
        verify(logger).log(1001);
    }

    @Test
    public void whenSingleCustomDelimiterIsUsed_ThenTheSumIsCalculatedCorrectly() {
        StringCalculator calculator = new StringCalculator(new LoggerStub());
        String input = "//[*]\n1*2*3";
        Assertions.assertEquals(6, calculator.add(input), "The calculator should handle a single custom delimiter correctly.");
    }

    @Test
    public void whenMultipleCustomDelimitersAreUsed_ThenTheSumIsCalculatedCorrectly() {
        StringCalculator calculator = new StringCalculator(new LoggerStub());
        String input = "//[*][%]\n1*2%3";
        Assertions.assertEquals(6, calculator.add(input), "The calculator should handle multiple custom delimiters correctly.");
    }

    @Test
    public void whenCustomDelimitersOfDifferentLengthsAreUsed_ThenTheSumIsCalculatedCorrectly() {
        StringCalculator calculator = new StringCalculator(new LoggerStub());
        String input = "//[***][%%%]\n1***2%%%3";
        Assertions.assertEquals(6, calculator.add(input), "The calculator should handle custom delimiters of different lengths correctly.");
    }

    @Test
    public void whenComplexDelimitersAndEmptyString_ThenResultIsZero() {
        StringCalculator calculator = new StringCalculator(new LoggerStub());
        String input = "//[***][%%%]\n";
        Assertions.assertEquals(0, calculator.add(input), "The calculator should return zero for an empty string.");
    }

}

