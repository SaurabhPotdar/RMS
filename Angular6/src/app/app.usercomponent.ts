import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'userpage',
    templateUrl: 'app.user.html'
})
export class UserComponent implements OnInit{
    
    ngOnInit(){

        if(!(sessionStorage.getItem('userRole') === "user")){
               this.router.navigate(['forbidden']);
           }

    }

    constructor(private service:RmsService, private router:Router){}       

}