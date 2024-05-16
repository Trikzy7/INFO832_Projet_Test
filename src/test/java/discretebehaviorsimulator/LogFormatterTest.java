package discretebehaviorsimulator;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.*;

class LogFormatterTest {

    @Test
    void testFormatCorrect() {
        LogRecord rec = new LogRecord(Level.INFO, "Test message");
        LogFormatter logFormatter = new LogFormatter();

        String result = logFormatter.format(rec);

        String expectedDate = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS").format(new Date(rec.getMillis()));
        String expected = expectedDate + ": " + rec.getLevel() + System.getProperty("line.separator") + rec.getMessage() + System.getProperty("line.separator");

        assertEquals(expected, result, "The formatted string should match the expected format");
    }

    @Test
    void testFormatWithNullLogRecord() {
        LogFormatter logFormatter = new LogFormatter();
        LogRecord rec = null;

        assertThrows(NullPointerException.class, () -> {
            logFormatter.format(rec);
        }, "Formatting should fail with a null LogRecord");
    }

    @Test
    void testGetHeadEmptyString() {
        LogFormatter logFormatter = new LogFormatter();
        ConsoleHandler handler = new ConsoleHandler();

        String result = logFormatter.getHead(handler);

        assertEquals("", result, "The head string should be empty");
    }

    @Test
    void testGetHeadWithNullHandler() {
        LogFormatter logFormatter = new LogFormatter();
        Handler handler = null;

        String result = logFormatter.getHead(handler);

        assertEquals("", result, "The head string should be empty when called with a null handler");
    }

    @Test
    void testGetTailEmptySring() {
        LogFormatter logFormatter = new LogFormatter();
        ConsoleHandler handler = new ConsoleHandler();

        // Fermer le gestionnaire
        handler.close();

        String result = logFormatter.getTail(handler);

        assertEquals("", result, "The tail string should be empty");
    }

    @Test
    void testGetTailWithNullHandler() {
        LogFormatter logFormatter = new LogFormatter();
        Handler handler = null;

        String result = logFormatter.getTail(handler);

        assertEquals("", result, "The tail string should be empty when called with a null handler");
    }
}