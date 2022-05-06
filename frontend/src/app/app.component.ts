import { Component, OnInit } from '@angular/core';
import { FirebaseService } from './firebase/firebase.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'frontend';
  constructor(private firebaseService: FirebaseService) {}
  ngOnInit(): void {
    this.firebaseService.requestPermission();
    this.firebaseService.listen();
  }
}
