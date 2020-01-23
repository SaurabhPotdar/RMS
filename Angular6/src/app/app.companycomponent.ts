import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'companypage',
    templateUrl:'app.company.html'
})

export class CompanyComponent implements OnInit{

    ngOnInit(){

        //Navigate to forbidden if a user tries to access company page.
        if(!(sessionStorage.getItem('userRole') === "company")){
               this.router.navigate(['forbidden']);
           }

    }

    constructor(private service:RmsService, private router:Router){  }

}