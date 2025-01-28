package com.prularia.magazijn.inkomendeLevering;

import com.google.common.collect.Lists;
import com.prularia.magazijn.inkomendeLeveringsLijn.InkomendeLeveringsLijn;
import com.prularia.magazijn.inkomendeLeveringsLijn.InkomendeOnvolledigeLeveringslijnDTO;
import com.prularia.magazijn.artikel.ArtikelRepository;
import com.prularia.magazijn.magazijnplaats.Magazijnplaats;
import com.prularia.magazijn.magazijnplaats.MagazijnplaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class InkomendeLeveringService {

    private final InkomendeLeveringRepository inkomendeLeveringRepository;
    private final MagazijnplaatsRepository magazijnplaatsRepository;
    private final ArtikelRepository artikelRepository;

    public InkomendeLeveringService(InkomendeLeveringRepository inkomendeLeveringRepository, MagazijnplaatsRepository magazijnplaatsRepository, ArtikelRepository artikelRepository) {
        this.inkomendeLeveringRepository = inkomendeLeveringRepository;
        this.magazijnplaatsRepository = magazijnplaatsRepository;
        this.artikelRepository = artikelRepository;
    }

    @Transactional
    public int createInkomendeLevering(InkomendeLevering inkomendeLevering) {
        return inkomendeLeveringRepository.createInkomendeLevering(inkomendeLevering);
    }

    public List<InkomendeLeveringsLijn> vindBestePad(List<InkomendeOnvolledigeLeveringslijnDTO> inkomendeLeveringslijnen) {
        var alleCombinaties = new ArrayList<List<List<Magazijnplaats>>>();
        var hoeveelhedenNodig = new HashMap<Long, Long>();
        for (var inkomendeLeveringslijn : inkomendeLeveringslijnen) {
            // Vind voor elke leveringslijn (artikel) alle mogelijke plaatsen
            var plaatsen = magazijnplaatsRepository.findPlaatsenByArtikelId(inkomendeLeveringslijn.artikelId());

            // Check voor elk artikel of er genoeg ruimte is in de plaatsen waar ze al staan
            hoeveelhedenNodig.put(inkomendeLeveringslijn.artikelId(), inkomendeLeveringslijn.aantalGoedgekeurd());
            while (aantalBeschikbaar(plaatsen) < hoeveelhedenNodig.get(inkomendeLeveringslijn.artikelId())) {
                // Als niet genoeg: nieuwe plaatsen voorzien
                createMagazijnplaats(inkomendeLeveringslijn.artikelId());
                plaatsen = magazijnplaatsRepository.findPlaatsenByArtikelId(inkomendeLeveringslijn.artikelId());
            }
            // Maak alle mogelijke combinaties per artikel
            var mogelijkeCombinaties = combinatiesPerArtikel(plaatsen);
            // Enkel combos houden die genoeg plaats hebben
            var combinaties = mogelijkeCombinaties.stream().filter(combo ->
                    !combo.isEmpty() && aantalBeschikbaar(combo) >= inkomendeLeveringslijn.aantalGoedgekeurd()).toList();
            alleCombinaties.add(combinaties);
        }
        // Maak alle combinaties van verschillende opties voor de verschillende artikels
        var allePaden = Lists.cartesianProduct(alleCombinaties);

        List<Magazijnplaats> bestePad = new ArrayList<>();
        var besteScore = Integer.MAX_VALUE;
        for (var pad : allePaden) {
            // Sorteer elke combo alfabetisch/volgens ophalen
            var gesorteerdPad = maakGesorteerdPad(pad);
            var score = berekenScore(gesorteerdPad);
            // Selecteer het beste pad
            if (score < besteScore) {
                besteScore = score;
                bestePad = gesorteerdPad;
            }
        }

        // Transformeer Lijst van magazijnplaatsen naar lijst van inkomendeLeveringLijnen
        var resultaatLeveringsLijnen = new ArrayList<InkomendeLeveringsLijn>();
        for (var plaats : bestePad) {
            var onvolledigeInkomendeLeveringsLijn = inkomendeLeveringslijnen.stream().filter(lijn -> lijn.artikelId() == plaats.getArtikelId()).findFirst().orElse(null);
            var aantalVoorDezeLijn = Math.min(hoeveelhedenNodig.get(plaats.getArtikelId()), plaats.getAantal());
            // Het aantal nodig van dit artikel wordt verminderd met het aantal dat in deze lijn wordt toegevoegd
            hoeveelhedenNodig.put(plaats.getArtikelId(), hoeveelhedenNodig.get(plaats.getArtikelId()) - aantalVoorDezeLijn);
            var inkomendeLeveringsLijn = new InkomendeLeveringsLijn(onvolledigeInkomendeLeveringsLijn.leveringId(),
                    onvolledigeInkomendeLeveringsLijn.artikelId(), aantalVoorDezeLijn, 0, plaats.getMagazijnPlaatsId());
            resultaatLeveringsLijnen.add(inkomendeLeveringsLijn);
        }
        return resultaatLeveringsLijnen;
    }

    public List<List<Magazijnplaats>> combinatiesPerArtikel(List<Magazijnplaats> plaatsen) {
        var result = new LinkedList<List<Magazijnplaats>>();
        if (plaatsen.size() == 1) {
            result.add(new LinkedList<>());
            result.add(plaatsen);
            return result;
        }
        var plaats = plaatsen.removeFirst();
        var combinaties = combinatiesPerArtikel(plaatsen);
        for (var combinatie : combinaties) {
            result.add(combinatie);
            // Optimalisatie nog mogelijk: check of er al genoeg plaats is
            var uitgebreideCombinatie = new LinkedList<>(combinatie);
            uitgebreideCombinatie.add(plaats);
            result.add(uitgebreideCombinatie);
        }
        return result;
    }

    private int aantalBeschikbaar(List<Magazijnplaats> plaatsen) {
        var maxAantal = artikelRepository.vindMaxAantalInMagazijnPLaats(plaatsen.getFirst().getArtikelId());
        return plaatsen.stream().mapToInt(plaats -> (int) (maxAantal - plaats.getAantal())).sum();
    }

    private void createMagazijnplaats(long artikelId) {
        // Zeer inefficient: probeert gewoon plaatsen tot er 1 vrij is.
        for (char rijInt = 'A'; rijInt <= 'A' + 26; rijInt++) {
            for (var rek = 1; rek <= 60; rek++) {
                if (magazijnplaatsRepository.createMagazijnplaats(artikelId, String.valueOf(rijInt), rek) != 0) {
                    return;
                }
            }
        }
    }

    private List<Magazijnplaats> maakGesorteerdPad(List<List<Magazijnplaats>> plaatsen) {
        var resultaatPad = new ArrayList<Magazijnplaats>();
        plaatsen.forEach(resultaatPad::addAll);
        resultaatPad.sort(Comparator.comparing(magazijnplaats -> magazijnplaats.getRij() + magazijnplaats.getRek()));
        return resultaatPad;
    }

    private int berekenScore(List<Magazijnplaats> gesorteerdePlaatsen) {
        var scoreMap = new HashMap<String, Long>();
        for (var plaats : gesorteerdePlaatsen) {
            scoreMap.put(plaats.getRij(), Math.max(scoreMap.getOrDefault(plaats.getRij(), 0L), plaats.getRek()));
        }
        return scoreMap.values().stream().mapToInt(Long::intValue).sum();
    }
}
