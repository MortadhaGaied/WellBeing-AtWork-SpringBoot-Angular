import { Component, OnInit,Inject } from '@angular/core';
import { ForumService } from '../forum.service';
import { FileUpload } from '../../Models/file-upload.model'
import { Post } from '../../Models/Forum/Post';
import { ImageService } from '../../image.service';
import { Router } from '@angular/router';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
  import {MatChipInputEvent} from '@angular/material/chips';
@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  blog:Post=new Post();
  listPosts:Post[];
  selectedFiles?: FileList;
  currentFileUpload: FileUpload;
  percentage = 0;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = ['SPORT', 'ANIMAL'];
  constructor(@Inject(ForumService) private ev:ForumService,private uploadService: ImageService, private _router:Router) { }

  ngOnInit(): void {
    this.ev.getAllPost().subscribe(res=>this.listPosts=res);
  }
  addBlog (){
    
    if(this.currentFileUpload!=undefined){
      this.blog.file=this.currentFileUpload.url;
    }

    console.log(this.blog);
    
    this.ev.addBlog(this.blog,1).subscribe((p)=>
    {
    
      
      if(this.listPosts.some(e=>e.id==p.id)){
        this._router.navigateByUrl("/post-detail/"+p.id).then(()=>window.location.reload());
      }
      else{
        this._router.navigateByUrl("/").then(()=>window.location.reload());
      }
      
    })
  }
  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
   
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      this.selectedFiles = undefined;
      if (file) {
        this.currentFileUpload = new FileUpload(file);
        this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(
          percentage => {
            this.percentage = Math.round(percentage ? percentage : 0);
            
          },
          error => {
            console.log(error);
          }
          
        );
      }
    }
 
  }
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our tag
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }
}
