import {Component} from '@angular/core';
import { Router } from '@angular/router'
import { RmsService } from './_service/app.rmsservice';

@Component({
    selector: 'job',
    templateUrl: 'app.job.html'
})

export class JobComponent  {
    
    model:any={};

    constructor(private service:RmsService, private router:Router){}       

    addJob():any{
        console.log(sessionStorage.getItem("userId"));
        this.service.addJob(this.model).subscribe((data)=>alert("Added Successfully"),error=>alert(error.error));
        //this.router.navigate(['/show']);
    }


}