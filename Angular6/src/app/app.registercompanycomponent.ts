import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'registercompany',
    templateUrl: 'app.registercompany.html'
})

export class RegisterCompanyComponent implements OnInit{
    
    model:any={};
    file:any;

    errorMessage:String='';

    constructor(private service:RmsService, private router:Router){}
    
    ngOnInit(){

        //Navigate to forbidden if a user tries to access company page.
        if(!(sessionStorage.getItem('userRole') === "company")){
               this.router.navigate(['forbidden']);
           }

    }

    registerCompany():any{
        this.service.registerCompany(this.model).subscribe(data=>{alert("Registered Successfully");
        this.router.navigate(['login']);}
        ,error=>alert(error.error));
    }
    
}