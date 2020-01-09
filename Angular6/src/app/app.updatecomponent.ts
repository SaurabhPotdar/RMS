import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Product } from './_model/app.product';
import {ProductService} from './_service/app.productservice'

@Component({
    selector: 'update',
    templateUrl: 'app.update.html'
})

export class UpdateComponent implements OnInit{

    updateId:any;
    currProduct:Product={"prodId": 0, "prodName": "", "prodCost": 0};

    constructor(private route: ActivatedRoute, private service:ProductService){}

    ngOnInit(){
        this.updateId=this.route.snapshot.paramMap.get("id");
        this.service.searchProduct(this.updateId).subscribe((product: Product)=>this.currProduct=product);
    }

    updateProduct(){
        this.service.updateProduct(this.currProduct).subscribe();
    }

}