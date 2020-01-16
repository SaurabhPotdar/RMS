import {Component} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'login',
    templateUrl: 'app.login.html'
})

export class LoginComponent  {

    email:any;
    password:any;
    loginDetails:any={};
    model:any={};
    constructor(private service:RmsService, private router:Router){  }

    login():any{
        const formData = new FormData();
        formData.append("email",this.loginDetails.email);
        formData.append("password",this.loginDetails.password);
        console.log(this.loginDetails.email);
        console.log(this.loginDetails.password);
        this.service.login(formData).subscribe(data=>{alert("Login successfull");
        this.model=data;
        if (this.model.role=='user') {
            sessionStorage.setItem('userId',this.model.userId);
            sessionStorage.setItem('userRole',this.model.role);
            console.log(this.model.userId);
            console.log(this.model.role);
            this.router.navigate(['userpage']);    
        } else {
            sessionStorage.setItem('userId',this.model.userId);
            sessionStorage.setItem('userRole',this.model.role);
            console.log(this.model.userId);
            console.log(this.model.role);
            this.router.navigate(['companypage']);
        }
        }
        ,error=>alert(error.error));
    }

}