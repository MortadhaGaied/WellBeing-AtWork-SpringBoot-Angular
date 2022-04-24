import { Component, OnInit,Inject } from '@angular/core';
import { BlogsServiceService } from '../blogs-service.service';
import { Post } from '../../../Models/Forum/Post';
import { FileUpload } from '../../../Models/file-upload.model';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';
import { ImageService } from '../../../image.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
  import {MatChipInputEvent} from '@angular/material/chips';

@Component({
  selector: 'app-update-blog',
  templateUrl: './update-blog.component.html',
  styleUrls: ['./update-blog.component.scss']
})
export class UpdateBlogComponent implements OnInit {
  blog:Post=new Post();
  url:string;
  selectedFiles?: FileList;
  currentFileUpload: FileUpload;
  percentage = 0;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] =[];
  
  constructor(@Inject(BlogsServiceService) private ev:BlogsServiceService,private _router:Router,private dialogRef:MatDialogRef<UpdateBlogComponent>,private uploadService: ImageService) { }

  ngOnInit(): void {
    this.ev.$eventEmit.subscribe((data)=> {
      this.blog=data;
      console.log(this.blog);
      this.tags=[...this.blog.tags];
    })
    
  }
  updateBlog (){
    this.blog.file=this.currentFileUpload.url;
    console.log(this.currentFileUpload.key);
    this.blog.tags=this.tags;
    console.log(this.blog);
    this.ev.updateBlog(this.blog).subscribe(()=>
    {
      console.log("modifier");
      this.dialogRef.close();
      this._router.navigateByUrl("/forum/blogs").then(()=>window.location.reload());
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

    // Add our fruit
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
