"use strict"
import {verberg, toon, byId, verwijderChildElementenVan, setText} from "./util.js";

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

        //order button in- of uitschakelen wanneer alle checkboxen aangevinkt staan
        for (let i = 0; i < bestellijnCheckboxen.length; i++) {
            if (!bestellijnCheckboxen[i].checked) {
                document.getElementById("orderVoltooidBtn").disabled = true;
                return
            }
        }
        document.getElementById("orderVoltooidBtn").disabled = false;
    }
)
