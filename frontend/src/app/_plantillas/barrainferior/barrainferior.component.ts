import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-barrainferior',
  templateUrl: './barrainferior.component.html',
  styleUrls: ['./barrainferior.component.css']
})
export class BarrainferiorComponent implements OnInit {

  nombreapp: string = environment.nombreapp;
  version: string = environment.version;
  gestion: number = new Date().getFullYear();

  constructor() { }

  ngOnInit(): void {
  }

}
