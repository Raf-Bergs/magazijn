"use strict"
import {byId, toon, verberg} from "./util.js";

const leveringsId = localStorage.getItem("leveringsId");
const leveringslijnen = await getLeveringslijnen(leveringsId);
verwerkLeveringslijnen(leveringslijnen);

async function getLeveringslijnen(leveringsId) {
    // TODO: juiste url invullen
    // juiste url is leveringslijnen
    const response = await fetch("./leveringslijnen.json");
    if (response.ok) {
        verberg("storing");
        verberg("geenLeveringen");
        toon("leveringVoltooidButton");
        return  await response.json();
    } else if (response.status === 404) {
        verberg("storing");
        toon("geenLeveringen");
        verberg("leveringVoltooidButton");
    } else {
        toon("storing");
        verberg("geenLeveringen");
        verberg("leveringVoltooidButton");
    }
}

function verwerkLeveringslijnen(leveringslijnen) {
    const table = byId("leveringBody");
    for (const leveringslijn of leveringslijnen) {
        const tr = table.insertRow();

        // Checkbox
        const checkboxCell = tr.insertCell();
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkboxCell.appendChild(checkbox);
        checkbox.onchange = () => {
            if (checkbox.checked) {
                tr.classList.add("checked");
            } else {
                tr.classList.remove("checked");
            }
            localStorage.setItem(`leverCheckbox${leveringslijn.artikelId}`, `${checkbox.checked}`);
        };
        if (localStorage.getItem(`leverCheckbox${leveringslijn.artikelId}`) === "true") {
            checkbox.checked = true;
            tr.classList.add("checked");
        }


        // TODO: namen van DTO juist zetten
        // locatie
        tr.insertCell().textContent = leveringslijn.locatie;

        // artikel naam
        const naamCell = tr.insertCell();
        const link = document.createElement("a");
        link.href = "#";
        link.onclick = function () {
            localStorage.setItem("artikelId", leveringslijn.artikelId);
            window.open("artikel.html");
        };
        link.textContent = leveringslijn.naam;
        naamCell.appendChild(link);

        // aantal
        tr.insertCell().textContent = `x${leveringslijn.aantal}`;
    }
}

// eventListener om wijzigingen in de leveringslijn-checkboxen na te kijken
byId("main").addEventListener("change", () => {
    const bestellijnCheckboxen = document.querySelectorAll('input[type="checkbox"]');

    const alleCheckboxenAangevinkt = Array.from(bestellijnCheckboxen).every(checkbox => checkbox.checked);
    byId("leveringVoltooidButton").disabled = !alleCheckboxenAangevinkt;
});

byId("leveringVoltooidButton").addEventListener("click", async () => {
    // TODO: post request met levering voltooid naar juiste url
    await fetch(`leveringVoltooid/${leveringsId}`, {
        method: "POST",
        body: JSON.stringify(leveringslijnen),
        headers: {
            "Content-Type": "application/json"
        }
    });
    localStorage.clear();
    sessionStorage.clear();
    window.location = "levering.html"
})