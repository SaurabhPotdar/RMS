import {Component} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'home',
    templateUrl: 'app.home.html'
})

export class HomeComponent  {

    model:any={};
    data:any;
    file:any;

    status:boolean=true;
    emailStatus=false;
    passwordStatus=false;

    constructor(private service:RmsService, private router:Router){}

    login():any{
        this.service.login(this.model).subscribe((data)=>console.log(data)
        ,error=>alert(error.error));
    }

    validate():any{

        if(this.model.email!='' && this.model.email.match("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")){
            this.emailStatus=true;
            console.log("1");
        }
        if(this.model.password!='' && this.model.password.length>=5){
            this.passwordStatus=true;
            console.log("2");
        }
        if(this.emailStatus && this.passwordStatus){
            console.log("3");
            this.status=false;
        }
        else{
            this.status=true;
        }

    }

}