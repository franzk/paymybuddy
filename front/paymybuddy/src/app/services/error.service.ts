import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor() { }

  errorMessage(code: number) {
    switch(code) {
      case 401:
        return "Erreur : Vous n'êtes pas autorisé à accéder à cette page.";
      default:
        return "Oups ! Une erreur est survenue. Veuillez réessayer ultérieurement.";
    }
  }


}
