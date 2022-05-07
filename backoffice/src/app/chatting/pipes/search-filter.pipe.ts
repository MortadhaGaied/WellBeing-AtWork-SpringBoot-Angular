import { Pipe, PipeTransform } from '@angular/core';
import { Chatroom } from '../chatroom';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(rooms:Chatroom[],searchValue:string): Chatroom[] {
    if(!rooms || !searchValue){
        return rooms;
    }
    return rooms.filter(room=>room.roomName.toLowerCase().includes(searchValue.toLowerCase()))
  }

}
