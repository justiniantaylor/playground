import { Component,
         OnInit,
         OnDestroy,
         ViewContainerRef} from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs/Subscription';
import { Router,
         ActivatedRoute } from '@angular/router';
import { SlimLoadingBarService } from 'ng2-slim-loading-bar';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

import { AppState } from '../../reducers';
import { MenuActions } from '../../actions';
import { Menu } from '../../models';

@Component({
  selector: 'menus',
  template: `
<div class="row">
    <div class="col">
        <h2>Menus
            <span class="float-right">
                <button class="btn btn-primary" (click)="addMenu()"><i class="fa fa-fw fa-plus"></i> Add Menu</button>
            </span>
        </h2>       
        <hr/>
    </div>
</div>

<div class="row">
    <div class="col">
        <menu-list [menus]="menus$ | async"
                    [selectedMenu]="menu$ | async"
                    (selectedEvent)="selectMenu($event)"
                    (deletedEvent)="deleteMenu($event)">
        </menu-list>       
    </div>

    <div class="col">
        <menu-detail [menu]="menu$ | async"
                      (savedEvent)="saveMenu($event)"
                      (cancelledEvent)="clearMenu($event)">
        </menu-detail>
    </div>
</div>      
`
})

export class MenusComponent implements OnInit, OnDestroy {

  private _idSubscription: Subscription;
  navigated = false;
  addingMenu = false;

  menus$: Observable<Array<Menu>>;
  menu$: Observable<Menu>;

  constructor( private _store: Store<AppState>,
               private _route: ActivatedRoute,
               private _router: Router,
               private _menuActions: MenuActions,
               private _slimLoadingBarService: SlimLoadingBarService,
               public toastr: ToastsManager,
               public vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit(): void {
    this.menus$ = this._store.select('menus');
    this.menus$.subscribe(b => this.completeLoading());
      
    this.menu$ = this._store.select('menu');
    this.menu$.subscribe(menu => 
        this.completeLoading();
        this._store.dispatch(this._menuItemsActions.loadMenuItems(menu.id));
    );

    this._store.dispatch(this._menuActions.loadMenus());

    this._idSubscription = this._route.params.select<string>('id')
        .subscribe(id => {
          if (id) {
            this.addingMenu = false;
            this.navigated = true;
            this._store.dispatch(this._menuActions.getMenu(id));
          } else {
            this.clearMenu();
          }
        });
  }

  ngOnDestroy() {
    this._idSubscription.unsubscribe();
  }

  clearMenu() {
    this.addingMenu = false;
    this.navigated = false;
    this._store.dispatch(this._menuActions.resetBlankMenu());
  }

  selectMenu(menu:Menu) {
    this.startLoading();
    this.addingMenu = false;
    this.navigated = true;
    this._store.dispatch(this._menuActions.getMenu(menu.id));
    this._router.navigate(['/menus',menu.id]);
  }

  addMenu() {
    this.startLoading();
    this.clearMenu();
    this.addingMenu = true;
  }

  saveMenu(menu: Menu) {
    this.startLoading();
    if (menu.id === 0 || menu.id == null) {
      this._store.dispatch(this._menuActions.addMenu(menu));
      this.clearMenu();
    } else {
      this._store.dispatch(this._menuActions.saveMenu(menu));
      this.toastr.success('You successfully saved the menu.', 'Saved!');
    }
  }

  deleteMenu(menu: Menu) {
    this.startLoading();
    this._store.dispatch(this._menuActions.deleteMenu(menu));
    this._router.navigate(['/menus']);
    this.toastr.success('You successfully deleted the menu.', 'Deleted!');
  }

  startLoading() {
    this._slimLoadingBarService.start(() => {
      console.log('Loading complete');
    });
  }

  stopLoading() {
    this._slimLoadingBarService.stop();
  }

  completeLoading() {
    this._slimLoadingBarService.complete();
  }
}
