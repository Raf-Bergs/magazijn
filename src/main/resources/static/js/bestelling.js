// Javascript is geen leuke programmeertaal. Maar wanneer het werkt, voelt dat goed :)
// Om dit leesbaar te maken heb ik zo veel mogelijk comments gebruikt, maar toch lijkt dit op spaghetti code
// (I promise you it's not spaghetti code it's just javascript :D)
"use strict"
import {byId, toon, verberg} from "./util.js";

let timeoutId;

// Eventlistener voor "Nieuwe bestelling ophalen" button
byId("nieuweBestellingBtn").addEventListener("click", async () => {
    // Als er geen nieuwe bestellingen zijn, button uitschakelen (5 seconden cooldown)
    byId("nieuweBestellingBtn").disabled = true;
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
        byId("nieuweBestellingBtn").disabled = false;
    }, 5000); // Button weer inschakelen na 5 seconden
    await getBestelling()
})

// Deze function wordt uitgevoerd wanneer magazijnier op "Nieuwe bestelling ophalen" klikt
async function getBestelling() {
    const response = await fetch("bestellingen/findBestelling")
    // Wanneer er nieuwe bestellingen zijn, wordt de bestelling geopend en bestelId op het scherm getoond
    if (response.ok) {
        verberg("storing")
        verberg("geenBestellingen")
        verberg("nieuweBestellingBtn")
        toon("orderVoltooidBtn")
        const bestelId= await response.json()
        byId("bestelId").textContent = bestelId
        await getBestellijnen(bestelId)
        return
    }
    // Wanneer er geen nieuwe bestellingen zijn
    if (response.status === 404) {
        toon("geenBestellingen")
        toon("nieuweBestellingBtn")
        verberg("orderVoltooidBtn")
        byId("bestelId").innerHTML="";
        // Na 8 seconden "geen bestelling" bericht laten verdwijnen
        setTimeout(()=> verberg("geenBestellingen"), 8000)
    } else{
        verbergAlles()
        toon("storing")
    }
}
//fouten verbergen function wanneer alles fout loopt :(
function verbergAlles(){
    verberg("geenBestellingen")
    verberg("nieuweBestellingBtn")
    verberg("orderVoltooidBtn")
}

async function getBestellijnen(bestelId){
    const response = await fetch(`bestelling/${bestelId}`)
    if (response.ok) {
        const bestellijnen = await response.json()
        verwerkBestellijnen(bestellijnen)
    }

}

// Verwerkt en toont de bestellijnen in de tabel
function verwerkBestellijnen(bestellijnen) {
    const tbody = byId("bestellingBody");

    bestellijnen.forEach(bestellijn => {
        const tr = tbody.insertRow();
        const td = tr.insertCell();

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";

        const div = document.createElement("div");
        div.addEventListener("click", () => {
            sessionStorage.setItem("artikelId", bestellijn.artikelId);
            window.open("test.html");
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
    });
}



//'Change' eventlistener voor wijzigingen in de bestellijn-checkboxen realtime nakijken
byId("main").addEventListener("change", () => {
    const bestellijnCheckboxen = document.querySelectorAll('input[type="checkbox"]');

    //  Bijbehorende tekst doorstrepen (met css) wanneer een checkbox aangevinkt is
    bestellijnCheckboxen.forEach(checkbox => {
        if (checkbox.checked) {
            checkbox.nextElementSibling.classList.add("checked");
        } else {
            checkbox.nextElementSibling.classList.remove("checked");
        }
    });

    // Controleren of alle checkboxen zijn aangevinkt
    const alleCheckboxenAangevinkt = Array.from(bestellijnCheckboxen).every(cb => cb.checked);
    byId("orderVoltooidBtn").disabled = !alleCheckboxenAangevinkt; // Order voltooid button wordt enkel aangezet wanneer alle checkoxen aangevinkt zijn
});


byId("orderVoltooidBtn").addEventListener("click", async () => {
    byId("orderVoltooidBtn").disabled = true;
    byId("bestellingBody").innerHTML = "";
    await getBestelling()
})
