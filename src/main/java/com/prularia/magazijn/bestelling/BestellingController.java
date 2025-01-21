package com.prularia.magazijn.bestelling;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/bestellingen")
public class BestellingController {

    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    // Initialiseer bestellingSink voor het streamen van updates
    private final Sinks.Many<Bestelling> bestellingSink = Sinks.many().replay().latest();

    @GetMapping("/{bestelId}")
    public Bestelling findBestellingById(@PathVariable long bestelId) {
        return bestellingService.findBestellingById(bestelId); //niet meer nodig?

    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Bestelling> streamNextBestelling() { // Nieuwe bestellingen worden hier automatisch naar de client gestuurd
        return bestellingSink.asFlux();
    }

    @PostMapping("/{bestelId}/voltooi")
    public void markAsCompleted(@PathVariable long bestelId) {
        bestellingService.markAsCompleted(bestelId); // Update status naar 'Onderweg'
        bestellingService.findNextBestelling()
                .ifPresentOrElse(
                        bestellingSink::tryEmitNext, // Stuur de volgende bestelling
                        () -> bestellingSink.tryEmitNext(null) // Stuur 'null' als er geen bestellingen zijn
                );
    }
}
