import { Component,
         OnInit,
         OnDestroy,
         ViewContainerRef} from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs/Subscription';
import { Router,
         ActivatedRoute } from '@angular/router';

import { AppState } from '../../reducers';
import { MenuActions,
         MenuItemActions } from '../../actions';
import { Menu,
         MenuItem } from '../../models';

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

        <div *ngIf="(menu$.id | async) != null">

            <div *ngIf="(menuItem$ | async) == null">
                <menu-item-list [menuItems]="menuItems$ | async"
                                [selectedMenuItem]="menuItem$ | async"
                                (selectedEvent)="selectMenuItem($event)"
                                (deletedEvent)="deleteMenuItem($event)">
                </menu-item-list>       
            </div>
            <div *ngIf="(menuItem$ | async) != null">
                <menu-item-detail [menuItem]="menuItem$ | async"        
                                  (savedEvent)="saveMenuItem($event)"
                                  (cancelledEvent)="clearMenuItem($event)">
                </menu-item-detail>
            </div>
        </div>
    </div>
</div>      
`
})

export class MenusComponent implements OnInit, OnDestroy {

    private _idSubscription: Subscription;
    navigated = false;
    addingMenu = false;
    addingMenuItem = false;

    menus$: Observable<Array<Menu>>;
    menu$: Observable<Menu>;
    menuItems$: Observable<Array<MenuItem>>;
    menuItem$: Observable<MenuItem>;

    constructor(private _store: Store<AppState>,
                private _route: ActivatedRoute,
                private _router: Router,
                private _menuActions: MenuActions,
                private _menuItemActions: MenuItemActions) {}

    ngOnInit(): void {
        this.menus$ = this._store.select('menus');
        this.menus$.subscribe(b => this.completeLoading());

        this.menu$ = this._store.select('menu');
        this.menu$.subscribe(m => {
            if(m.id != null) {
                this._store.dispatch(this._menuItemActions.loadMenuItems(m.id));   
            } else {
                this._store.dispatch(this._menuItemActions.loadMenuItems(-1));   
            }       
        });

        this.menuItem$ = this._store.select('menuItem');
        this.menuItems$ = this._store.select('menuItems');

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
    
    clearMenuItem() {
        this.addingMenuItem = false;
        this._store.dispatch(this._menuItemActions.resetBlankMenuItem());
    }

    selectMenu(menu: Menu) {
        this.startLoading();
        this.addingMenu = false;
        this.navigated = true;
        this._store.dispatch(this._menuActions.getMenu(menu.id));
        this._router.navigate(['/menu', menu.id]);
    }
    
    selectMenuItem(menuItem: MenuItem) {
        this.addingMenuItem = false;
        this.navigated = true;
        this._store.dispatch(this._menuItemActions.getMenuItem(menuItem.id));
    }

    addMenu() {
        this.clearMenu();
        this.addingMenu = true;
    }
    
    addMenuItem() {
        this.clearMenuItem();
        this.addingMenuItem = true;
    }

    saveMenu(menu: Menu) {

        if (menu.id === 0 || menu.id == null) {
            this._store.dispatch(this._menuActions.addMenu(menu));
            this.clearMenu();
        } else {
            this._store.dispatch(this._menuActions.saveMenu(menu));
        }
    }
    
    saveMenuItem(menuItem: MenuItem) {
        if (menuItem.id === 0 || menuItem.id == null) {
            this._store.dispatch(this._menuItemActions.addMenuItem(menuItem));
            this.clearMenuItem();
        } else {
            this._store.dispatch(this._menuItemActions.saveMenuItem(menuItem));
        }
    }

    deleteMenu(menu: Menu) {
        this._store.dispatch(this._menuActions.deleteMenu(menu));
        this._router.navigate(['/menu']);
    }

    deleteMenuItem(menuItem: MenuItem) {
        this._store.dispatch(this._menuItemActions.deleteMenuItem(menuItem));
    }
}
