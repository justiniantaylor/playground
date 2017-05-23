import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,
         ReactiveFormsModule } from '@angular/forms';
import { Select2Module } from 'ng2-select2';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { MenusRoutingModule} from './menus-routing.module';

import { MenusComponent } from './menus.component';
import { MenuDetailComponent } from './menu-detail.component';
import { MenuListComponent } from './menu-list.component';
import { MenuItemDetailComponent } from './item/menu-item-detail.component';
import { MenuItemListComponent } from './item/menu-item-list.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Select2Module,
        NgbModule,

        MenusRoutingModule
    ],
    declarations: [
        MenuDetailComponent,
        MenuListComponent,
        MenuItemDetailComponent,
        MenuItemListComponent,
        MenusComponent
    ],
    exports: [
        MenuDetailComponent,
        MenuListComponent,
        MenusComponent
    ],
    providers: []
})
export class MenusModule { }
