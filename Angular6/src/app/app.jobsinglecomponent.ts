import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router'
import { RmsService } from './_service/app.rmsservice';
import { error } from 'util';

@Component({
    selector: 'jobsingle',
    templateUrl: 'app.jobsingle.html'
})

export class JobSingleComponent  implements OnInit{

    job:any={};
    
    constructor(private service:RmsService, private router:Router){} 
    
    /**
     * Get job data from userpage using service.
     */
    ngOnInit(): void {
        //Navigate to forbidden if a company tries to access user page.
        if((sessionStorage.getItem('userRole') === "company")){
            this.router.navigate(['forbidden']);
        }
        this.job=this.service.getData();
    }

    /**
     * Apply for job
     */
    applyJob(){
        this.service.applyJob(this.job.jobId).subscribe(data=>{alert("Applied Successfully"),this.router.navigate(['/userpage'])},error=>alert(error.error));
    }


}