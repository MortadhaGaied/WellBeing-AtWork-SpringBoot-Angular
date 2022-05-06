export interface Room {
  id: number;
  roomName: string;
  //this key helps creating unique rooms for one to one chatting
  uniqueKey?: string;
  MaxBadWords?: number;
  capacity?: any;
  averageResponseTime?: string;
  users?: any[];
  messages?: any[];
  visible?: boolean;
  status?: string;
  cap?: string;
  image: string;
}
