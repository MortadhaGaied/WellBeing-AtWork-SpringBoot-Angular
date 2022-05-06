import { Component, OnInit,Inject } from '@angular/core';
import { FormControl, FormGroup, Validators ,FormBuilder} from '@angular/forms';
import { Feedback } from '../../Models/event/Feedback';
import { EventService } from '../event-list/event.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {
  public form: FormGroup;
  feedback:Feedback=new Feedback();
  constructor(private fb: FormBuilder,private _service:EventService,private dialogRef:MatDialogRef<FeedbackComponent>,@Inject(MAT_DIALOG_DATA) public data:any ) {
    this.form = this.fb.group({
      feedback:['', Validators.required],
      rating: ['', Validators.required],
  }); }

  ngOnInit(): void {
  }
 
  onFormSubmit(): void {

    this.feedback.content=this.form.get("feedback")?.value;
    this.feedback.rate=this.form.get('rating')?.value;
    console.log(this.feedback);
    this._service.addFeedback(this.feedback,this.data.idev,3).subscribe(e=>console.log(e));
  }
  close(){
    this.dialogRef.close();
   

  }
}
