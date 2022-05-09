import {Injectable} from "@angular/core";
import {Sujet} from "../Models/Evaluation/Sujet";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http:HttpClient) { }
  
  baseURL: string = 'http://localhost:8081/Wellbeignatwork/Vote/AffichageSujet';



  addSujet(sujet:Sujet)
  { return this.http
    .post("http://localhost:8081/Wellbeignatwork/Vote/addsujet", sujet);
   // return this.http.post("http://localhost:8081/Wellbeignatwork/Vote/addsujet", sujet)
  }
  deleteTask(sujetId: number) {
    return this.http.delete(`http://localhost:8081/Wellbeignatwork/Vote/delete/${sujetId}`);
  }


  getSujetById(sujetId: number): Observable<Sujet> {
    return this.http
      .get<Sujet>(`http://localhost:8081/Wellbeignatwork/Vote/getSujetById/${sujetId}`)
      .pipe(map((d: Sujet) => d));
  }



  updateTask(sujet: Sujet,sujetId: number): Observable<Sujet> {
    return this.http
      .put<Sujet>(`http://localhost:8081/Wellbeignatwork/Vote/Update/${sujetId}`, sujet)
      .pipe(map((d: Sujet) => d));
  }


  FindSujet(): Observable<Sujet[]>
  {
  return this.http.get<Sujet[]>(this.baseURL);
  }


  findName:string='http://localhost:8081/Wellbeignatwork/Vote/findNameUsersVoter';






  findNomdesUsersVoter(sujetId:number) :Observable<Sujet[]>
  {
    return this.http.get<Sujet[]>(`${this.findName}/${sujetId}`);

  }

  countYesandNo(sujetId:number) :Observable<Array<Sujet>>
  {
    return this.http.get<Sujet[]>("http://localhost:8081/Wellbeignatwork/Vote/YesAndNo/"+sujetId)
      .pipe(map((d: Array<Sujet>) => d));
  }











}
