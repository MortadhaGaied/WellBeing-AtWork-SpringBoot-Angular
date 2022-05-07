import { Component, OnInit } from "@angular/core";
import { User, UserService } from "../../services/auth-services/user.service";

@Component({
  selector: "app-user-index",
  templateUrl: "./user-index.component.html",
  styleUrls: ["./user-index.component.scss"],
})
export class UserIndexComponent implements OnInit {
  users: User[] = [];
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe({
      next: (res) => (this.users = res),
      error: (err) => console.log(err),
    });
  }

  delete() {}
}
