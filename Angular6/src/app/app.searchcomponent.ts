import {Component} from '@angular/core';
import { ProductService } from './_service/app.productservice';
import { Product } from './_model/app.product';

@Component({
    selector: 'search',
    templateUrl: 'app.search.html'
})

export class SearchComponent{
    
    searchId:number;
    product:Product={"prodId": 0, "prodName": "", "prodCost": 0};
    flag:boolean=false;

    constructor(private service:ProductService){}

    search(){
        this.service.searchProduct(this.searchId).subscribe((product: Product)=>this.product=product);
        this.flag=true;
    }

}