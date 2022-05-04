import { Collaboration } from "./collaboration";
import { Image } from './Image';
export class Offer {
     idOffer:number;
	 title:string;
	 descrption:string;
	 starDateOf:string;
	 endDateOf:string;
	 nplaces:number;
	 promotion:number;
	 percentage:Number;
	 localisation:string;
	 prix:number;
     picture?:Image;
     happy:string;
	 collaboration? :Collaboration;
}
