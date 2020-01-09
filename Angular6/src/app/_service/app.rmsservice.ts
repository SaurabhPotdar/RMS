import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class RmsService{
    
    /**
     * Method to log in
     * @param email 
     * @param password 
     */
    login(data:any) {
        console.log(data);
        return this.myhttp.post('http://localhost:9088/admin/login',data);
    }

    temp:any;
    email:String = 'saura@gmail.com';

    //Dependency Injection
    constructor(private myhttp:HttpClient){}

    registerCompany(data:any){
        //For RequestBody
        console.log(data);
        return this.myhttp.post('http://localhost:9088/company/register',data);
    }

    registerUser(data:any){
        console.log(data);
        return this.myhttp.post('http://localhost:9088/user/register',data);
    }

    addJob(data:any){
        return this.myhttp.post('http://localhost:9088/company/addjob?email='+this.email,data);
    }

    searchJob(id:number){
        return this.myhttp.get('http://localhost:9088/user/searchbyexperience?experience='+id);
    }

    uploadFile(data:any){
        return this.myhttp.post('http://localhost:9088/user/uploadFile',data);
    }


}