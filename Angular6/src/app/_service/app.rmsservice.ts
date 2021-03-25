import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})

export class RmsService {
    
    //http://13.233.118.2:9087/user/uploadFile
    //http://localhost:9087/user/uploadFile
    host:string = window.location.hostname;
    baseUrl:string= "http://" + this.host + ":9088/";
    uploadUrl:string= this.host + ':9088/user/uploadFile';

    //To pass job data between userpage and jobsingle page.
    private data={};
    setData(data) {
        this.data = data;
    }
    getData() {
        let temp = this.data;
        this.clearData();
        return temp;
    }
    clearData() {
        this.data = undefined;
    }

    /**
     * 
     * @param data - Form data containing email and password.
     */
    login(data: any) {
        console.log(data);
        return this.myhttp.post(this.baseUrl +'admin/login', data);
    }

    /**
     * Logout
     */
    logout() {
        console.log("In logout");
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("userRole");
    }

    temp: any;
    email: String = 'saura@gmail.com';

    //Dependency Injection
    constructor(private myhttp: HttpClient) { }

    /**
     * 
     * @param data 
     */
    registerCompany(data: any) {
        return this.myhttp.post(this.baseUrl+'company/register', data);
    }

    /**
     * 
     * @param data 
     */
    registerUser(data: any) {
        console.log(data);
        return this.myhttp.post(this.baseUrl+'user/register', data);
    }

    /**
     * 
     * @param data 
     */
    addJob(data: any) {
        console.log(sessionStorage.getItem("userId"));
        return this.myhttp.post(this.baseUrl+'company/addjob?userId=' + sessionStorage.getItem("userId"), data);
    }

    /**
     * 
     * @param location 
     * @param designation 
     */
    searchJob(location: any, designation: any) {
        return this.myhttp.get(this.baseUrl+'user/search?userId=' + sessionStorage.getItem("userId") + '&location=' + location + '&designation=' + designation);
    }

    /**
     * 
     * @param data - Request body for job
     */
    applyJob(data: any) {
        return this.myhttp.get(this.baseUrl+'user/applyforjob?jobId=' + data + '&userId=' + sessionStorage.getItem("userId"));
    }

    /**
     *  View all jobs applied by a user.
     */
    viewJobAppliedByUser(){
        return this.myhttp.get(this.baseUrl+'user/jobsapplied?userId='+ sessionStorage.getItem("userId"));
    }
    
    /**
     * 
     * @param data - Position in company, eg: Software Developer etc
     */
    searchUser(data:any){
        return this.myhttp.get(this.baseUrl+'company/searchbyposition?position='+data);
    }
    
    /**
     * Upload resume for user.
     * @param data - Form data containing file and userId
     */
    uploadFile(data: any) {
        return this.myhttp.post(this.baseUrl+'user/uploadFile', data);
    }

    /**
     * Download resume
     * @param data 
     */
    downloadFile(data:Observable<Blob>){
        return this.myhttp.get(this.baseUrl+'user/downloadFile?userId='+data,{'responseType':"blob"});
    }

    /**
     * Get list of jobs posted by company. Used for selecting jobId for viewUsersAppliedForJob()
     * @param companyId 
     */
    getJobsPostedByCompany(companyId:any){
        return this.myhttp.get(this.baseUrl+'company/getJobId?companyId='+companyId);
    }
    
    /**
     * View users which have applied for a job
     * @param jobId 
     */
    viewUsersAppliedForJob(jobId:any){
        return this.myhttp.get(this.baseUrl+'company/usersapplied?jobId='+jobId);
    }

    imageFetchUrl:any=this.baseUrl+'company/downloadFile?companyId=';
    
    /**
     * Get the logo of company from database.
     * @param companyId 
     */
    getBlobThumbnail(companyId:any): Observable<Blob> {
        const headers = new HttpHeaders({
          'Content-Type': 'image/png',
          'Accept': 'image/png'
        });
        return this.myhttp.get<Blob>(this.imageFetchUrl+companyId,
          { headers: headers, responseType: 'blob' as 'json' });
      }


}