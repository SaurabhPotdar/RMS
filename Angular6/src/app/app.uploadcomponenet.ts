import {Component} from '@angular/core';
import {RmsService} from './_service/app.rmsservice'
import { Router } from '@angular/router'
import { HttpErrorResponse, HttpClient } from '@angular/common/http';

@Component({
    selector: 'upload',
    templateUrl: 'app.upload.html'
})

export class UploadComponent  {

    title = 'Upload Flight Data';

    constructor(private service:RmsService, private router:Router, private myhttp:HttpClient){}
  
    myFiles:string [] = [];
    sMsg:string = '';
  
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
      frmData.append("userId","1");
      this.myhttp.post('http://localhost:9088/user/uploadFile',frmData).subscribe(
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
    }


}