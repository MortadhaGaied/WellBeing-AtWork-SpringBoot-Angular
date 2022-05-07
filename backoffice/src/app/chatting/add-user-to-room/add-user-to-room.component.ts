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
  userMap: Map<any, boolean> = new Map();

  /**TODO:
   * 1) get all users and show them in the dropList
   * 2) set icons for each user based on if the user already a member of the chatroom or not
   * 3) implement a method to add the user to the chatroom if the user isnt a member yet
   */

  ngOnInit(): void {
    this.getAllUsers();
  }

  public getAllUsers(): void {
    this.service.getAllUsers().subscribe({
      next: (users: any[]) => (this.users = users),
    });
  }

  public checkIfUserIsMember(chatroom: Chatroom, user: any): boolean {
    let result = false; // initial value
    chatroom.users?.map((roomUser) => {
      if (roomUser.id === user.id) {
        result = true;
      }
    });
    /*console.log(
      "result from checking user existance in room " +
        chatroom.roomName +
        "is : " +
        result
    );*/
    return result;
  }

  onAddUserToChatRoom(user: any) {
    this.service.addUserToChatRoom(this.data.id, user.id).subscribe({
      next: () => {
        console.log("user added to chatroom" + this.data.id);
        /**
         this method is to force the update on the parent component (in our case usersListComponent)
         */
        this.data.users?.push(user);
      },
      error: (error) => window.alert(error.message),
    });
  }

  onDialogClosed(): void {
    window.location.reload();
    this.dialog.closeAll();
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
