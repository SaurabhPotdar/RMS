import {Component} from '@angular/core';
import {ProductService} from './_service/app.productservice';
import { Router } from '@angular/router'
import { RmsService } from './_service/app.rmsservice';

@Component({
    selector: 'job',
    templateUrl: 'app.job.html'
})

export class JobComponent  {
    
    model:any={};

    status:boolean=true;
    designationStatus=false;
    qualificationStatus=false;
    locationStatus=false;
    salaryStatus=false;
    experienceStatus=false;

    constructor(private service:RmsService, private router:Router){}       

    addJob():any{
        this.service.addJob(this.model).subscribe((data)=>alert("Added Successfully"),error=>alert(error.error));
        //this.router.navigate(['/show']);
    }

    validate():any{

        if(this.model.designation!='' && this.model.designation.match("[a-zA-Z0-9 ]")){
            this.designationStatus=true;
            console.log("1");
        }
        if(this.model.qualification!='' && this.model.qualification.match("[a-zA-Z]")){
            this.qualificationStatus=true;
            console.log("2");
        }
        if(this.model.location!='' && this.model.location.match("[a-zA-Z]")){
            this.locationStatus=true;
            console.log("3");
        }
        if(this.model.salary!='' && this.model.salary<=10000000){
            this.salaryStatus=true;
            console.log("4");
        }
        if(this.model.experience!='' && this.model.experience<=99){
            this.experienceStatus=true;
            console.log("5");
        }
        if(this.designationStatus && this.qualificationStatus && this.locationStatus && this.experienceStatus && this.salaryStatus){
            console.log("6");
            this.status=false;
        }
        
        else{
            this.status=true;
        }
            
        
    }

}