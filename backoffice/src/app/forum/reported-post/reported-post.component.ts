import { Component, OnInit } from '@angular/core';
import { Post } from '../../Models/Forum/Post';
import {PageEvent} from '@angular/material/paginator';
import { BlogsServiceService } from '../blogs/blogs-service.service';

@Component({
  selector: 'app-reported-post',
  templateUrl: './reported-post.component.html',
  styleUrls: ['./reported-post.component.scss']
})
export class ReportedPostComponent implements OnInit {
  listPosts : Post[];
  length:number;
  search:string;
  pageSize=5;
  pageEvent:PageEvent;
  constructor(private _service:BlogsServiceService) { }

  ngOnInit(): void {
    this._service.getAllPost().subscribe
    (res=>
      {
        
        this.listPosts=res.filter(function(value, index, arr){ 
          return (value.content.length <= 200 && value.reported==true);
      }).reverse();
      this.length=this.listPosts.length;
        }
        
        
        );
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
            return (value.content.length <= 200 && value.reported==true);
        }).reverse();
        this.length=this.listPosts.length;
          }
          
          
          );
    }
  }
  getBlogByTag(tag:string){
    this._service.getBlogByTag(tag).subscribe(res=>{
      console.log("taags\n"+res);
      this.listPosts=res.filter(function(value, index, arr){ 
        return (value.content.length <= 200 && value.reported==true);
    }).reverse();
      this.length=this.listPosts.length;
    });
  }
  deletePost(id:number){
    
    this._service.deleteBlogById(id).subscribe(()=>this._service.getAllPost().subscribe
    (res=>
      {
        
        this.listPosts=res.filter(function(value, index, arr){ 
          return (value.content.length <= 200 && value.reported==true);
      }).reverse();
      this.length=this.listPosts.length;
        }
        
        
        ));
  }
  clearPost(id:number){
    this._service.unreportPost(id).subscribe(()=>this._service.getAllPost().subscribe
    (res=>
      {
        
        this.listPosts=res.filter(function(value, index, arr){ 
          return (value.content.length <= 200 && value.reported==true);
      }).reverse();
      this.length=this.listPosts.length;
        }
        
        
        ));
  }

}
