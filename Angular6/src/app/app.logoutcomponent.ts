import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'logout',
    templateUrl: 'app.logout.html'
})

export class LogoutComponent{

    constructor(private service:RmsService, private router:Router){  }

    logout(){
        console.log(sessionStorage.getItem("userId"));
        console.log(sessionStorage.getItem("userRole"));
        this.service.logout();
        console.log(sessionStorage.getItem("userId"));
        console.log(sessionStorage.getItem("userRole"));
        this.router.navigate(['home']);
    }

}
