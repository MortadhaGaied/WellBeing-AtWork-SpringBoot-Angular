import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  rating = 0;
  starCount = 5;
  ratingArr: boolean[] = []; // true = solid star; false = empty star

  starCount2=5;
  ratingArr2:boolean[]=[];
  rating2=0;
  starCount3=5;
  ratingArr3:boolean[]=[];
  rating3=0;

  snackBarDuration = 1000; //bar show in one second

  response = [
    'You broke my heart!',
    'Really?',
    'We will do better next time.',
    'Glad you like it!',
    'Thank you so much!'
  ]

  constructor(private snackBar: MatSnackBar) {
    this.ratingArr = Array(this.starCount).fill(false);
    this.ratingArr2 = Array(this.starCount2).fill(false);
    this.ratingArr3 = Array(this.starCount3).fill(false);

  }

  ngOnInit(): void {
  }

  returnStar(i: number) {
    if (this.rating >= i + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  onClick(i: number) {
    this.rating = i + 1;
    this.snackBar.open(this.response[i], '', {
      duration: this.snackBarDuration,
      panelClass: ['snack-bar']
    });

  }

  onClick2(p:number)
  {
    this.rating2 = p + 1;
    this.snackBar.open(this.response[p], '', {
      duration: this.snackBarDuration,
      panelClass: ['snack-bar']
    });
  }

  returnStar2(p: number) {
    if (this.rating2 >= p + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  onClick3(x:number)
  {
    this.rating3= x + 1;
    this.snackBar.open(this.response[x], '', {
      duration: this.snackBarDuration,
      panelClass: ['snack-bar']
    });
  }

  returnStar3(x: number) {
    if (this.rating3 >= x + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }


}
