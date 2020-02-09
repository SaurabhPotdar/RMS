import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'
import { saveAs } from 'file-saver';


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

    searchData:any={designation:"All"};

    userList:any[] = [];
    
    show:boolean=false;

    size:number=0;

    searchUser(){
        this.service.searchUser(this.searchData.designation).subscribe((data:any[])=>{this.userList=data;
        this.size=this.userList.length;
        this.show=true;}
        ,error=>{alert(error.error);
        this.show=false});
    }

    download(userId:any){
        this.service.downloadFile(userId).subscribe((data)=>{
            var blob = new Blob([data],{type:'application/pdf'});
            var filename = 'Resume.pdf';
            saveAs(blob,filename);
        },error=>alert("No File uploaded by user"));
    }

}