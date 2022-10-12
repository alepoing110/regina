import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-escritorio',
  templateUrl: './escritorio.component.html',
  styleUrls: ['./escritorio.component.css'],
})
export class EscritorioComponent implements OnInit {

  nombreapp: string = environment.nombreapp;
  version: string = environment.version;
  gestion: number = new Date().getFullYear();

  ngOnInit() {
  }

}
