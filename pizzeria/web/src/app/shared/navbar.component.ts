import { Component } from '@angular/core';

@Component({
    selector: 'navbar',
    template: `
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" routerLink="/" routerLinkActive="active">Pizzeria</a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a routerLink="/menu" routerLinkActive="active" class="nav-link">Menu</a>
      </li>
      <li class="nav-item">
        <a routerLink="/order" routerLinkActive="active" class="nav-link">Order</a>
      </li>
      <li class="nav-item">
        <a routerLink="/delivery" routerLinkActive="active" class="nav-link">Delivery</a>
      </li>
      <li class="nav-item">
        <a routerLink="/campaign" routerLinkActive="active" class="nav-link">Campaign</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="text" placeholder="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>
    `
})
export class NavbarComponent {}