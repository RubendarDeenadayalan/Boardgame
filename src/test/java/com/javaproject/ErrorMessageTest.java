package com.javaproject;
import com.javaproject.beans.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorMessageTest {
    private ErrorMessage errorMessage;

    @BeforeEach
    void setUp() {
        errorMessage = new ErrorMessage("Test error message");
    }

    @Test
    void testConstructorSetsMessage() {
        assertEquals("Test error message", errorMessage.getMessage());
    }

    @Test
    void testGetSTATUSReturnsError() {
        assertEquals("error", errorMessage.getSTATUS());
    }

    @Test
    void testSetMessageUpdatesValue() {
        String newMessage = "Updated error message";
        errorMessage.setMessage(newMessage);
        assertEquals(newMessage, errorMessage.getMessage());
    }

    @Test
    void testSTATUSIsFinalAndConstant() {
        // STATUS is final, so it always returns "error"
        assertEquals("error", errorMessage.getSTATUS());

        errorMessage.setMessage("any change");
        assertEquals("error", errorMessage.getSTATUS()); // Unchanged
    }
}
