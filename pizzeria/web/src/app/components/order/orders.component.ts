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
import { OrderActions,
         OrderItemActions } from '../../actions';
import { Order, 
         OrderItem, 
         MenuItem } from '../../models';

@Component({
    selector: 'orders',
    template: `
<div class="row">
    <div class="col">
        <h2>Orders
            <span class="float-right">
                <button class="btn btn-primary" (click)="addOrder()"><i class="fa fa-fw fa-plus"></i> Add Order</button>
            </span>
        </h2>       
        <hr/>
    </div>
</div>

<div class="row">
    <div class="col">
        <order-list [orders]="orders$ | async"
                    [selectedOrder]="order$ | async"
                    (selectedEvent)="selectOrder($event)"
                    (deletedEvent)="deleteOrder($event)">
        </order-list>       
    </div>

    <div class="col">
        <order-detail [order]="order$ | async"
                      (savedEvent)="saveOrder($event)"
                      (cancelledEvent)="clearOrder($event)">
        </order-detail>

        <div *ngIf="(order$.id | async) != null">

            <div *ngIf="(orderItem$ | async) == null">
                <order-item-list [orderItems]="orderItems$ | async"
                                 [selectedOrderItem]="orderItem$ | async"
                                 (selectedEvent)="selectOrderItem($event)"
                                 (deletedEvent)="deleteOrderItem($event)">
                </order-item-list>       
            </div>
            <div *ngIf="(orderItem$ | async) != null">
                <order-item-detail [orderItem]="orderItem$ | async"   
                                   [menuItems]="menuItems$ | async"     
                                   (savedEvent)="saveOrderItem($event)"
                                   (cancelledEvent)="clearOrderItem($event)">
                </order-item-detail>
            </div>
        </div>
    </div>    
</div>      
`
})

export class OrdersComponent implements OnInit, OnDestroy {

    private _idSubscription: Subscription;
    navigated = false;
    addingOrder = false;
    addingOrderItem = false;

    orders$: Observable<Array<Order>>;
    order$: Observable<Order>;
    orderItems$: Observable<Array<OrderItem>>;
    orderItem$: Observable<OrderItem>;
    
    menuItems$: Observable<Array<MenuItem>>;
    
    constructor(private _store: Store<AppState>,
                private _route: ActivatedRoute,
                private _router: Router,
                private _orderActions: OrderActions,
                private _orderItemActions: OrderItemActions) {}

    ngOnInit(): void {
        this.orders$ = this._store.select('orders');
        this.orders$.subscribe(b => this.completeLoading());

        this.order$ = this._store.select('order');
        this.order$.subscribe(o => {            
            if(o.id != null) {
                this._store.dispatch(this._orderItemActions.loadOrderItems(o.id));   
            } else {
                this._store.dispatch(this._orderItemActions.loadOrderItems(-1));   
            }       
        });

        this.orderItem$ = this._store.select('orderItem');
        this.orderItems$ = this._store.select('orderItems');
        
        this.menuItems$ = this._store.select('menuItems');
            
        this._store.dispatch(this._orderActions.loadOrders());

        this._idSubscription = this._route.params.select<string>('id')
            .subscribe(id => {
                if (id) {
                    this.addingOrder = false;
                    this.navigated = true;
                    this._store.dispatch(this._orderActions.getOrder(id));
                } else {
                    this.clearOrder();
                }
            });
    }

    ngOnDestroy() {
        this._idSubscription.unsubscribe();
    }

    clearOrder() {
        this.addingOrder = false;
        this.navigated = false;
        this._store.dispatch(this._orderActions.resetBlankOrder());
    }
        
    clearOrderItem() {
        this.addingOrderItem = false;
        this._store.dispatch(this._orderItemActions.resetBlankOrderItem());
    }

    selectOrder(order: Order) {
        this.addingOrder = false;
        this.navigated = true;
        this._store.dispatch(this._orderActions.getOrder(order.id));
        this._router.navigate(['/order', order.id]);
    }
        
    selectOrderItem(orderItem: OrderItem) {
        this.addingOrderItem = false;
        this._store.dispatch(this._orderItemActions.getOrderItem(orderItem.id));       
    }

    addOrder() {
        this.clearOrder();
        this.addingOrder = true;
    }
        
    addOrderItem() {
        this.clearOrderItem();
        this.addingOrderItem = true;
    }

    saveOrderItem(orderItem: OrderItem) {
        if (orderItem.id === 0 || orderItem.id == null) {
            this._store.dispatch(this._orderItemActions.addOrderItem(orderItem));
            this.clearOrderItem();
        } else {
            this._store.dispatch(this._orderItemActions.saveOrderItem(orderItem));
        }
    }

    deleteOrder(order: Order) {
        this._store.dispatch(this._orderActions.deleteOrder(order));
    }
        
    deleteOrderItem(orderItem: OrderItem) {
        this._store.dispatch(this._orderItemActions.deleteOrderItem(orderItem));
    }
}
