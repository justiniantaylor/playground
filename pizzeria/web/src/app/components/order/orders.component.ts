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
import { OrderActions, 
         OrderItemActions } from '../../actions';
import { Order } from '../../models';

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
    </div>
</div>      
`
})

export class OrdersComponent implements OnInit, OnDestroy {

  private _idSubscription: Subscription;
  navigated = false;
  addingOrder = false;

  orders$: Observable<Array<Order>>;
  order$: Observable<Order>;

  constructor( private _store: Store<AppState>,
               private _route: ActivatedRoute,
               private _router: Router,
               private _orderActions: OrderActions,
               private _orderItemsActions: OrderItemActions,
               private _slimLoadingBarService: SlimLoadingBarService,
               public toastr: ToastsManager,
               public vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit(): void {
    this.orders$ = this._store.select('orders');
    this.orders$.subscribe(b => this.completeLoading());
      
    this.order$ = this._store.select('order');
    this.order$.subscribe(o => {
            this.completeLoading();
            this._store.dispatch(this._orderItemsActions.loadOrderItems(o.id));
        }
    );

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

  selectOrder(order:Order) {
    this.startLoading();
    this.addingOrder = false;
    this.navigated = true;
    this._store.dispatch(this._orderActions.getOrder(order.id));
    this._router.navigate(['/order',order.id]);
  }

  addOrder() {
    this.startLoading();
    this.clearOrder();
    this.addingOrder = true;
  }

  saveOrder(order: Order) {
    this.startLoading();
    if (order.id === 0 || order.id == null) {
      this._store.dispatch(this._orderActions.addOrder(order));
      this.clearOrder();
    } else {
      this._store.dispatch(this._orderActions.saveOrder(order));
      this.toastr.success('You successfully saved the order.', 'Saved!');
    }
  }

  deleteOrder(order: Order) {
    this.startLoading();
    this._store.dispatch(this._orderActions.deleteOrder(order));
    this._router.navigate(['/order']);
    this.toastr.success('You successfully deleted the order.', 'Deleted!');
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
