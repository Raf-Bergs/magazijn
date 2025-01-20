package com.prularia.magazijn.bestelling;

import java.util.List;

public record AfgerondeBestellingDTO(long bestelId, List<AfgerondeBestellijnenDTO> bestellijnenDTOs) {
}
