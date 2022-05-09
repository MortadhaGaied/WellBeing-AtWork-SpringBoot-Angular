import { Component, OnInit } from '@angular/core';
import { ForumService } from '../forum.service';

import { Post } from '../../Models/Forum/Post';
import { Reaction } from '../../Models/Forum/Reaction';
import { Comment } from '../../Models/Forum/Comment';
import { Timestamp } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { UpdatePostComponent } from '../update-post/update-post.component';
@Component({
  selector: 'app-share-post',
  templateUrl: './share-post.component.html',
  styleUrls: ['./share-post.component.css']
})

export class SharePostComponent implements OnInit {
  isShown:boolean=false;
  reaction:Reaction=new Reaction();
  comment:Comment=new Comment();
  listPostsWithImage : Post[];
  listPostsWithoutImage : Post[];
  listPosts: Post[];
  datenow:any=new Date();
  dateexemple:any=new Date('4/28/2022');
  startPage : number;
  paginationLimit:number; 
  showEmojis = false;
    emojiList: string[];

    reactionCount: any;

    subscription: any;
  constructor(private _service:ForumService,private matDialog:MatDialog) { }
 
  ngOnInit(): void {
    


    
    this._service.getAllPost().subscribe
    (res=>
      {
        this.listPosts=res.filter(function(value, index, arr){ 
          return value.content.length < 200;
      }).reverse();
        }
        
        
        );
        
  
  }
  
  

   
trending(){
  this._service.getTrendingpost().subscribe(res=>
    {
      console.log(res);
      this.listPosts=res.filter(function(value, index, arr){ 
        return (value.content.length < 200 && value.reported==false);
    });
      }
      
      
      );
}
deletePost(id:number){
  this._service.deleteBlogById(id).subscribe(()=>this._service.getAllPost().subscribe
  (res=>
    {
      this.listPosts=res.filter(function(value, index, arr){ 
        return value.content.length < 200;
    }).reverse();
      }
      
      
      )
    
    );
}
reportPost(id:number){
  this._service.reportPost(id).subscribe(p=>console.log(p));
}
openUpdate(id:number){
  this.matDialog.open(UpdatePostComponent,{
    restoreFocus:false,
    data:{'idpost':id}
  })
}
}
