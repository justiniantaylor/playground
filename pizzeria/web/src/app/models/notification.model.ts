export class NotificationType {
    public static ERROR='ERROR';
    public static WARNING='WARNING';
    public static INFO='INFO';
    public static SUCCESS='SUCCESS';
}

export interface Notification {
    status: number;
    description: string
    type: NotificationType;
    header: string;
    message: string;
};