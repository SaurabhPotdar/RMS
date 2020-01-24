import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'
import { Job } from './_model/app.job';

@Component({
    selector: 'userpage',
    templateUrl: 'app.user.html'
})
export class UserComponent implements OnInit{

    //searchData:any={location:"", jobTitle:""};
    searchData:any={location:"All",designation:"All"};

    jobList:any[] = [];
    //job:any;

    show:boolean=false;

    size:number=0;

    ngOnInit(){

        if(!(sessionStorage.getItem('userRole') === "user")){
               this.router.navigate(['forbidden']);
           }

    }

    constructor(private service:RmsService, private router:Router){}
    
    searchJob(){
        //Size and show in the subscribe code as it is an asynchronous operation.
        //If we write size after the asynchronous operation then it runs before list is fetched.
        //For debugging write console.log(jobList) also in async. 
        this.service.searchJob(this.searchData.location, this.searchData.designation).subscribe((data:any[])=>{this.jobList=data;
        this.size=this.jobList.length;
        this.show=true;},error=>{alert(error.error);
        this.show=false});
    }

    applyJob(data:any){
        console.log(data);
        this.service.applyJob(data).subscribe(data=>{alert("Applied Successfully"),location.reload()});
    }

    redirect(data:any){
        this.service.setData(data);
        this.router.navigate(['/jobsingle']);
    }

}