"use strict"
import {byId, toon, verberg} from "./util.js";


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

let timeoutId;
//eventlistener voor "Nieuwe bestelling ophalen" button
byId("nieuweBestellingBtn").addEventListener("click", async () => {

    byId("nieuweBestellingBtn").disabled = true;
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
        byId("nieuweBestellingBtn").disabled = false;
    }, 8000); // Button weer inschakelen na 8 seconden
    await getBestelling()
})

//deze function wordt uitgevoerd wanneer magazijnier op "Nieuwe bestelling ophalen" klikt
async function getBestelling() {
    const response = await fetch("bestellingen/findBestelling")
    //wanneer er nieuwe bestellingen zijn, bestelling openen en bestelId op scherm tonen
    if (response.ok) {
        verberg("storing")
        verberg("geenBestellingen")
        verberg("nieuweBestellingBtn")
        toon("orderVoltooidBtn")
        byId("bestelId").textContent = await response.json();
        //verwerkBestellijnen functie komt hier
        verwerkBestellijnen(bestellijnen)
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

function verbergAlles(){
    verberg("geenBestellingen")
    verberg("nieuweBestellingBtn")
    verberg("orderVoltooidBtn")
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



//Eventlistener voor order voltooid button.
byId("main").addEventListener("change", () => {
    let bestellijnCheckboxen = document.querySelectorAll('input[type="checkbox"]');

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

byId("orderVoltooidBtn").addEventListener("click", async () => {
    console.log("clicked");
    byId("orderVoltooidBtn").disabled = true;
    byId("bestellingBody").innerHTML = "";
    await getBestelling()
})
