import {Component, OnInit} from '@angular/core';
import { RmsService } from './_service/app.rmsservice';
import { Router } from '@angular/router'
import { saveAs } from 'file-saver';

@Component({
    selector: 'usersapplied',
    templateUrl: 'app.usersapplied.html'
})

export class UsersAppliedComponent implements OnInit{
    
    constructor(private service:RmsService, private router:Router ){}

    jobList:any[] = [];

    userList:any[] = [];

    show:boolean=false;

    jobId:any; //Set in html when user selects from dropdown.

    size:any=0;
    
    ngOnInit(): void {
        //Navigate to forbidden if a user tries to access company page.
        if ((sessionStorage.getItem('userRole') === "user")) {
            this.router.navigate(['forbidden']);
        }
        this.service.getJobsPostedByCompany(sessionStorage.getItem("userId")).subscribe((data:any[])=>{
            this.jobList=data;
        },error=>alert(error.error));
    }

    viewUsersAppliedForJob(){
        this.service.viewUsersAppliedForJob(this.jobId).subscribe((data:any[])=>{
            this.userList=data;
            this.show=true;
            this.size=this.userList.length;
        },error=>alert(error.error));
    }

    download(userId:any){
        this.service.downloadFile(userId).subscribe((data)=>{
            var blob = new Blob([data],{type:'application/pdf'});
            var filename = 'Resume.pdf';
            saveAs(blob,filename);
        },error=>alert(error.error));
    }

}