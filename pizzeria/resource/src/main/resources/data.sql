INSERT INTO notification_status (description, code) 
VALUES ('Queued', 'queued');
INSERT INTO notification_status (description, code) 
VALUES ('Failed', 'failed');
INSERT INTO notification_status (description, code) 
VALUES ('Sent', 'sent');

INSERT INTO notification_method (description, code) 
VALUES ('SMS', 'sms');
INSERT INTO notification_method (description, code) 
VALUES ('Push Notification', 'push_notification');
 
INSERT INTO order_status (description, code) 
VALUES ('Ordered', 'ordered');
INSERT INTO notification_method (description, code) 
VALUES ('Ready', 'ready');
INSERT INTO notification_method (description, code) 
VALUES ('Sent', 'Sent');
INSERT INTO notification_method (description, code) 
VALUES ('Fulfilled', 'fulfilled');

INSERT INTO employee (first_name, last_name, user_name) 
VALUES ('Employee', '1', 'employee');
INSERT INTO employee (first_name, last_name, user_name) 
VALUES ('Manager', '1', 'manager');