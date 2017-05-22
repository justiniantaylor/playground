import reducer, * as fromMenuItem from './menuItem.reducer';
import { MenuItemActions } from '../actions';

describe('MenuItem Reducer', () => {
    let actions: MenuItemActions;
    let state: fromMenuItem.MenuItemState;

    beforeEach(() => {
        actions = new MenuItemActions();
        state = {
            id: 1,
            name: 'Test',
            code: 'Code'
        };
    });

    it('uses an initial state when none is given', () => {
        let result = reducer(undefined, {type: 'SOME ACTION'});
        expect(result.id).toBe(0);
        expect(result.name).toBe('');
        expect(result.code).toBe('');
    });
});