import { Component, Inject, OnInit } from '@angular/core';
import { CollabService } from '../../services/collab.service';
import { CollabType } from '../../services/collab-type';

@Component({
  selector: 'app-show-collabs',
  templateUrl: './show-collabs.component.html',
  styleUrls: ['./show-collabs.component.scss']
})
export class ShowCollabsComponent implements OnInit {

  constructor(private collabService: CollabService) { }

  ngOnInit(): void {
     this.collabService
      .getAllCollab()
      .subscribe((collabs: CollabType[]) => console.log(collabs))
   }

}
