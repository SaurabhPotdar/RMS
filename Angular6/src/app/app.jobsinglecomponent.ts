import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router'
import { RmsService } from './_service/app.rmsservice';

@Component({
    selector: 'jobsingle',
    templateUrl: 'app.jobsingle.html'
})

export class JobSingleComponent  implements OnInit{

    job:any={};
    
    model:any={};

    constructor(private service:RmsService, private router:Router){} 
    
    ngOnInit(): void {
        this.job=this.service.getData();
        console.log(this.job);
    }

    applyJob(){
        this.service.applyJob(this.job.jobId).subscribe(data=>{alert("Applied Successfully"),this.router.navigate(['/userpage'])});
    }


}