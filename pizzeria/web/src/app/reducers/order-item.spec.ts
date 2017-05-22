import reducer, * as fromOrderItem from './orderItem.reducer';
import { OrderItemActions } from '../actions';

describe('OrderItem Reducer', () => {
    let actions: OrderItemActions;
    let state: fromOrderItem.OrderItemState;

    beforeEach(() => {
        actions = new OrderItemActions();
        state = {
            id: 1,
            quantity: 5,
            unitPriceInCents: 500,
            orderId: 1,
            menuItemId: 1
        };
    });

    it('uses an initial state when none is given', () => {
        let result = reducer(undefined, {type: 'SOME ACTION'});
        expect(result.id).toBe(0);
        expect(result.name).toBe('');
        expect(result.code).toBe('');
    });
});