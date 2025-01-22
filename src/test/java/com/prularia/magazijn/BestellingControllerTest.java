package com.prularia.magazijn;

import com.prularia.magazijn.bestelling.Bestelling;
import com.prularia.magazijn.bestelling.BestellingController;
import com.prularia.magazijn.bestelling.BestellingService;
import com.prularia.magazijn.bestelling.BestellingsStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BestellingControllerTest {

    @Test
    void testFindBestellingById_FoundAndNotFound() {
        // Mock de service
        BestellingService bestellingService = mock(BestellingService.class);
        BestellingController bestellingController = new BestellingController(bestellingService);

        // Arrange: Gevonden scenario
        long foundBestelId = 1L;
        Bestelling foundBestelling = new Bestelling(
                foundBestelId,
                LocalDateTime.now(),
                123L,
                true,
                "PAY123",
                "REF456",
                BestellingsStatus.BETAALD,
                false,
                "Bedrijf BV",
                "NL123456789B01",
                "John",
                "Doe",
                456L,
                789L
        );

        when(bestellingService.findBestellingById(foundBestelId)).thenReturn(foundBestelling);

        // Act: Test gevonden bestelling
        Bestelling result = bestellingController.findBestellingById(foundBestelId);

        // Assert: Controleer de gevonden bestelling
        assertNotNull(result);
        assertEquals(foundBestelId, result.getBestelId());
        assertEquals("John", result.getVoornaam());
        assertEquals("Doe", result.getFamilienaam());

        // Arrange: Niet gevonden scenario
        long notFoundBestelId = 2L;
        when(bestellingService.findBestellingById(notFoundBestelId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Bestelling not found"));

        // Act & Assert: Test niet-gevonden bestelling
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                bestellingController.findBestellingById(notFoundBestelId)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Bestelling not found", exception.getReason());

        // Controleer dat beide service-aanroepen correct zijn gedaan
        verify(bestellingService, times(1)).findBestellingById(foundBestelId);
        verify(bestellingService, times(1)).findBestellingById(notFoundBestelId);
    }
}
