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

    logout(){
        console.log("In logout");
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("userRole");
        
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
        console.log(sessionStorage.getItem("userId"));
        return this.myhttp.post('http://localhost:9088/company/addjob?userId='+sessionStorage.getItem("userId"),data);
    }

    searchJob(data:any){
        return this.myhttp.get('http://localhost:9088/user/searchbylocation'+data);
    }

    uploadFile(data:any){
        return this.myhttp.post('http://localhost:9088/user/uploadFile',data);
    }


}