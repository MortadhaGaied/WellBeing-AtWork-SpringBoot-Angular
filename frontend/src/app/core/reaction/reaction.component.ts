import { Component, OnInit, Input, OnDestroy  } from '@angular/core';
import { Post } from '../../Models/Forum/Post';
import { Reaction } from '../../Models/Forum/Reaction';
import { Comment } from '../../Models/Forum/Comment';
import { ForumService } from '../forum.service';

@Component({
  selector: 'app-reaction',
  templateUrl: './reaction.component.html',
  styleUrls: ['./reaction.component.scss']
})
export class ReactionComponent implements OnInit {
  @Input() itemId: number;
  isShown:boolean=true;

  showEmojis = false;
  emojiList: string[];
  post:Post=new Post();
  reactionCount: number[]=[0,0,0,0,0];
  userReaction: any;
  reaction:Reaction=new Reaction();

  startPage : number;
  paginationLimit:number; 
  comment:Comment=new Comment();
  constructor(private _service:ForumService) { }

  ngOnInit(): void {
    this.emojiList = this._service.emojiList
    console.log("reaaactionnnn: "+this.itemId);
    this._service.getBlogById(this.itemId).subscribe(p=>this.post=p);
    this._service.getReactionCounterByPost(this.itemId,this.emojiList[0]).subscribe(r=>this.reactionCount[0]=r);
    this._service.getReactionCounterByPost(this.itemId,this.emojiList[1]).subscribe(r=>this.reactionCount[1]=r);
    this._service.getReactionCounterByPost(this.itemId,this.emojiList[2]).subscribe(r=>this.reactionCount[2]=r);
    this._service.getReactionCounterByPost(this.itemId,this.emojiList[3]).subscribe(r=>this.reactionCount[3]=r);
    this._service.getReactionCounterByPost(this.itemId,this.emojiList[4]).subscribe(r=>this.reactionCount[4]=r);
    console.log("reaction cooooouuunttttt: "+this.reactionCount);
    this.startPage = 0;
    this.paginationLimit = 5;
  }
  react(i:number) {
    console.log(this.emojiList[i]);
    
    this.reaction.reactionType=this.emojiList[i];
    this._service.addReactionToPost(this.itemId,this.reaction).subscribe((data) =>{
      console.log(data);

      this.ngOnInit();

    });
  }

  toggleShow(b:boolean) {
    this.showEmojis = b;
  }
  toggleShowComment(){
    this.isShown=!this.isShown;
}


  emojiPath(emoji:string) {
   return `assets/reactions/${emoji}.svg`
  }
  addCommentToPost(id:number){
    console.log("comment this post: "+id);
    this._service.addCommentToPost(id,this.comment).subscribe((data) =>{
      console.log(data);

      this.ngOnInit();

    });
  }
  hasReactions(index:number) {
    return true;
  }
  showMoreItems()
  {
     this.paginationLimit = Number(this.paginationLimit) + 5;        
  }
  showLessItems()
  {
    this.paginationLimit = Number(this.paginationLimit) - 5;
  }
 

}
