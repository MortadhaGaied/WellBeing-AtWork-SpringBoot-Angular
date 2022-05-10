import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import Swal from 'sweetalert2';
import { Room } from '../models/room';
import { ChatroomServiceService } from '../services/chatroom-service.service';

@Component({
  selector: 'app-room-user-list',
  templateUrl: './room-user-list.component.html',
  styleUrls: ['./room-user-list.component.css'],
})
export class RoomUserListComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Room,
    private service: ChatroomServiceService,
    private dialog: MatDialog,
    private router: Router,
    private tokenService: TokenStorageService
  ) {
    this.currentUser = tokenService.getUser();
  }
  currentUser: any;
  public searchValue: string = '';

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
      next: (users: any[]) => {
        users.splice(users.indexOf(this.currentUser.id));
        this.users = users;
      },
    });
  }

  checkIfUsreBannedFromRoom(chatroom: Room, user: any): boolean {
    let result = false;
    if (chatroom.bannList.includes(user.id)) {
      result = true;
    }
    return result;
  }

  public checkIfUserIsMember(chatroom: Room, user: any): boolean {
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

  onAddUserToChatRoom(user: any, roomId: number) {
    this.service
      .inviteUserToRoom(user.id, roomId, this.currentUser.id)
      .subscribe({
        next: () => {
          console.log('user invited to chatroom');
          console.log('userId' + user.id + 'room id : ' + roomId);
          Swal.fire({
            icon: 'success',
            title: 'user invited successfully',
            text: user.displayName + ' has been notified',
          });
          //console.log('user added to chatroom' + this.data.id);
          /**
         this method is to force the update on the parent component (in our case usersListComponent)
         */
          //this.data.users?.push(user);
        },
        error: (error) => {
          //console.log(error);
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
            footer: error.error.message,
          });
        },
      });
  }

  onDialogClosed(): void {
    window.location.reload();
    this.dialog.closeAll();
  }

  setHoverColor(event: MouseEvent) {
    const item: HTMLLIElement = <HTMLLIElement>event.target;

    switch (event.type) {
      case 'mouseenter':
        item.className = 'list-group-item active';
        break;
      case 'mouseleave':
        item.className = 'list-group-item';
        break;
      default:
        item.className = 'list-group-item';
        break;
    }
  }

  deleteUserFromRoom(user: any, roomId: number) {
    this.service.deleteUserFromRoom(user.id, roomId).subscribe({
      next: (data) => {
        console.log('deleted successfully ');
        this.users.splice(this.users.indexOf(user));
      },
    });
  }

  bannUserFromCHatROom(userID: number, roomId: number) {
    this.service.bannUserFromChatRoom(userID, roomId).subscribe({
      next: (data) =>
        Swal.fire({
          icon: 'success',
          title: 'user banned successfully',
          text: 'user got notified for his bann',
        }),
    });
  }
  unbannUserFromChatROom(userID: number, roomId: number) {
    this.service.unbannUserFromChatRoom(userID, roomId).subscribe({
      next: (data) =>
        Swal.fire({
          icon: 'success',
          title: 'user unbanned successfully',
          text: 'user got notified for his bann',
        }),
    });
  }
}
