import { Component, OnInit,Inject } from '@angular/core';
import { Post } from '../../../Models/Forum/Post';
import { Router } from '@angular/router';
import { BlogsServiceService } from '../blogs-service.service';
export interface Fruit{
  name:string;
}
@Component({
  selector: 'app-add-blog',
  templateUrl: './add-blog.component.html',
  styleUrls: ['./add-blog.component.scss']
})

export class AddBlogComponent implements OnInit {
  tags=['CELEBRITY', 'ANIMAL', 'PEOPLE', 'CURIOSITY', 'SCIENCE', 'FUNNY', 'NATURE', 'INTERESTING_PLACE', 'ART', 'MOVIE', 'FASHION', 'SALE',
  'MUSIC', 'CULTURE', 'SPORT'];
  constructor(@Inject(BlogsServiceService) private ev:BlogsServiceService, private _router:Router) { }
  blog:Post=new Post();
  ngOnInit(): void {
    this.ev.$eventEmit.subscribe((data)=> {
      console.log("aaaaaaa");
      this.blog=data;
    })
  }
  addBlog (){
    this.ev.addBlog(this.blog).subscribe(()=>this._router.navigateByUrl("/forum/"));
  }

}
