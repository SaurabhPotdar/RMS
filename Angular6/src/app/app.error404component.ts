import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'errorcomponent',
    templateUrl: 'app.error404.html'
})

export class Error404Component  implements OnInit{
    
    url:string="/home";
    
    ngOnInit(): void {
        if(sessionStorage.getItem("userRole")==="user")
            this.url="/userpage";
        else if(sessionStorage.getItem("userRole")==="company")
            this.url="/companypage";
        else
            this.url="/home";
    }

}