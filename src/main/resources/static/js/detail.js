"use strict"
import {setText, toon, verberg} from "./util.js";

document.addEventListener("DOMContentLoaded", () => {
    verberg("info", "nietGevonden", "storing");
    const artikelId = JSON.parse(localStorage.getItem("artikelId"))
    findById(artikelId).then(r => console.log(artikelId));
});

async function findById(id) {
    const dataSource = '../voorbeeldArtikelen.json'
    const response = await fetch(dataSource);
    if (response.ok) {
        const artikel = await response.json();
        console.log(id);
        toon("info")
        setText("artikelId", artikel.artikelId);
        setText("ean", artikel.ean);
        setText("naam", artikel.naam);
        setText("beschrijving", artikel.beschrijving);
        setText("prijs", artikel.prijs);
        setText("gewichtInGram", artikel.gewichtInGram);
        setText("bestelpeil", artikel.bestelpeil);
        setText("voorraad", artikel.voorraad);
        setText("minimumVoorraad", artikel.minimumVoorraad);
        setText("maximumVoorraad", artikel.maximumVoorraad);
        setText("levertijd", artikel.levertijd);
        setText("aantalBesteldLeverancier", artikel.aantalBesteldLeverancier);
        setText("maxAantalInMagazijnPLaats", artikel.maxAantalInMagazijnPLaats);
        setText("leveranciersId", artikel.leveranciersId);
    } else {
        if (response.status === 404) {
            toon("nietGevonden")
        } else {
            toon("storing")
        }
    }
}