import reducer, * as fromOrder from './order.reducer';
import { OrderActions } from '../actions';

describe('Order Reducer', () => {
    let actions: OrderActions;
    let state: fromOrder.OrderState;

    beforeEach(() => {
        actions = new OrderActions();
        state = {
            id: 1,
            deliver: false,
            cashierId: 1,
            customerId: 1,
            addressId: 1,
            orderedDate: "2017-05-20",
            fulfilledDate: null
        };
    });

    it('uses an initial state when none is given', () => {
        let result = reducer(undefined, {type: 'SOME ACTION'});
        expect(result.id).toBe(0);
        expect(result.name).toBe('');
        expect(result.code).toBe('');
    });
});