import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Survey} from "../Models/Evaluation/Survey";

@Injectable({
  providedIn: 'root'
})
export class SurveyService {
  constructor(private http:HttpClient) { }
  baseURL: string = 'http://localhost:8081/Wellbeignatwork/QVT/Survey';


  getSurvey(){
    return this.http.get<Survey[]>(this.baseURL);}

  addSurvey(survey: Survey){
    return this.http.post(this.baseURL, survey)}

  deleteSurvey(id:number){
    return this.http.delete(this.baseURL+id)}

  updateProduct(survey:Survey){
    return this.http.put(this.baseURL+survey.id, survey)
  }


}
