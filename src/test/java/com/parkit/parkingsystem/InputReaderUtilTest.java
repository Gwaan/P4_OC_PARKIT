package com.parkit.parkingsystem;

import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class InputReaderUtilTest {

    private static InputReaderUtil inputReaderUtil;

    @BeforeEach
    public void setUpPerTest() {
        inputReaderUtil = new InputReaderUtil();
    }

    @Test
    public void readSelectionTest() {
        // ARRANGE
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // ASSERT
        assertEquals(1, inputReaderUtil.readSelection());
    }

    @Test
    public void readVehicleRegNumberTest() throws Exception {
        // ARRANGE
        String input = "ABCDEF";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // ASSERT
        assertEquals("ABCDEF", inputReaderUtil.readVehicleRegistrationNumber());
    }

    @Test
    public void readVehicleRegNumberWhenInputIsInvalid() {
        // ARRANGE
        String input = "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // ASSERT
        assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
    }


}
