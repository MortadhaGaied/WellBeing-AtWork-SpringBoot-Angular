import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Post } from '../../Models/Forum/Post';
import { AddBlogComponent } from './add-blog/add-blog.component';
import { BlogsServiceService } from './blogs-service.service';
import { Router } from '@angular/router';
import { ImageService } from '../../image.service';
import { map } from 'rxjs/operators';
import { UpdateBlogComponent } from './update-blog/update-blog.component';
import {PageEvent} from '@angular/material/paginator';
@Component({
  selector: 'app-blogs',
  templateUrl: './blogs.component.html',
  styleUrls: ['./blogs.component.scss']
})
export class BlogsComponent implements OnInit {
  length:number;
  listPosts : Post[];
  updateId : number;
  blog:Post;
  search:string;
  @Output () eventEmmit= new EventEmitter(); 
  fileUploads?: any[];
  pageSize=5;
  pageEvent:PageEvent;
  constructor(private _service:BlogsServiceService,private matDialog:MatDialog, private router:Router,private uploadService: ImageService) { }
  ngOnInit(): void {
    console.log("hello");
    this._service.getAllPost().subscribe
    (res=>
      {
        
        this.listPosts=res.filter(function(value, index, arr){ 
          return value.content.length > 200;
      }).reverse();
      this.length=this.listPosts.length;
        }
        
        
        );
    
    this._service.getPostReactions(1).subscribe(res=>console.log(res));
    
  }
  deleteBlog(id:number){
    
    this._service.deleteBlogById(id).subscribe(()=>this._service.getAllPost().subscribe(res=>
      {
        
        this.listPosts=res.filter(function(value, index, arr){ 
          return value.content.length > 200;
      }).reverse();
      this.length=this.listPosts.length;
        }
        
        
        ));
  }
  getnbreactionByblog(id:number){
    this._service.getPostReactions(id).subscribe(res=>console.log(res));
  }
  updateBlog(idBlog:number){
    
    this.blog=this._service.sendEventData(idBlog);
    this.matDialog.open(UpdateBlogComponent
    );
  }
  onOpenDialogClick(){
    this.matDialog.open(AddBlogComponent);
    
  }
  Search(){
    if(this.search!=""){
      this.listPosts=this.listPosts.filter(res=>{
        if(res.subject.toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true;
        }
        else if(res.content.toLocaleLowerCase().match(this.search.toLocaleLowerCase())){
          return true;
        }
       
        else{
          return false;
        }
      });
    }
    else{
      this._service.getAllPost().subscribe
      (res=>
        {
          
          this.listPosts=res.filter(function(value, index, arr){ 
            return value.content.length > 200;
        }).reverse();
        this.length=this.listPosts.length;
          }
          
          
          );
    }
  }
  getBlogByTag(tag:string){
    this._service.getBlogByTag(tag).subscribe(res=>{
      console.log("taags\n"+res);
      this.listPosts=res;
      this.length=res.length;
    });
  }
  

}
