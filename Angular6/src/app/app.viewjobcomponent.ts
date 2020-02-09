import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'viewjob',
    templateUrl: 'app.viewjob.html'
})
export class ViewJobComponent implements OnInit{
    
    jobList:any[] = [];

    imageNumber:any;
    
    constructor(private service:RmsService, private router:Router){}
    
    ngOnInit(): void {
        this.service.viewJobAppliedByUser().subscribe((data:any[])=>{this.jobList=data;
        this.imageNumber=Math.floor((Math.random() * 5) + 1);
        console.log(this.jobList.length)}
        ,error=>alert(error.error));
    }

}