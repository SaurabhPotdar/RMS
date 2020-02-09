import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router'
import { RmsService } from './_service/app.rmsservice';

@Component({
    selector: 'job',
    templateUrl: 'app.job.html'
})

export class JobComponent implements OnInit{
    
    model:any={};

    ngOnInit(){

        //Navigate to forbidden if a user tries to access company page.
        if(!(sessionStorage.getItem('userRole') === "company")){
               this.router.navigate(['forbidden']);
           }

    }

    constructor(private service:RmsService, private router:Router){}       

    addJob():any{
        console.log(sessionStorage.getItem("userId"));
        this.service.addJob(this.model).subscribe(data=>{alert("Added Successfully");
        location.reload();},error=>alert(error.error));
        //this.router.navigate(['/show']);
    }


}