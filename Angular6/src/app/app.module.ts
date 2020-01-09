﻿import { NgModule }      from '@angular/core';
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
import { Error404Component } from './app.errorcomponent';
import { LoginComponent } from './app.logincomponent';
import { BlogComponent } from './app.blogcomponent';

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
    { path: '**', component:Error404Component}
];

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        RouterModule.forRoot(myroutes)
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
        LoginComponent,
        BlogComponent
		],
    providers: [ ],
    bootstrap: [AppComponent]
})

export class AppModule { }