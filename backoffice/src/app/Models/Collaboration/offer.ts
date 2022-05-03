import { Collaboration } from "./collaboration";
import { Image } from './Image';
export class Offer {
     idOffer:number;
	 title:string;
	 descrption:string;
	 starDateOf:Date;
	 endDateOf:Date;
	 nplaces:number;
	 promotion:number;
	 percentage:Number
	 location:string;
	 prix:number;
	 rate :string;
     picture:Image;
     Happy:string;
	 collaboration :Collaboration;
}
