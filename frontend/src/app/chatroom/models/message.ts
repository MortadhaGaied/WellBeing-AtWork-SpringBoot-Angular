import { Room } from './room';

export interface Message {
  id?: number;
  content?: string;
  sendAt?: Date;
  sender: any;
  chatroom?: Room;
}
