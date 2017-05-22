export interface Order {
    id: number;
    deliver: boolean;
    cashierId: number;
    customerId: number;
    addressId: number;
    orderedDate: string;
    fulfilledDate: string;
};
