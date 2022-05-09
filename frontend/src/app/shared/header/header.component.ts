import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { User, UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  userId: number;
  constructor(
    private tokenService: TokenStorageService,
    private userService: UserService,
    private router: Router
  ) {}

  /*
  ngOnInit(): void {
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.userId = JSON.parse(localstorageData).id;
  }
*/
  onNavigateToStreamClicked() {
    this.router.navigateByUrl('/live-stream/' + this.userId);

  }
  currentUser!: User;

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe({
      next: (res) => (this.currentUser = res),
      error: (err) => {
        console.log(err);
      },
    });
  }

  onLogout() {
    this.tokenService.signOut();
    this.router.navigate(['/login']);
  }
}
