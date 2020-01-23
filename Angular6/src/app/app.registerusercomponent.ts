import {Component} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'registeruser',
    templateUrl: 'app.registeruser.html'
})
export class RegisterUserComponent{

    user:any={};

    constructor(private service:RmsService, private router:Router){}       
    
    registerUser():any{
        console.log(this.user.position);
        this.service.registerUser(this.user).subscribe(data=>{alert("Registered Successfully");
        this.router.navigate(['login']);}
        ,error=>alert(error.error));
    }

}