import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'userpage',
    templateUrl: 'app.user.html'
})
export class UserComponent implements OnInit{

    //searchData:any={location:"", jobTitle:""};
    searchData:any={location:"",designation:""};

    jobList:any[] = [];

    ngOnInit(){

        if(!(sessionStorage.getItem('userRole') === "user")){
               this.router.navigate(['forbidden']);
           }

    }

    constructor(private service:RmsService, private router:Router){}
    
    searchJob(){
        const formData = new FormData();
        formData.append("location",this.searchData.location);
        formData.append("userId",sessionStorage.getItem("userId"));
        console.log(this.searchData.location);
        this.service.searchJob(formData).subscribe((data:any[])=>this.jobList=data,error=>alert(error.error));
    }

}