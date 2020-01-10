import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'companypage',
    template:``
})

export class CompanyComponent implements OnInit{

    ngOnInit(){

        if(!(sessionStorage.getItem('userRole') === "company")){
               this.router.navigate(['forbidden']);
           }

    }

    constructor(private service:RmsService, private router:Router){  }

}