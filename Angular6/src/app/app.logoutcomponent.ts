import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'logout',
    template: ``
})

export class LogoutComponent implements OnInit{
    
    ngOnInit(): void {
        this.service.logout();
        this.router.navigate(['home']);
    }

    constructor(private service:RmsService, private router:Router){  }


}
