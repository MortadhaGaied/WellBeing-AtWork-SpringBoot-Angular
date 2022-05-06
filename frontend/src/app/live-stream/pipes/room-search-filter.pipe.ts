import { Pipe, PipeTransform } from '@angular/core';
import { Room } from 'src/app/chatroom/models/room';

@Pipe({
  name: 'roomSearchFilter',
})
export class RoomSearchFilterPipe implements PipeTransform {
  transform(rooms: Room[], searchValue: string): Room[] {
    if (!rooms || !searchValue) {
      return rooms;
    }
    return rooms.filter((room) =>
      room.roomName.toLowerCase().includes(searchValue.toLowerCase())
    );
  }
}
