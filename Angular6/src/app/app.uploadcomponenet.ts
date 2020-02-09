import {Component, OnInit} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'
import { HttpErrorResponse, HttpClient } from '@angular/common/http';

@Component({
    selector: 'upload',
    templateUrl: 'app.upload.html'
})

export class UploadComponent implements OnInit{

    title = 'Upload Flight Data';

    constructor(private service:RmsService, private router:Router, private myhttp:HttpClient){}
  
    myFiles:string [] = [];
    sMsg:string = '';

    ngOnInit(){

      //Navigate to forbidden if a company tries to access user page.
      if((sessionStorage.getItem('userRole') === "company")){
             this.router.navigate(['forbidden']);
         }

  }
  
     getFileDetails (e) {
      //console.log (e.target.files);
      for (var i = 0; i < e.target.files.length; i++) { 
        this.myFiles.push(e.target.files[i]);
      }
    }
  
    uploadFiles () {
      const frmData = new FormData();
      
      for (var i = 0; i < this.myFiles.length; i++) { 
        frmData.append("file", this.myFiles[i]);
      }
      frmData.append("userId",sessionStorage.getItem("userId"));
      this.service.uploadFile(frmData).subscribe(
        data => {
          // SHOW A MESSAGE RECEIVED FROM THE WEB API.
          this.sMsg = data as string;
          console.log (this.sMsg);
          alert("Uploaded successfully");
          location.reload();
        }
        ,
        (err: HttpErrorResponse) => {
          console.log (err.message);    // Show error, if any.
        }
      );

      // this.myhttp.post('http://ec2-3-81-98-128.compute-1.amazonaws.com:9088/user/uploadFile',frmData).subscribe(
      //   data => {
      //     // SHOW A MESSAGE RECEIVED FROM THE WEB API.
      //     this.sMsg = data as string;
      //     console.log (this.sMsg);
      //     alert("Uploaded successfully");
      //     location.reload();
      //   }
      //   ,
      //   (err: HttpErrorResponse) => {
      //     console.log (err.message);    // Show error, if any.
      //   }
      // );
    }


}