INSERT INTO income_tax_period (start_date, end_date) 
VALUES ('2012-07-01', '2013-06-30');

INSERT INTO income_tax_rate (income_tax_period_id, income_start_amount_in_cents, income_end_amount_in_cents, tax_rate_amount_in_cents) 
VALUES ((SELECT id FROM income_tax_period WHERE start_date = '2012-07-01'), 0, 18200*100, null);

INSERT INTO income_tax_rate (income_tax_period_id, income_start_amount_in_cents, income_end_amount_in_cents, tax_rate_amount_in_cents) 
VALUES ((SELECT id FROM income_tax_period WHERE start_date = '2012-07-01'), 18201*100, 37000*100, 19);

INSERT INTO income_tax_rate (income_tax_period_id, income_start_amount_in_cents, income_end_amount_in_cents, tax_rate_amount_in_cents) 
VALUES ((SELECT id FROM income_tax_period WHERE start_date = '2012-07-01'), 37001*100, 80000*100, 32.5);

INSERT INTO income_tax_rate (income_tax_period_id, income_start_amount_in_cents, income_end_amount_in_cents, tax_rate_amount_in_cents) 
VALUES ((SELECT id FROM income_tax_period WHERE start_date = '2012-07-01'), 80001*100, 180000*100, 37);

INSERT INTO income_tax_rate (income_tax_period_id, income_start_amount_in_cents, income_end_amount_in_cents, tax_rate_amount_in_cents) 
VALUES ((SELECT id FROM income_tax_period WHERE start_date = '2012-07-01'), 180001*100, null, 45);
