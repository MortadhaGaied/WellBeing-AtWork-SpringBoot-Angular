import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { catchError, retry, throwError } from 'rxjs';
import Swal from 'sweetalert2';
import { Message } from '../models/message';
import { Room } from '../models/room';

@Injectable({
  providedIn: 'root',
})
export class ChatroomServiceService {
  URL: string =
    'http://localhost:8081/Wellbeignatwork/chatroom/all-public-rooms';
  constructor(private http: HttpClient) {}

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Something went wrong!',
        footer: errorMessage,
      });
    } else {
      // server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Something went wrong!',
        footer: errorMessage,
      });
    }
    console.log(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }

  getAllRooms() {
    return this.http
      .get<Room[]>(this.URL)
      .pipe(retry(1), catchError(this.handleError));
  }
  saveDiscussion(messages: Message[]) {
    return this.http
      .post('http://localhost:8089/Wellbeignatwork/chatroom/save-discussion/', {
        messages: messages,
      })
      .pipe(catchError(this.handleError));
  }

  getUsersByRoom(roomId: number) {
    console.log('getting users by chatroom');
    return this.http.get<any[]>(
      'http://localhost:8081/Wellbeignatwork/chatroom/getUsersByRoom/' + roomId
    );
  }
  getAllUsers() {
    return this.http.get<any>(
      'http://localhost:8081/Wellbeignatwork/api/get-all-users'
    );
  }
  inviteUserToRoom(userId: number, roomId: number, senderId: number) {
    return this.http.get(
      'http://localhost:8081/Wellbeignatwork/chatroom/inviteUserToRoom/' +
        roomId +
        '/' +
        userId +
        '/' +
        senderId
    );
  }

  acceptUserInvitation(redirectionLink: string, senderId: number) {
    return this.http
      .get(redirectionLink + '/' + senderId)
      .pipe(catchError(this.handleError));
  }

  deleteUserFromRoom(userId: number, roomId: number) {
    return this.http.get(
      'http://localhost:8081/Wellbeignatwork/chatroom/removeUserFromRoom/' +
        roomId +
        '/' +
        userId
    );
  }

  bannUserFromChatRoom(userId: number, roomId: number) {
    /**
     * TODO: implement userBann
     */
    return this.http.get(
      'http://localhost:8081/Wellbeignatwork/chatroom/bannUserFromRoom/' +
        userId +
        '/' +
        roomId
    );
  }

  retrieveDiscussionPerRoom(roomId: number) {
    return this.http.get<Message[]>(
      'http://localhost:8081/Wellbeignatwork/chatroom/retrieve-discussion/' +
        roomId
    );
  }

  unbannUserFromChatRoom(userID: number, roomID: number) {
    return this.http.get(
      'http://localhost:8081/Wellbeignatwork/chatroom/unbannUserFromRoom/' +
        userID +
        '/' +
        roomID
    );
    /**
     * TODO: implement user unbann
     */
  }
}
