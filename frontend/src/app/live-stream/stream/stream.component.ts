import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-stream',
  templateUrl: './stream.component.html',
  styleUrls: ['./stream.component.css'],
})
export class StreamComponent implements OnInit {
  constructor(private router: Router, private route: ActivatedRoute) {}
  id: number;
  userId: number;
  meStreamer: boolean;
  ngOnInit(): void {
    this.route.params.subscribe((params) => (this.id = params['id']));
    console.log('path id : ' + this.id);
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.userId = JSON.parse(localstorageData).id;
    this.checkIfMeStreamer();
  }

  checkIfMeStreamer() {
    if (this.id == this.userId) this.meStreamer = true;
    else this.meStreamer = false;
  }
}
