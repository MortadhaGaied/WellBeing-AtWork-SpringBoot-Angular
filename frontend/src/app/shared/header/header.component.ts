import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  userId: number;
  constructor(private router: Router) {}

  ngOnInit(): void {
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.userId = JSON.parse(localstorageData).id;
  }

  onNavigateToStreamClicked() {
    this.router.navigateByUrl('/live-stream/' + this.userId);
  }
}
