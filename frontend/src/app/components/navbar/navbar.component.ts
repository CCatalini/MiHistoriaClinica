import { Component, OnInit } from '@angular/core';
import {Router, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
    constructor(private router: Router) { }

    ngOnInit() : void{
    }

    isHomePage(): boolean {
        return this.router.url === '/' || this.router.url==='/patient/login' || this.router.url==='/patient/signup' || this.router.url==='/medic/login' || this.router.url==='/medic/signup';
    }

}
