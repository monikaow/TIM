package pl.test;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static java.lang.Math.hypot;
import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;


public class MathTest {

    @Test
    public void main() {
        assertEquals("en", new Locale("en").toLanguageTag());
    }
}
