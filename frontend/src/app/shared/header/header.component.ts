import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService, User } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  user: any;
  constructor(
    private tokenService: TokenStorageService,
    private userService: UserService,
    private router: Router
  ) {
    this.user = this.tokenService.getUser() || null;
    console.log(this.user);
    if (this.user === {}) {
      this.router.navigateByUrl('/login');
    }
  }

  /*
  ngOnInit(): void {
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.userId = JSON.parse(localstorageData).id;
  }
*/
  onNavigateToStreamClicked() {
    this.router.navigateByUrl('/live-stream/' + this.user.id);
  }
  currentUser!: User;

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe({
      next: (res: any) => (this.currentUser = res),
      error: (err: any) => {
        console.log(err);
      },
    });
  }

  onLogout() {
    this.tokenService.signOut();
    this.router.navigate(['/login']);
  }
}
