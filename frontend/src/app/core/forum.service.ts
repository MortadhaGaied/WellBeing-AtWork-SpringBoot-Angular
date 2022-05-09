import { Injectable,EventEmitter } from '@angular/core';
import { HttpClient } from'@angular/common/http';
import { Post } from '../Models/Forum/Post';
import { Observable,Subject } from 'rxjs';
import { take } from 'rxjs/operators';
import { Reaction } from '../Models/Forum/Reaction';
import { Comment } from '../Models/Forum/Comment';
@Injectable({
  providedIn: 'root'
})
export class ForumService {

  post:Post;
  emojiList = ['LIKE', 'LOVE', 'HAHA', 'SAD', 'ANGRY'];
  constructor(private _http:HttpClient) { }
  $eventEmit = new EventEmitter();
  getAllPost():Observable<Post[]>{
    return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/all-post")
  }
  getAllPostWithImage():Observable<Post[]>{
    return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/all-postWithImage")
  }
  getAllPostWithoutImage():Observable<Post[]>{
    return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/all-postWithoutImage")
  }
  deleteBlogById(id:number){
    console.log("delete"+id);
    return this._http.delete("http://localhost:8081/Wellbeignatwork/Post/delete-post/"+id);
  }
  getPostReactions(id:number){
    return this._http.get<number>("http://localhost:8081/Wellbeignatwork/React/getNbrReactionByPost/"+id);
  }
  updateBlog(post:Post ){
    return this._http.put<Post>("http://localhost:8081/Wellbeignatwork/Post/update-post",post);
  }
  addBlog(post:Post,id:number):Observable<Post>{
    return this._http.post<Post>("http://localhost:8081/Wellbeignatwork/Post/add-post/"+id,post);
}
  getBlogById(idPost: number): Observable<Post> {
    return this._http.get<Post>("http://localhost:8081/Wellbeignatwork/Post/getPostById/"+idPost);
   }
  addReactionToPost(idPost:number,reaction:Reaction){
    return this._http.post<Reaction>("http://localhost:8081/Wellbeignatwork/React/addReaction/"+idPost+"/1",reaction);
  }
  addCommentToPost(idPost:number,comment:Comment){
    return this._http.post<Comment>("http://localhost:8081/Wellbeignatwork/Comment/add-comment/"+idPost+"/1",comment);
  }
  getNbrReactionByReactionTypeAndPost(idPost:number){
    return this._http.get<any>("http://localhost:8081/Wellbeignatwork/React/getNbrReactionByReactionTypeAndPost/"+idPost);
  }
    sendEventData(idBlog : number):any{
      
      this.getBlogById(idBlog).pipe(take(1)).subscribe(x=>{
        console.log(x.content);
        this.post=x;
        this.$eventEmit.emit(this.post);
        return x;
      });
      
    }
    getReactionCounterByPost(id:number,type:string):Observable<number>{
      let x=0;
      var subject=new Subject<number>();
      this.getBlogById(id).subscribe(p=>{
        p.reactions.forEach(r=>{
          if(r.reactionType==type){
            x++;
          }
        });
        subject.next(x);
      });
      return subject.asObservable(); 

    }
    getTrendingpost():Observable<Post[]>{
      return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/TrendingPost");
    }
    getPreferencepost(idUser:number):Observable<Post[]>{
      return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/groupByPreference/1");
    }
    reportPost(idPost:number){
      return this._http.get("http://localhost:8081/Wellbeignatwork/Post/reportPost/"+idPost);
    }
    downloadpdf(id:number):Observable<Blob>{
    
      return this._http.get("http://localhost:8081/Wellbeignatwork/Post/downloadArticle/"+id,{responseType:'blob'});
    }
}
