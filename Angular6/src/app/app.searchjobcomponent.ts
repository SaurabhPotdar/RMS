import {Component} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'
import { Job } from './_model/app.job';

@Component({
    selector: 'searchjob',
    templateUrl: 'app.searchjob.html'
})

export class SearchJobComponent  {
    
    
    
    
    model:any={};

    job:Job={"jobId": 0, "designation": "", "qualification": "", "experience":0, "salary":0, "location":""};

    searchId:number;
    flag:boolean=false;

    experienceStatus=false;

    constructor(private service:RmsService, private router:Router){} 
    
    searchJob(){
        this.service.searchJob(this.searchId).subscribe((job: Job)=>this.job=job,error=>alert(error.error));
        this.flag=true;
    }

}