import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'registeruser',
    templateUrl: 'app.registeruser.html'
})
export class RegisterUserComponent implements OnInit{

    user:any={};

    constructor(private service:RmsService, private router:Router){}
    
    ngOnInit(){

        //Navigate to forbidden if a company tries to access user page.
        if((sessionStorage.getItem('userRole') === "company")){
               this.router.navigate(['forbidden']);
           }

    }
    
    registerUser():any{
        console.log(this.user.position);
        this.service.registerUser(this.user).subscribe(data=>{alert("Registered Successfully");
        this.router.navigate(['login']);}
        ,error=>alert(error.error));
    }

}