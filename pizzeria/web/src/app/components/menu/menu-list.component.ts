import { Component,
         Input,
         Output,
         EventEmitter,
         ChangeDetectionStrategy } from '@angular/core';

import { Menu } from '../../models';

@Component({
    selector: 'menu-list',
    changeDetection: ChangeDetectionStrategy.OnPush,
    template:
`
<div class="modal fade" id="confirm-delete-dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" (click)="cancelEvent.emit()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete this menu?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" (click)="deleteMenu()">OK</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="doNotDeleteMenu()">Cancel</button>
            </div>
        </div>
    </div>
</div>

<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <tr *ngFor="let menu of menus"
             (click)="toggleSelected(menu.id);selectedEvent.emit(menu)" 
             class="clickable"
             [class.selected]="menu.id === selectedMenu.id" 
             [ngClass]="{'selected': selectedId === menu.id}">
        
            <td>{{menu.id}}</td>
            <td>{{menu.startDate}}</td>
            <td>{{menu.endDate}}</td>
            <td><button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#confirm-delete-dialog"><i class="fa fa-fw fa-trash"></i></button></td>
        </tr>
    </tbody>
<table>
`
})
export class MenuListComponent {

    @Input() menus: Menu[];
    @Input() selectedMenu: Menu;
    selectedId: number;

    @Output() selectedEvent = new EventEmitter();
    @Output() deletedEvent = new EventEmitter();

    toggleSelected(newValue: number) {
        if (this.selectedId === newValue) {
            this.selectedId = 0;
        }
        else {
            this.selectedId = newValue;
        }
    }

    deleteMenu(){
        this.deletedEvent.emit(this.selectedMenu)
    }

    doNotDeleteMenu(){}
}