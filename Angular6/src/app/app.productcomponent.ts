import {Component} from '@angular/core';
import {ProductService} from './_service/app.productservice';
import { Router } from '@angular/router'

@Component({
    selector: 'prod',
    templateUrl: 'app.product.html'
})

export class ProductComponent  {
    
    model:any={};

    constructor(private service:ProductService, private router:Router){}       

    addProduct():any{
        this.service.addProduct(this.model).subscribe((data)=>console.log(data));
        this.router.navigate(['/show']);
    }

}