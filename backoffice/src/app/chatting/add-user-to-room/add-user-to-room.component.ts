import { Component, Inject, OnInit } from "@angular/core";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-add-user-to-room",
  templateUrl: "./add-user-to-room.component.html",
  styleUrls: ["./add-user-to-room.component.scss"],
})
export class AddUserToRoomComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Chatroom,
    private service: ChatroomService,
    private dialog: MatDialog
  ) {}

  public searchValue: string = "";

  public users: any[] = [];
  isLoading: boolean = false;

  ngOnInit(): void {
    this.fetchUsers();
    console.log(this.data);
  }

  checkUserInChatRoom(user: any): boolean {
    let result = false;
    if (this.users.includes(user)) result = true;

    return result;
  }

  fetchUsers(): void {
    this.service.getAllUsers().subscribe({
      next: (users) => (this.users = users),
      error: (error) => {
        this.isLoading = false;
        window.alert(error.message);
      },
      complete: () => (this.isLoading = false),
    });
  }

  onAddUserToChatRoom(user: any) {
    this.service.addUserToChatRoom(this.data.id, user.id).subscribe({
      next: () => console.log("user added to chatroom" + this.data.id),
      error: (error) => window.alert(error.message),
    });
  }

  setHoverColor(event: MouseEvent) {
    const item: HTMLLIElement = <HTMLLIElement>event.target;

    switch (event.type) {
      case "mouseenter":
        item.className = "list-group-item active";
        break;
      case "mouseleave":
        item.className = "list-group-item";
        break;

      default:
        item.className = "list-group-item";
        break;
    }
  }
}
