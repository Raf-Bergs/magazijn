"use strict"
import {byId, toon, verberg} from "./util.js";

const leveringsId = localStorage.getItem("leveringsId");
getLeveringslijnen(leveringsId);


async function getLeveringslijnen(leveringsId) {
    // TODO: juiste url invullen
    // TODO: namen van DTO juist zetten
    const response = await fetch("TODO");
    if (response.ok) {
        // TODO: toon/verberg nodige velden
        const leveringslijnen = await response.json();
        verwerkLeveringslijnen(leveringslijnen);
    }
    if (response.status === 404) {
        // TODO: toon/verberg nodige velden
    } else {
        // TODO: toon/verberg nodige velden
    }
}

function verwerkLeveringslijnen(leverinslijnen) {
    const table = byId("leveringsBody");
    for (const leveringslijn of leverinslijnen) {
        const tr = table.insertRow();

        const checkboxCell = tr.insertCell();
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        // TODO: Wat is dit?
        checkbox.addEventListener("click", () => {
            sessionStorage.setItem("artikelId", leveringslijn.artikelId);
        });
        checkboxCell.appendChild(checkbox);

        // TODO: namen van DTO juist zetten
        tr.insertCell().textContent = leveringslijn.locatie;
        tr.insertCell().textContent = leveringslijn.naam;
        tr.insertCell().textContent = leveringslijn.aantal;
    }
}

// eventListener om wijzigingen in de leveringslijn-checkboxen na te kijken
byId("main").addEventListener("change", () => {
    const bestellijnCheckboxen = document.querySelectorAll('input[type="checkbox"]');

    // TODO: veldjes doorstrepen
    bestellijnCheckboxen.forEach(checkbox => {
        if (checkbox.checked) {
            //checkbox.nex
        }
    });

    const alleCheckboxenAangevinkt = Array.from(bestellijnCheckboxen).every(checkbox => checkbox.checked);
    byId("leveringVoltooidButton").disabled = !alleCheckboxenAangevinkt;
});

byId("leveringVoltooidButton").addEventListener("click", () => {
    window.location = "levering.html"
})