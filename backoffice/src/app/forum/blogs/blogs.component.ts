import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Post } from '../../Models/Forum/Post';
import { AddBlogComponent } from './add-blog/add-blog.component';
import { BlogsServiceService } from './blogs-service.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-blogs',
  templateUrl: './blogs.component.html',
  styleUrls: ['./blogs.component.scss']
})
export class BlogsComponent implements OnInit {

  listPosts : Post[];
  updateId : number;
  blog:Post;
  @Output () eventEmmit= new EventEmitter(); 
  
  constructor(private _service:BlogsServiceService,private matDialog:MatDialog, private router:Router) { }
  ngOnInit(): void {
    this._service.getAllPost().subscribe
    (res=>
      {
        console.log(res);
      this.listPosts=res
      }
    );
    this._service.getPostReactions(1).subscribe(res=>console.log(res));
    
  }
  deleteBlog(id:number){
    this._service.deleteBlogById(id).subscribe(()=>this._service.getAllPost().subscribe(res=>{this.listPosts=res}));
  }
  getnbreactionByblog(id:number){
    this._service.getPostReactions(id).subscribe(res=>console.log(res));
  }
  updateBlog(idBlog:number){
    
    this.blog=this._service.sendEventData(idBlog);
    this.matDialog.open(AddBlogComponent,{
      data:{
          subject:"aa",
          content:"bb"
      }
    });
  }
  onOpenDialogClick(){
    this.matDialog.open(AddBlogComponent);
  }

}
