import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-stream',
  templateUrl: './stream.component.html',
  styleUrls: ['./stream.component.css'],
})
export class StreamComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private tokenService: TokenStorageService
  ) {}
  id: number;
  userId: number;
  meStreamer: boolean;
  ngOnInit(): void {
    this.route.params.subscribe((params) => (this.id = params['id']));
    console.log('path id : ' + this.id);

    this.userId = this.tokenService.getUser().id;
    console.log(this.userId);
    this.checkIfMeStreamer();
  }

  checkIfMeStreamer() {
    if (this.id == this.userId) this.meStreamer = true;
    else this.meStreamer = false;
  }
}
