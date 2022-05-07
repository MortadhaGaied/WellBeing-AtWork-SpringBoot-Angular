import {FormControl,FormGroup,ValidatorFn,AbstractControl,ValidationErrors} from "@angular/forms";

export function restrictedWords(control: AbstractControl) {
  if (control.value.includes('foo')) {
    return { invalidWord: true };
  }
  return null;
}

