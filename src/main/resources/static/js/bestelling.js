"use strict";

import {byId, toon, verberg} from "./util.js";

document.addEventListener("DOMContentLoaded", () => {
    let timeoutId;

    // Eventlistener for "Nieuwe bestelling ophalen" button
    byId("nieuweBestellingBtn").addEventListener("click", async () => {

        // Button 5 seconden uitschakelen om spamming tegen te gaan
        byId("nieuweBestellingBtn").disabled = true;
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            byId("nieuweBestellingBtn").disabled = false;
        }, 5000);

        await getBestelling();

    });

    // Fetch nieuwe bestelling
    async function getBestelling() {

        const response = await fetch("bestellingen/findBestelling");

        if (response.ok) {
            verberg("storing");
            verberg("geenBestellingen");
            verberg("nieuweBestellingBtn");
            toon("orderVoltooidBtn");

            const bestelId = await response.json();
            byId("bestelId").textContent = bestelId;
            localStorage.setItem("bestelId", JSON.stringify(bestelId));

            await getBestellijnen(bestelId);
            return
        }
        if (response.status === 404) {
            toon("geenBestellingen");
            toon("nieuweBestellingBtn");
            verberg("orderVoltooidBtn");
            byId("bestelId").textContent = "";

            // verberg "geenBestellingen" bericht na 8 seconden
            setTimeout(() => verberg("geenBestellingen"), 8000);
        } else {
            throw new Error("Unexpected response status: " + response.status);
        }

    }

    // Alle errors verbergen
    function verbergAlles() {
        verberg("geenBestellingen");
        verberg("nieuweBestellingBtn");
        verberg("orderVoltooidBtn");
    }

    // Fetch en verwerk bestellijnen
    async function getBestellijnen(bestelId) {
        const response = await fetch(`bestelling/${bestelId}`);
        if (response.ok) {
            const bestellijnen = await response.json();
            verwerkBestellijnen(bestellijnen);
        } else {
            throw new Error("Failed to fetch bestellijnen");
        }
    }

    // verwerk en bestellijnen tonen
    function verwerkBestellijnen(bestellijnen) {
        const tbody = byId("bestellingBody");
        tbody.innerHTML = "";

        bestellijnen.forEach((bestellijn, index) => {
            const tr = tbody.insertRow();

            const td = tr.insertCell();
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.id = `checkbox-${index}`;

            const div = document.createElement("div");
            div.addEventListener("click", () => {
                sessionStorage.setItem("artikelId", bestellijn.artikelId);
                window.location = "detail.html";
            });

            td.appendChild(checkbox);
            td.appendChild(div);

            const locatie = document.createElement("span");
            locatie.textContent = ` ${bestellijn.locatie}, `;

            const beschrijving = document.createElement("span");
            beschrijving.textContent = bestellijn.naam;

            const aantal = document.createElement("span");
            aantal.textContent = `, x${bestellijn.aantal}`;

            div.appendChild(locatie);
            div.appendChild(beschrijving);
            div.appendChild(aantal);

            // het laden van checkbox uit localstorage
            const savedState = localStorage.getItem(`checkbox-${index}`);
            checkbox.checked = savedState === "true";
        });

        let jsonData = [];
        for (const bestellijn of bestellijnen) {
            let obj = {
                "artikelId": bestellijn.artikelId,
                "aantal": bestellijn.aantal,
                "magazijnPlaats": `${bestellijn.locatie}`
            }
            jsonData.push(obj);
        }


        //  "Order Voltooid" button click
        byId("orderVoltooidBtn").addEventListener("click", async () => {
            byId("orderVoltooidBtn").disabled = true;
            byId("bestellingBody").innerHTML = "";
            const response = await fetch(`bestellingen/${localStorage.getItem("bestelId")}`, {
                method: "POST",
                headers: {'Content-Type': "application/json"},
                body:JSON.stringify(jsonData)
            })
            if(response.ok) {
                console.log("goed");
            }else{
                console.log("error");
            }
            localStorage.clear();
            await getBestelling();

        });
    }

    // Evenlistener for checkbox
    byId("main").addEventListener("change", () => {
        const checkboxes = document.querySelectorAll("input[type='checkbox']");

        checkboxes.forEach((checkbox) => {
            const checkboxId = checkbox.id;

            if (checkbox.checked) {
                checkbox.nextElementSibling?.classList.add("checked");
                localStorage.setItem(checkboxId, "true");
            } else {
                checkbox.nextElementSibling?.classList.remove("checked");
                localStorage.setItem(checkboxId, "false");
            }
        });

        const allChecked = Array.from(checkboxes).every((cb) => cb.checked);
        byId("orderVoltooidBtn").disabled = !allChecked;
    });


    // bij het laden van pagina, checken of bestelId bestaat in localstorage
    (async () => {

        const storedBestelId = localStorage.getItem("bestelId");
        if (storedBestelId) {
            await getBestelling();
            const checkboxes = document.querySelectorAll("input[type='checkbox']");
            const allChecked = Array.from(checkboxes).every((cb) => cb.checked);
            checkboxes.forEach((checkbox) => {
                if (checkbox.checked) {
                    checkbox.nextElementSibling?.classList.add("checked");
                }
            })
            byId("orderVoltooidBtn").disabled = !allChecked;
        }
    })();
});
