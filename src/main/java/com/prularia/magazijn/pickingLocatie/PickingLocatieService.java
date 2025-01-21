package com.prularia.magazijn.pickingLocatie;

import com.prularia.magazijn.magazijnplaats.MagazijnPlaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PickingLocatieService {

    private final MagazijnPlaatsRepository magazijnPlaatsenRepository;

    public PickingLocatieService(MagazijnPlaatsRepository magazijnPlaatsenRepository) {
        this.magazijnPlaatsenRepository = magazijnPlaatsenRepository;
    }


    public List<PickingLocatie> getOptimizedPickingPath(long bestelId) {
        // Fetch locaties from repository
        List<PickingLocatie> locaties = magazijnPlaatsenRepository.findLocatiesVoorBestelling(bestelId);
        System.out.println("Fetched locaties for bestelId " + bestelId + ": " + locaties);

        if (locaties.isEmpty()) {
            return new ArrayList<>();
        }

        // Group locaties by Artikel ID
        List<PickingLocatie> optimizedPath = new ArrayList<>();
        PickingLocatie currentLocation = null;

        var groupedLocaties = locaties.stream()
                .collect(Collectors.groupingBy(PickingLocatie::getArtikelId));

        for (var entry : groupedLocaties.entrySet()) {
            List<PickingLocatie> artikelLocaties = entry.getValue();
            optimizedPath.addAll(calculateBestPath(artikelLocaties, currentLocation));
            currentLocation = optimizedPath.isEmpty() ? null : optimizedPath.get(optimizedPath.size() - 1);
        }

        return optimizedPath;
    }

    private List<PickingLocatie> calculateBestPath(List<PickingLocatie> locaties, PickingLocatie start) {
        List<PickingLocatie> bestPath = new ArrayList<>();

        // Sort locations by proximity to the starting point
        locaties.sort(Comparator.comparing(locatie -> calculateScore(start, locatie)));

        // Determine the total required quantity to pick
        long requiredQuantity = locaties.get(0).getAantalBesteld();

        for (PickingLocatie locatie : locaties) {
            if (requiredQuantity == 0) {
                break;
            }

            long pickable = Math.min(locatie.getVoorraadInPlaats(), requiredQuantity);
            requiredQuantity -= pickable;

            if (pickable > 0) {
                bestPath.add(createUpdatedPickingLocatie(locatie, pickable));
            }
        }

        return bestPath;
    }

    private PickingLocatie createUpdatedPickingLocatie(PickingLocatie locatie, long pickable) {
        return new PickingLocatie(
                locatie.getArtikelId(),
                locatie.getArtikelNaam(),
                locatie.getMagazijnPlaatsId(),
                locatie.getRij(),
                locatie.getRek(),
                locatie.getVoorraadInPlaats(),
                pickable
        );
    }

    private int calculateScore(PickingLocatie current, PickingLocatie next) {
        if (current == null) {
            return getRijAsInt(next.getRij()) + next.getRek();
        }
        int rijDistance = Math.abs(getRijAsInt(current.getRij()) - getRijAsInt(next.getRij()));
        int rekDistance = Math.abs(current.getRek() - next.getRek());
        return rijDistance + rekDistance;
    }

    private int getRijAsInt(char rij) {
        return rij - 'A' + 1;
    }
}
