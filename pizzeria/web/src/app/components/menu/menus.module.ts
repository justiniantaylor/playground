import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,
         ReactiveFormsModule } from '@angular/forms';
import { Select2Module } from 'ng2-select2';

import { MenusRoutingModule} from './menus-routing.module';

import { MenusComponent } from './menus.component';
import { MenuDetailComponent } from './menu-detail.component';
import { MenuListComponent } from './menu-list.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    Select2Module,

    MenusRoutingModule
  ],
  declarations: [
    MenuDetailComponent,
    MenuListComponent,
    MenusComponent
  ],
  exports: [
      MenuDetailComponent,
      MenuListComponent,
      MenusComponent
  ],
  providers: [ ]
})
export class MenusModule { }
