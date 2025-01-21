"use strict"
import {verberg, toon, byId, verwijderChildElementenVan, setText} from "./util.js";


const bestelling = {
    "bestelId": 1,
    "bestelDatum": "2025-01-06T10:00:00",
    "klantId": 1,
    "betaald": true,
    "betalingscode": "K20250106100000",
    "terugbetalingscode": null,
    "bestellingsStatusId": 5,
    "actiecodeGebruikt": false,
    "bedrijfsnaam": "VDAB",
    "btwNummer": "0887010362",
    "voornaam": "Ad",
    "familienaam": "Ministrateur",
    "facturatieAdresId": 1,
    "leveringsAdresId": 7
}
const bestellijnen = [
    {
        "artikelId": 2,
        "locatie": "A12",
        "naam": "emmer 12 l",
        "beschrijving": "huishoudemmer inhoud 12 l",
        "aantal": 2
    },
    {
        "artikelId": 80,
        "locatie": "A13",
        "naam": "Klopboormachine 1000W",
        "beschrijving": "klopboormachine 1000W blauw",
        "aantal": 2
    },
    {
        "artikelId": 89,
        "locatie": "A14",
        "naam": "Schuurmachine 600W",
        "beschrijving": "Schuurmachine 600W groen",
        "aantal": 1
    },
    {
        "artikelId": 118,
        "locatie": "A15",
        "naam": "Multitool 450W",
        "beschrijving": "Multitool 450W geel",
        "aantal": 2
    }
]



if (bestelling) {
    verberg("geenBestellingen")
    byId("bestelId").textContent = bestelling.bestelId;
    verwerkBestellijnen(bestellijnen)
} else{
    toon("geenBestellingen")
}

function verwerkBestellijnen(bestellijnen) {
    const tbody = byId("bestellingBody")
    for (const bestellijn of bestellijnen) {
        const tr = tbody.insertRow()
        const td = tr.insertCell()
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        const div = document.createElement("div");
        div.addEventListener('click', () => {
            sessionStorage.setItem("artikelId", bestellijn.artikelId)
            //window.location = "artikelinfo.html"
        })
        td.appendChild(checkbox);
        td.appendChild(div);
        const locatie = document.createElement("span");
        locatie.textContent = ` ${bestellijn.locatie} `
        const beschrijving = document.createElement("span");
        beschrijving.textContent = bestellijn.beschrijving
        const aantal = document.createElement("span");
        aantal.textContent = ` x ${bestellijn.aantal}`;
        div.appendChild(locatie);
        div.appendChild(beschrijving);
        div.appendChild(aantal);

    }

}


let bestellijnCheckboxen = document.querySelectorAll('input[type="checkbox"]');
//Eventlistener voor order voltooid button.
document.addEventListener("change", () => {

        //bestellijn doorstrepen wanneer ze aangevinkt is
        bestellijnCheckboxen.forEach(checkbox => {
            if (checkbox.checked) {
                checkbox.nextElementSibling.classList.add('checked');
            } else {

                checkbox.nextElementSibling.classList.remove('checked');
            }
        });

        //order voltooid button inschakelen wanneer alle checkboxen aangevinkt staan
        for (let i = 0; i < bestellijnCheckboxen.length; i++) {
            if (!bestellijnCheckboxen[i].checked) {
                document.getElementById("orderVoltooidBtn").disabled = true;
                return
            }
        }
        document.getElementById("orderVoltooidBtn").disabled = false;
    }
)
