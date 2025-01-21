package com.prularia.magazijn.bestelling;

public enum BestellingsStatus {
    LOPEND(1),
    BETAALD(2),
    GEANNULEERD(3),
    KLAARMAKEN(4),
    ONDERWEG(5),
    GELEVERD(6),
    VERLOREN(7),
    BESCHADIGD(8),
    RETOUR(9),
    RETOUR_IN_STOCK(10);

    private final int statusId;

    BestellingsStatus(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public static BestellingsStatus fromId(int id) {
        for (BestellingsStatus status : values()) {
            if (status.statusId == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Ongeldige BestellingsStatus ID: " + id);
    }
}
