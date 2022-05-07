import { Component, OnInit } from '@angular/core';
import {AbstractControl,ValidationErrors , FormGroup,FormControl, FormBuilder, Validators, ValidatorFn } from '@angular/forms';
import {Router} from "@angular/router";
import { restrictedWords } from '../ValidateInput';


@Component({
  selector: 'app-survey',
  templateUrl: './survey.component.html',
  styleUrls: ['./survey.component.css']
})
export class SurveyComponent implements OnInit {

  starForm: FormGroup;


  constructor(  private fb: FormBuilder,private router:Router)
  { }

  ngOnInit(): void {

    this.starForm = this.fb.group({
      question1: ['',[Validators.required,restrictedWords] ],
      stars1: [null, Validators.required],
      question2: ['',[Validators.required,restrictedWords] ],
      stars2: [null, Validators.required],
      question3:['',[Validators.required,restrictedWords] ],
      stars3: [null, Validators.required],
      question4:['',[Validators.required,restrictedWords] ],

    },




    );



  }














/*
 restrictWords(words)
  {
    return (control:FormGroup) :{[key:string]:any} |null =>
    {
      if(!words) return null
      var invalidWords=words

        .map(w => control.value.includes(w) ?w : null )
        .filter(w  =>w !=null );

      return invalidWords && invalidWords.length > 0
      ? {'restrictedWords':invalidWords.join(',')}
      : null
    }
  }

  */




  onSubmitStarForm() {
    console.log('Star form submitted!');
    console.log(this.starForm.value);
    this.router.navigate([''])

  }



}
