import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { SlimLoadingBarModule } from 'ng2-slim-loading-bar';
import { ToastModule } from 'ng2-toastr/ng2-toastr';

/* CUSTOM MODULES */
import { MenusModule } from './components/menu/menus.module';
import { AppRoutingModule } from './app-routing.module';

/* CUSTOM COMPONENT AND SERVICES */
import { PageNotFoundComponent } from './shared';
import { NavbarComponent } from './shared';
import { AppComponent } from './app.component';

import { MenuService,
         MenuItemService } from './services';

/* NGRX */
import reducer from './reducers';

import { MenuActions,
         MenuItemActions } from './actions';

import { MenuEffects,
         MenuItemEffects } from './effects';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,  
    SlimLoadingBarModule.forRoot(),
    ToastModule.forRoot(),
    ToastModule,
    
    StoreModule.provideStore(reducer),
    
    EffectsModule.run(MenuEffects),
    EffectsModule.run(MenuItemEffects),
    
    MenusModule,
    
    AppRoutingModule
  ],
  providers: [MenuActions, MenuService,
              MenuItemActions, MenuItemService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
