import { Component, OnInit } from '@angular/core';
import { RmsService } from './_service/app.rmsservice'
import { Router } from '@angular/router'

@Component({
    selector: 'userpage',
    templateUrl: 'app.user.html'
})
export class UserComponent implements OnInit {

    //searchData:any={location:"", jobTitle:""};
    searchData: any = { location: "All", designation: "All" };

    jobList: any[] = [];
    //job:any;

    show: boolean = false;

    size: number = 0;

    ngOnInit() {

        if (!(sessionStorage.getItem('userRole') === "user")) {
            this.router.navigate(['forbidden']);
        }

    }

    imageNumber: any;


    constructor(private service: RmsService, private router: Router) { }

    searchJob() {
        //Size and show in the subscribe code as it is an asynchronous operation.
        //If we write size after the asynchronous operation then it runs before list is fetched.
        //For debugging write console.log(jobList) also in async. 
        this.service.searchJob(this.searchData.location, this.searchData.designation).subscribe((data: any[]) => {
        this.jobList = data;
            this.size = this.jobList.length;
            this.show = true;
            this.imageNumber = Math.floor((Math.random() * 5) + 1);
            console.log(this.imageNumber);
            this.getThumbnail();
        }
            , error => {
                alert(error.error);
                this.show = false
            });
    }

    applyJob(data: any) {
        console.log(data);
        this.service.applyJob(data).subscribe(data => { alert("Applied Successfully"), location.reload() });
    }

    redirect(data: any) {
        this.service.setData(data);
        this.router.navigate(['/jobsingle']);
    }

    imageBlobUrl: string | null = null;

    getThumbnail(): void {
        this.service.getBlobThumbnail(sessionStorage.getItem("userId"))
            .subscribe((val) => {
                this.createImageFromBlob(val);
            },
                response => {
                    console.log("POST - getThumbnail - in error", response);
                },
                () => {
                    console.log("POST - getThumbnail - observable is now completed.");
                });
    }
    
    createImageFromBlob(image: Blob) {
        let reader = new FileReader();
        reader.addEventListener("load", () => {
            this.imageBlobUrl = reader.result.toString();
        }, false);
        if (image) {
            reader.readAsDataURL(image);
        }
    }

}