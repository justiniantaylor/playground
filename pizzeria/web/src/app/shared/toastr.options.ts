import { ToastOptions } from 'ng2-toastr';

export class ToastrOptions extends ToastOptions {
    animate = 'flyRight';
    newestOnTop = false;
    showCloseButton = true;
    positionClass = 'toast-bottom-right';
}