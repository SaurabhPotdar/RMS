import {Component, OnInit} from '@angular/core';
import {ProductService} from './_service/app.productservice';
import {Product} from './_model/app.product';
import {Router} from '@angular/router';
import { routerNgProbeToken } from '@angular/router/src/router_module';

@Component({
    selector: 'show',
    templateUrl: 'app.show.html'
})

export class ShowComponent implements OnInit{

    flag:boolean=true;

    products:Product[]=[];

    constructor(private service:ProductService, private router:Router){}       

    ngOnInit(){
        this.service.getAllData().subscribe((data:Product[])=>this.products=data);
    }

    updateProduct(prodId:number){
        this.router.navigate(['update',prodId]);
    }

    deleteProduct(id: any):any{
        this.service.deleteProduct(id).subscribe();
        location.reload();
    }

}