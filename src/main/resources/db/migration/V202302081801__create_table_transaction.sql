DROP TABLE IF EXISTS public.transaction;
CREATE TABLE IF NOT EXISTS public.transaction
(
    id uuid NOT NULL,
    create_at timestamp without time zone,
    type integer,
    value numeric(10,2) DEFAULT 0.00,
    account_id uuid,
    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk6g20fcr3bhr6bihgy24rq1r1b FOREIGN KEY (account_id)
        REFERENCES public.account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);