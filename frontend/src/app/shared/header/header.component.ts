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
  constructor(
    private tokenService: TokenStorageService,
    private userService: UserService,
    private router: Router
  ) {}

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
