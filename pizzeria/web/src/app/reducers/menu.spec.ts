import reducer, * as fromMenu from './menu.reducer';
import { MenuActions } from '../actions';

describe('Menu Reducer', () => {
    let actions: MenuActions;
    let state: fromMenu.MenuState;

    beforeEach(() => {
        actions = new MenuActions();
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