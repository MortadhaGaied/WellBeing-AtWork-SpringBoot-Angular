import { Component, OnInit } from '@angular/core';
import { CollabType } from '../../services/collab-type';
import { CollabService } from '../../services/collab.service';

@Component({
  selector: 'app-new-collab',
  templateUrl: './new-collab.component.html',
  styleUrls: ['./new-collab.component.scss']
})
export class NewCollabComponent implements OnInit {
  collab: CollabType;
  constructor(private collabService: CollabService) { }

  ngOnInit(): void {
    this.collab = {
      name: 'string',
      description: 'string',
      phone: 1,
      email: 'string',
      date: new Date(),
      rate: 'string',
      town: 'string',
    }
    this.collabService.addCollab(this.collab).subscribe(res => console.log(res))

  }

}
