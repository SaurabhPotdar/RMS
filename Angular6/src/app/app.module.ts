import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }  from './app.component';
import { FormsModule } from "@angular/forms";
import { ProductComponent } from "./app.productcomponent";
import { HttpClientModule } from '@angular/common/http'
import { ShowComponent } from './app.showcomponent';
import { AboutUsComponent } from './app.aboutuscomponent';
import { Routes, RouterModule } from '@angular/router';
import { SearchComponent } from './app.searchcomponent';
import { UpdateComponent } from './app.updatecomponent';
import { HomeComponent } from './app.homecomponent';
import { RegisterCompanyComponent } from './app.registercompanycomponent';
import { RegisterUserComponent } from './app.registerusercomponent';
import { JobComponent } from './app.jobcomponent';
import { SearchJobComponent } from './app.searchjobcomponent';
import { UploadComponent } from './app.uploadcomponenet';
import { Error404Component } from './app.error404component';
import { LoginComponent } from './app.logincomponent';
import { BlogComponent } from './app.blogcomponent';
import { CompanyComponent } from './app.companycomponent';
import { Error403Component } from './app.error403component';
import { UserComponent } from './app.usercomponent';
import { LogoutComponent } from './app.logoutcomponent';
import {NgxPaginationModule} from 'ngx-pagination';
import { BlogSingleComponent } from './app.blogsinglecomponent';
import { ContactComponent } from './app.contactcomponent';
import { PricingComponent } from './app.pricing';

//{path: 'show/:text', component: ShowComponent},
const myroutes:Routes= [
    {path: '', redirectTo:'home', pathMatch: 'full'},
    {path: 'registercompany', component: RegisterCompanyComponent},
    {path: 'about', component: AboutUsComponent},
    {path: 'add', component: ProductComponent},
    {path: 'show', component: ShowComponent},
    {path: 'search', component: SearchComponent},
    {path: 'update/:id', component: UpdateComponent},
    {path: 'home', component: HomeComponent},
    {path: 'registeruser', component: RegisterUserComponent},
    {path: 'addjob', component: JobComponent},
    {path: 'searchjob', component: SearchJobComponent},
    {path: 'upload', component: UploadComponent},
    { path: 'login', component:LoginComponent},
    { path: 'blog', component:BlogComponent},
    { path: 'userpage', component:UserComponent},
    { path: 'companypage', component:CompanyComponent},
    { path: 'forbidden', component:Error403Component},
    { path: 'logout', component:LogoutComponent},
    { path: 'blog-single', component:BlogSingleComponent},
    { path: 'contact', component:ContactComponent},
    { path: 'pricing', component:PricingComponent},
    { path: '**', component:Error404Component}
];

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        RouterModule.forRoot(myroutes),
        NgxPaginationModule
    ],
    declarations: [
        AppComponent,
        ProductComponent,
        ShowComponent,
        AboutUsComponent,
        SearchComponent,
        UpdateComponent,
        RegisterCompanyComponent,
        HomeComponent,
        RegisterUserComponent,
        JobComponent,
        SearchJobComponent,
        UploadComponent,
        Error404Component,
        Error403Component,
        LoginComponent,
        BlogComponent,
        CompanyComponent,
        UserComponent,
        LogoutComponent,
        BlogSingleComponent,
        ContactComponent,
        PricingComponent
        ],
    providers: [ ],
    bootstrap: [AppComponent]
})

export class AppModule { }