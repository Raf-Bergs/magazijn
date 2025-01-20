"use strict"
import {verberg, toon, byId, verwijderChildElementenVan, setText} from "./util.js";

let bestellijnCheckboxen = document.querySelectorAll('input[type="checkbox"]');


//Eventlistener voor order voltooid button.
//Button wordt geactiveerd wanneer alle checkbox gechecked zijn.
document.addEventListener("change", () => {
        for (let i = 0; i < bestellijnCheckboxen.length; i++) {
            if (!bestellijnCheckboxen[i].checked) {
                document.getElementById("orderVoltooidBtn").disabled = true;
                return
            }
        }
        document.getElementById("orderVoltooidBtn").disabled = false;
    }
)
