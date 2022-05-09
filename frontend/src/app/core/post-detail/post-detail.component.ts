import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../../Models/Forum/Post';
import { ForumService } from '../forum.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {
  idPost:number;
p:Post=new Post();
  constructor(private _service:ForumService,private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.idPost=this.route.snapshot.params['id'];
    this._service.getBlogById(this.idPost).subscribe((p)=>
    {
      this.p=p;
      console.log(this.p);
    })
  }
  deletePost(id:number){
console.log("delete") ; }
  reportPost(id:number){
    this._service.reportPost(id).subscribe(p=>console.log(p));
  }

}
